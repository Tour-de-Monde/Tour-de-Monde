package com.ll.tourdemonde.place.dto;

import com.ll.tourdemonde.place.entity.Place;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class PlaceDto {
    private String name;
    private String address;
    private Double la;
    private Double ma;

    public Place toEntity() {
        return Place.builder()
                .name(this.name)
                .address(this.address)
                .build();
    }
}
