package com.ll.tourdemonde.place.service;

import com.ll.tourdemonde.global.rsData.RsData;
import com.ll.tourdemonde.place.dto.PlaceDto;
import com.ll.tourdemonde.place.entity.Place;
import com.ll.tourdemonde.place.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
                .la(dto.getLa())
                .ma(dto.getMa())
                .address(dto.getAddress())
                .build();

        placeRepository.save(place);
    }
    public Place findByCoordinateOrCreate(PlaceDto dto) {
        Optional<Place> opPlace = placeRepository.findByAddress(dto.getAddress());

        if (opPlace.isPresent()) {
            return opPlace.get();
        }

        Place place = Place.builder()
                .address(dto.getAddress())
                .la(dto.getLa())
                .ma(dto.getMa())
                .name(dto.getName())
                .build();

        return placeRepository.save(place);
    }

    public RsData<List<Place>> findAllByOrderByNameAscCreateDateAsc() {
        List<Place> list = placeRepository.findAllByOrderByNameAscCreateDateAsc();

        return new RsData<>("S-200","리스트 가져오기", list);
    }
}
