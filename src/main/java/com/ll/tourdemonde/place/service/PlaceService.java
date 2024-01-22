package com.ll.tourdemonde.place.service;

import com.ll.tourdemonde.global.rsData.RsData;
import com.ll.tourdemonde.place.entity.Place;
import com.ll.tourdemonde.place.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlaceService {
    private final PlaceRepository placeRepository;

    public RsData<Place> save(String name, String address, List<String> coordinates) {
        Place place = new Place(name, address, coordinates);

        // TODO 같은 장소의 경우 원래 있던 장소를 저장
        placeRepository.save(place);

        return new RsData<>(
                "S-1",
                "장소 저장 완료",
                place
        );
    }
}
