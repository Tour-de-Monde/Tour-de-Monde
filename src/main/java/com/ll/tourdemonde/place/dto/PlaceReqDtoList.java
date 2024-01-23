package com.ll.tourdemonde.place.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PlaceReqDtoList { // 여러 개의 장소 리스트
    private List<PlaceReqDto> placeReqDtoList;
}
