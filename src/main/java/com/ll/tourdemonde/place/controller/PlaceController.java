package com.ll.tourdemonde.place.controller;

import com.ll.tourdemonde.global.rsData.RsData;
import com.ll.tourdemonde.place.dto.PlaceReqDto;
import com.ll.tourdemonde.place.dto.PlaceReqDtoList;
import com.ll.tourdemonde.place.entity.Place;
import com.ll.tourdemonde.place.service.PlaceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/place")
@RequiredArgsConstructor
public class PlaceController {
    private final PlaceService placeService;

    // 장소 저장 TODO 게시글 저장시 한번에 여러장소 저장 그리고 게시글:장소는 1:N이 아닌 N:1이 아닌가?
//    @PreAuthorize("isAuthenticated()") // 로그인한 회원만 글쓰기 접근 가능 TODO 로그인 할 때 주석 해제
    @PostMapping("/save")
    public String savePlace(@Valid PlaceReqDto placeReqDto) {
        RsData<Place> place = placeService.save(placeReqDto.getName(), placeReqDto.getAddress(), placeReqDto.getCoordinates());

        // TODO 장소 저장 완료를 보여줄 필요가 있을까?
        return "main"; // TODO 임시로 main.html을 사용 나중에 다른거로 보여줘야 함
    }

    // 장소 여러개 저장
    @PostMapping("/saveList")
    public String saveListPlace(PlaceReqDtoList placeReqDtoList) {
        RsData<Place> place = placeService.saveList(placeReqDtoList);

        // TODO 장소 저장 완료를 보여줄 필요가 있을까?
        return "main"; // TODO 임시로 main.html을 사용 나중에 다른거로 보여줘야 함
    }
}
