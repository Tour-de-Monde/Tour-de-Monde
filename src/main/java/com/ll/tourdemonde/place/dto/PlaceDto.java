package com.ll.tourdemonde.place.dto;

import com.ll.tourdemonde.place.entity.Place;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PlaceDto {
    private String name;
    private String coordinate;

    public Place toEntity(){
        return Place.builder()
                .name(this.name)
                .coordinate(this.coordinate)
                .build();
    }
}
