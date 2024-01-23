package com.ll.tourdemonde.place.controller;

import com.ll.tourdemonde.global.rsData.RsData;
import com.ll.tourdemonde.place.dto.PlaceReqDtoList;
import com.ll.tourdemonde.place.entity.Place;
import com.ll.tourdemonde.place.service.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
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
    public String saveListPlace(PlaceReqDtoList placeReqDtoList) {
        RsData<Place> place = placeService.save(placeReqDtoList);

        return "domain/place/test"; // TODO 임시로 test.html을 사용 나중에 다른거로 보여줘야 함
    }
}
