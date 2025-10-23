package com.codedecode.restaurantListingV1.dto;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RestaurantDTO {

    private int id;

    private String name;

    private String address;

    private String city;

    private String restaurantDescription;

}
