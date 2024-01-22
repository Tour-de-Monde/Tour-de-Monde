package com.ll.tourdemonde.place.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class PlaceReqDto { // 1 개의 장소
    @NotBlank
    private String name; // 이름
    @NotBlank
    private String address; // 주소
    private List<String> coordinates; // [latitude(위도), longitude(경도)] TODO 프론트단에서 무조건 들어오게 하기
}
