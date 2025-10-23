package com.codedecode.restaurantListingV1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class RestaurantListingV1Application {

	public static void main(String[] args) {
		SpringApplication.run(RestaurantListingV1Application.class, args);
	}

}
