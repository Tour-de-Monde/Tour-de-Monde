package com.ll.tourdemonde.place.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PlaceReqDto { // 1 개의 장소
    @NotBlank
    private String name; // 이름
    @NotBlank
    private String address; // 주소
    private String coordinates; // (latitude(위도), longitude(경도))
}
