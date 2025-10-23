
FROM eclipse-temurin:17-jdk-focal

WORKDIR /opt

COPY target/*.jar /opt/app.jar

ENTRYPOINT exec java $JAVA_OPTS -jar /opt/app.jar
