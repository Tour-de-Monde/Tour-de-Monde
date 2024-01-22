package com.ll.tourdemonde.place.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class PlaceReqDto {
    @NotBlank
    private String name; // 이름
    @NotBlank
    private String address; // 주소
    @NotBlank
    private List<String> coordinates; // [latitude(위도), longitude(경도)]
}
