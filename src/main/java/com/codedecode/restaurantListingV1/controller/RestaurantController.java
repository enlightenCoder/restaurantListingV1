package com.codedecode.restaurantListingV1.controller;

import com.codedecode.restaurantListingV1.dto.RestaurantDTO;
import com.codedecode.restaurantListingV1.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/restaurant")
public class RestaurantController {

    @Autowired
    RestaurantService restaurantService;


    @GetMapping("/fetchAllRestaurants")
    public ResponseEntity<List<RestaurantDTO>> fetchAllRestaurants(){
        List<RestaurantDTO> restaurantDTOList = restaurantService.fetchAllRestaurants();
        return ResponseEntity.ok(restaurantDTOList);
    }

    @GetMapping("/getRestaurantById/{id}")
    public ResponseEntity<RestaurantDTO> getRestaurantById(@PathVariable int id){
        RestaurantDTO restaurantDTO = restaurantService.getRestaurantById(id);
        return ResponseEntity.ok(restaurantDTO);
    }


    @PostMapping(value = "/addRestaurant", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RestaurantDTO> addRestaurant(@RequestBody RestaurantDTO restaurantDTO) {
        RestaurantDTO savedrestaurantDTO = restaurantService.addRestaurantInDB(restaurantDTO);
        return ResponseEntity.ok(savedrestaurantDTO);
    }



}
