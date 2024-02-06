package com.ll.tourdemonde.place.dto;

import com.ll.tourdemonde.place.entity.Place;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
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
