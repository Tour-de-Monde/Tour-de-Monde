package com.ll.tourdemonde.place.service;

import com.ll.tourdemonde.place.dto.PlaceDto;
import com.ll.tourdemonde.place.entity.Place;
import com.ll.tourdemonde.place.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PlaceService {
    private final PlaceRepository placeRepository;


    public Place findById(Long placeId) {
        return placeRepository.findById(placeId)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 장소를 찾을 수 없습니다."));
    }

    @Transactional
    public Place save(PlaceDto dto) {
        Place place = Place.builder()
                .name(dto.getName())
                .coordinate(dto.getCoordinate())
                .build();

        placeRepository.save(place);

        // TODO 반환값 지우기 반환타입도 void로 바꾸기
        return place;
    }
}
