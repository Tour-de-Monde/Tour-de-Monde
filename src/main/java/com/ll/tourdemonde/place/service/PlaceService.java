package com.ll.tourdemonde.place.service;

import com.ll.tourdemonde.place.dto.PlaceDto;
import com.ll.tourdemonde.place.entity.Place;
import com.ll.tourdemonde.place.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public List<Place> createPlacesIfNotExist(List<String> placeNames, List<String> coordinates) {
        List<Place> places = new ArrayList<>();

        for (int i = 0; i < coordinates.size(); i++) {
            String coordinate = coordinates.get(i);
            Optional<Place> existingPlace = placeRepository.findByCoordinate(coordinate);

            if (!existingPlace.isPresent()) {
                // 좌표가 존재하지 않으면 새로운 Place 객체 생성
                Place newPlace = Place.builder()
                        .name(placeNames.get(i))
                        .coordinate(coordinate)
                        .build();
                places.add(newPlace);
            }
        }

        placeRepository.saveAll(places);
        return places;
    }

    public List<Place> findAllByCoordinateOrCreate(List<PlaceDto> placeDtos) {
        return placeDtos.stream().map(placeDto -> findByCoordinateOrCreate(placeDto))
                .collect(Collectors.toList());
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
