package com.codedecode.restaurantListingV1.service;

import com.codedecode.restaurantListingV1.dto.RestaurantDTO;
import com.codedecode.restaurantListingV1.entity.Restaurant;
import com.codedecode.restaurantListingV1.repository.RestaurantRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class RestaurantServiceTest {


    @InjectMocks
    private RestaurantService restaurantService;

    @Mock
    private RestaurantRepository restaurantRepository;


    private List<Restaurant> createDummyRestaurantList() {
        return List.of(
                Restaurant.builder()
                        .id(1)
                        .name("The Gourmet Kitchen")
                        .address("123 Food St, Flavor Town")
                        .city("Tasteville")
                        .restaurantDescription("A place for gourmet delights.")
                        .build(),
                Restaurant.builder()
                        .id(2)
                        .name("Pasta Paradise")
                        .address("456 Noodle Ave, Carb City")
                        .city("Tasteville")
                        .restaurantDescription("Heaven for pasta lovers.")
                        .build()
        );
    }

    private RestaurantDTO getOneRestauntDTO(Restaurant restaurant) {
        return RestaurantDTO.builder()
                .id(restaurant.getId())
                .name(restaurant.getName())
                .address(restaurant.getAddress())
                .city(restaurant.getCity())
                .restaurantDescription(restaurant.getRestaurantDescription())
                .build();
    }


    @Test
    void fetchAllRestaurants() {
        // Arrange
        List<Restaurant> dummyRestaurants = createDummyRestaurantList();
        when(restaurantRepository.findAll()).thenReturn(dummyRestaurants);
        //Act
        List<RestaurantDTO> restaurantDTOList = restaurantService.fetchAllRestaurants();

        //Assert
        assertEquals(2, restaurantDTOList.size());
    }

    @Test
    void addRestaurantInDB() {
        // Arrange
        Restaurant restaurant = createDummyRestaurantList().get(0);
        when(restaurantRepository.save(any(Restaurant.class))).thenReturn(restaurant);
        //Act
        RestaurantDTO savedRestaurant  = restaurantService.addRestaurantInDB(getOneRestauntDTO(restaurant));
        //Assert
        assertEquals(1, savedRestaurant.getId());

    }



    @Test
    void getRestaurantById() {
        //Arrange
        Restaurant restaurant = createDummyRestaurantList().get(0);
        when(restaurantRepository.findById(1)).thenReturn(Optional.of(restaurant));
        //Act
        RestaurantDTO restaurantDTO = restaurantService.getRestaurantById(1);
        //Assert
        assertEquals(1, restaurantDTO.getId());
    }
}