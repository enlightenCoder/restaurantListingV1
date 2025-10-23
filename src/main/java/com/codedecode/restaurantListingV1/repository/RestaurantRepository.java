package com.codedecode.restaurantListingV1.repository;

import com.codedecode.restaurantListingV1.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {
}
