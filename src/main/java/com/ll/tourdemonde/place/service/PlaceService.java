package com.ll.tourdemonde.place.service;

import com.ll.tourdemonde.place.dto.PlaceDto;
import com.ll.tourdemonde.place.entity.Place;
import com.ll.tourdemonde.place.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlaceService {
    private final PlaceRepository placeRepository;

    public Place findById(Long placeId) {
        return placeRepository.findById(placeId)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 장소를 찾을 수 없습니다."));
    }

    @Transactional
    public void save(PlaceDto dto) {
        Place place = Place.builder()
                .name(dto.getName())
                .coordinate(dto.getCoordinate())
                .build();

        placeRepository.save(place);
    }
    public Place findByCoordinateOrCreate(PlaceDto dto) {
        Optional<Place> opPlace = placeRepository.findByCoordinate(dto.getCoordinate());

        if (opPlace.isPresent()) {
            return opPlace.get();
        }

        Place place = Place.builder()
                .coordinate(dto.getCoordinate())
                .name(dto.getName())
                .build();

        return placeRepository.save(place);
    }
}
