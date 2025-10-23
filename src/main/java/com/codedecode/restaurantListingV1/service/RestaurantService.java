package com.codedecode.restaurantListingV1.service;

import com.codedecode.restaurantListingV1.dto.RestaurantDTO;
import com.codedecode.restaurantListingV1.entity.Restaurant;
import com.codedecode.restaurantListingV1.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;


    public List<RestaurantDTO> fetchAllRestaurants() {

        List<Restaurant> restaurants = restaurantRepository.findAll();
        // map it to DTO
        List<RestaurantDTO> restaurantDTOList = restaurants.stream()
                .map(restaurant -> RestaurantDTO.builder()
                        .id(restaurant.getId())
                        .name(restaurant.getName())
                        .address(restaurant.getAddress())
                        .city(restaurant.getCity())
                        .restaurantDescription(restaurant.getRestaurantDescription())
                        .build())
                .toList();

        return restaurantDTOList;
    }

    public RestaurantDTO addRestaurantInDB(RestaurantDTO restaurantDTO) {
        Restaurant savedRestaurant = restaurantRepository.save(Restaurant.builder()
                .name(restaurantDTO.getName())
                .address(restaurantDTO.getAddress())
                .city(restaurantDTO.getCity())
                .restaurantDescription(restaurantDTO.getRestaurantDescription())
                .build());

        return  RestaurantDTO.builder()
                .id(savedRestaurant.getId())
                .name(savedRestaurant.getName())
                .address(savedRestaurant.getAddress())
                .city(savedRestaurant.getCity())
                .restaurantDescription(savedRestaurant.getRestaurantDescription())
                .build();
    }

    public RestaurantDTO getRestaurantById(int id) {
        Restaurant restaurant = restaurantRepository.findById(id).orElse(null);

        if (restaurant != null) {
            return RestaurantDTO.builder()
                    .id(restaurant.getId())
                    .name(restaurant.getName())
                    .address(restaurant.getAddress())
                    .city(restaurant.getCity())
                    .restaurantDescription(restaurant.getRestaurantDescription())
                    .build();
        }
        return null;
    }
}
