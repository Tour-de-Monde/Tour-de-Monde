package com.ll.tourdemonde.place.controller;

import com.ll.tourdemonde.global.rsData.RsData;

import com.ll.tourdemonde.place.dto.PlaceReqDto;
import com.ll.tourdemonde.place.dto.PlaceReqDtoList;
import com.ll.tourdemonde.place.entity.Place;
import com.ll.tourdemonde.place.service.PlaceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/place")
@RequiredArgsConstructor
public class PlaceController {
    private final PlaceService placeService;

    // 장소 1~N개 저장
//    @PreAuthorize("isAuthenticated()") // 로그인한 회원만 글쓰기 접근 가능 TODO 로그인 할 때 주석 해제
    @PostMapping("/save")
    public String savePlaceList(PlaceReqDtoList placeReqDtoList) {
        RsData<Place> placeRsData = placeService.save(placeReqDtoList);

        return "domain/place/test"; // TODO 임시로 test.html을 사용 나중에 다른거로 보여줘야 함
    }

    // 장소 이름과 장소 주소로 장소 찾기
    @GetMapping("")
    public String findPlace(PlaceReqDto placeReqDto) {
        RsData<Place> placeRsData = placeService.findPlace(placeReqDto);

        return "domain/place/test"; // TODO 임시로 test.html을 사용 나중에 다른거로 보여줘야 함
    }

    // 장소 수정
    //    @PreAuthorize("isAuthenticated()") // 로그인한 회원만 글쓰기 접근 가능 TODO 로그인 할 때 주석 해제
    @PostMapping("/{id}/modify") // PutMappting
    public String modifyPlace(@PathVariable Long id, @Valid PlaceReqDto placeReqDto) {

        // TODO 권한 여부 체크

        placeService.modifyPlace(id, placeReqDto);

        return "domain/place/test"; // TODO 임시로 test.html을 사용 나중에 다른거로 보여줘야 함
    }

    // 장소 삭제
    //    @PreAuthorize("isAuthenticated()") // 로그인한 회원만 글쓰기 접근 가능 TODO 로그인 할 때 주석 해제
    @PostMapping("/delete") // @DeleteMapping
    public String deletePlace(@Valid PlaceReqDto placeReqDto) {

        // TODO 권한 여부 체크

        placeService.deletePlace(placeReqDto);

        return "domain/place/test"; // TODO 임시로 test.html을 사용 나중에 다른거로 보여줘야 함
    }
}
