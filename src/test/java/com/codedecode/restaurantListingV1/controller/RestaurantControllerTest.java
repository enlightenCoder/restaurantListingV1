package com.codedecode.restaurantListingV1.controller;

import com.codedecode.restaurantListingV1.dto.RestaurantDTO;
import com.codedecode.restaurantListingV1.service.RestaurantService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RestaurantControllerTest {


    @InjectMocks
    RestaurantController restaurantController;

    @Mock
    RestaurantService restaurantService;


    // Arrange helpers: factory for dummy data
    private List<RestaurantDTO> createDummyRestaurantDTOList() {
        return List.of(
                RestaurantDTO.builder()
                        .id(1)
                        .name("The Gourmet Kitchen")
                        .address("123 Food St, Flavor Town")
                        .city("Tasteville")
                        .restaurantDescription("A place for gourmet delights.")
                        .build(),
                RestaurantDTO.builder()
                        .id(2)
                        .name("Pasta Paradise")
                        .address("456 Noodle Ave, Carb City")
                        .city("Tasteville")
                        .restaurantDescription("Heaven for pasta lovers.")
                        .build()
        );
    }


    @Test
    void testfetchAllRestaurants() {

        //1 Arrange
        List<RestaurantDTO> dummyRestaurants = createDummyRestaurantDTOList();
        when(restaurantService.fetchAllRestaurants()).thenReturn(dummyRestaurants);

        //2 Act
        ResponseEntity<List<RestaurantDTO>> response = restaurantController.fetchAllRestaurants();

        //3 Assert
        assert response.getStatusCode().is2xxSuccessful();
        assert response.getBody().equals(dummyRestaurants);

    }

    @Test
    void testGetRestaurantById() {

        // Arrange
        RestaurantDTO dummyRestaurant = createDummyRestaurantDTOList().get(0);
        when(restaurantService.getRestaurantById(1)).thenReturn(dummyRestaurant);
        //Act
        ResponseEntity<RestaurantDTO> response = restaurantController.getRestaurantById(1);
        //Assert
        assert response.getStatusCode().is2xxSuccessful();
        assert response.getBody().equals(dummyRestaurant);

    }

    @Test
    void testAddRestaurant() {
        //Arrange
        RestaurantDTO dummyRestaurant = createDummyRestaurantDTOList().get(0);
        when(restaurantService.addRestaurantInDB(dummyRestaurant)).thenReturn(dummyRestaurant);
        //Act
        ResponseEntity<RestaurantDTO> response = restaurantController.addRestaurant(dummyRestaurant);
        //Assert
        assert response.getStatusCode().is2xxSuccessful();
        assert response.getBody().equals(dummyRestaurant);
    }
}
