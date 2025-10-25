pipeline {
  agent any  // Use any available Jenkins agent (node) to run this pipeline

  environment {
    DOCKERHUB_CREDENTIALS = credentials('DOCKER_HUB_CREDENTIALS') // Load Docker Hub credentials stored in Jenkins
    VERSION = "${env.BUILD_ID}"  // Use Jenkins build number as Docker image version
  }

  tools {
    maven "Maven"  // Ensure Maven is installed on the agent, using the Jenkins configured Maven installation
  }

  stages {  // Begin pipeline stages

    stage('Maven Build'){  // Stage to build the Java project
        steps{
            sh 'mvn clean package  -DskipTests'  // Run Maven: clean previous build, package the project, skip tests for speed
        }
    }

    stage('Run Tests') {  // Stage to execute unit/integration tests
        steps {
            sh 'mvn test'  // Run Maven test goal
        }
    }

    stage('SonarQube Analysis') {  // Stage to perform static code analysis with SonarQube
        steps {
            sh 'mvn clean org.jacoco:jacoco-maven-plugin:prepare-agent install sonar:sonar -Dsonar.host.url=http://52.66.229.47:9000/ -Dsonar.login=squ_cefd7bb07d2407d6865a31ea92c78d92e0e80c47'
            // Maven command to run SonarQube analysis and generate code coverage report using JaCoCo
        }
    }

    stage('Check code coverage') {  // Stage to enforce minimum code coverage threshold
        steps {
            script {  // Use Groovy script block because we need variables, API calls, and conditionals
                def token = "squ_cefd7bb07d2407d6865a31ea92c78d92e0e80c47" // SonarQube API token
                def sonarQubeUrl = "http://52.66.229.47:9000/api"  // SonarQube API base URL
                def componentKey = "com.codedecode:restaurantListingV1"  // SonarQube project/component key, check in your project pom.xml groupid and Artifactid
                def coverageThreshold = 80.0  // Minimum required code coverage percentage

                // Call SonarQube API to get code coverage measure
                def response = sh (
                    script: "curl -H 'Authorization: Bearer ${token}' '${sonarQubeUrl}/measures/component?component=${componentKey}&metricKeys=coverage'",
                    returnStdout: true  // Capture the output of the curl command
                ).trim()  // Remove any leading/trailing whitespace

                // Extract the coverage value from JSON response using jq
                def coverage = sh (
                    script: "echo '${response}' | jq -r '.component.measures[0].value'", // -jq is a lightweight JSON processor
                    returnStdout: true
                ).trim().toDouble()  // Convert string to number

                echo "Coverage: ${coverage}"  // Print coverage for logging

                // Fail the pipeline if coverage is below threshold
                if (coverage < coverageThreshold) {
                    error "Coverage is below the threshold of ${coverageThreshold}%. Aborting the pipeline."
                }
            }
        }
    }

    stage('Docker Build and Push') {  // Stage to build and push Docker image
        steps {
            // Log in to Docker Hub using credentials from Jenkins
            sh 'echo $DOCKERHUB_CREDENTIALS_PSW | docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin'
            sh 'docker build -t christopherami/restaurant-listing-service:${VERSION} .'  // Build Docker image with tag
            sh 'docker push christopherami/restaurant-listing-service:${VERSION}'  // Push Docker image to Docker Hub
        }
    }

    stage('Cleanup Workspace') {  // Stage to clean the Jenkins workspace
        steps {
            deleteDir()  // Delete all files in the workspace to free up space and avoid conflicts
        }
    }

    stage('Update Image Tag in GitOps') {  // Stage to update Kubernetes manifests in GitOps repo with new Docker image
        steps {
            // Checkout the GitOps repository using SSH credentials
            checkout scmGit(
                branches: [[name: '*/main']],  // Checkout master branch
                extensions: [],
                userRemoteConfigs: [[ credentialsId: 'git-ssh', url: 'git@github.com:enlightenCoder/deployment.git']]
            )
            script {  // Groovy script block for dynamic commands, sed is stream editor find and replace text in file, like a superpower find and replace: -i change file in place and s substitute s/<search_pattern>/<replacement>/
                sh '''
                    sed -i "s/image:.*/image: christopherami\\/restaurant-listing-service:${VERSION}/" restaurant-manifest.yml
                '''  // Update the Docker image tag in the Kubernetes YAML file

                sh 'git checkout main'  // Ensure on main branch
                sh 'git add .'  // Stage changes for commit
                sh 'git commit -m "Update image tag"'  // Commit the updated image tag

                // Push changes using SSH credentials
                sshagent(['git-ssh']) {
                    sh('git push')
                }
            }
        }
    }

  }  // End of stages

}  // End of pipeline
