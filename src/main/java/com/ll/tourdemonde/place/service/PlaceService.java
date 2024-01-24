package com.ll.tourdemonde.place.service;

import com.ll.tourdemonde.global.rsData.RsData;
import com.ll.tourdemonde.place.dto.PlaceReqDto;
import com.ll.tourdemonde.place.dto.PlaceReqDtoList;
import com.ll.tourdemonde.place.entity.Place;
import com.ll.tourdemonde.place.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PlaceService {
    private final PlaceRepository placeRepository;

    // 장소 여러개 저장
    @Transactional
    public RsData<Place> save(PlaceReqDtoList placeReqDtoList) {
        for (PlaceReqDto placeReqDto : placeReqDtoList.getPlaceReqDtoList()) {
            String name = placeReqDto.getName();
            String address = placeReqDto.getAddress();
            String coordinates = placeReqDto.getCoordinates();

            // 장소가 이미 존재 하는 경우, 장소를 저장 하지 않음
            Optional<Place> existingPlaceOptional = placeRepository.findByNameAndAddress(name, address);

            if (existingPlaceOptional.isEmpty()) {
                // 존재 하지 않는 경우 새로운 장소 저장
                Place newPlace = new Place(name, address, coordinates);
                placeRepository.save(newPlace);
            }
        }

        return new RsData<>(
                "S-1",
                "장소 저장 완료",
                null
        );
    }

    // 장소의 이름, 주소로 장소 찾기
    public RsData<Place> findPlace(PlaceReqDto placeReqDto) {
        Place place = placeRepository.findByNameAndAddress(placeReqDto.getName(), placeReqDto.getAddress())
                .orElseThrow(() -> new IllegalArgumentException("해당하는 장소는 없습니다."));

        return new RsData<>(
                "S-2",
                "%s 장소를 찾았습니다.".formatted(place.getName()),
                place
        );
    }

    // 장소의 ID로 장소 찾기
    public RsData<Place> findByPlace(Long id) {
        Place place = placeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 장소는 없습니다."));

        return new RsData<>(
                "S-3",
                "%s 장소를 찾았습니다.".formatted(place.getName()),
                place
        );
    }

    // 장소 수정
    @Transactional
    public void modifyPlace(Long id, PlaceReqDto placeReqDto) {
        // 장소 찾기
        Place place = findByPlace(id).getData();

        place.setName(placeReqDto.getName());
        place.setAddress(placeReqDto.getAddress());
        place.setCoordinates(placeReqDto.getCoordinates());
    }

    @Transactional
    public void deletePlace(PlaceReqDto placeReqDto) {
        // 장소 찾기
        Place place = findPlace(placeReqDto).getData();

        placeRepository.delete(place);
    }

    // 마지막에 등록한 장소
    public Optional<Place> findLatest() {
        return placeRepository.findFirstByOrderByIdDesc();
    }
}
