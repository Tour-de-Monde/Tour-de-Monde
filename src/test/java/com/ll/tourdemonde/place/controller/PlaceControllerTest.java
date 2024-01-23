package com.ll.tourdemonde.place.controller;

import com.ll.tourdemonde.place.dto.PlaceReqDto;
import com.ll.tourdemonde.place.dto.PlaceReqDtoList;
import com.ll.tourdemonde.place.entity.Place;
import com.ll.tourdemonde.place.service.PlaceService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class PlaceControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private PlaceService placeService;

    // 장소 조회 GET /place
    @DisplayName("장소를 조회한다.")
    @Test
    void t1() throws Exception {
        // GIVEN
        PlaceReqDto placeReqDto = new PlaceReqDto("아름식당", "대구 달서구", "24.5, 34");
        List<PlaceReqDto> placeReqDtos = new ArrayList<>();
        placeReqDtos.add(placeReqDto);
        PlaceReqDtoList placeReqDtoList = new PlaceReqDtoList(placeReqDtos);
        placeService.save(placeReqDtoList);

        // WHEN
        ResultActions resultActions = mvc
                .perform(get("/place")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("name", "아름식당")
                        .param("address", "대구 달서구")
                        .param("coordinates", "24.5, 34")
                )
                .andDo(print());

        // THEN
        resultActions.andExpect(status().isOk())
                .andExpect(view().name("domain/place/test"))
                .andExpect(handler().handlerType(PlaceController.class))
                .andExpect(handler().methodName("findPlace"));

        // 검증
        Place place = placeService.findLatest().get();

        Assertions.assertThat(place.getName()).isEqualTo("아름식당");
        Assertions.assertThat(place.getAddress()).isEqualTo("대구 달서구");
        Assertions.assertThat(place.getCoordinates()).isEqualTo("24.5, 34");
    }

    // 장소 등록 POST /place/save
    @DisplayName("장소를 등록한다.")
    @Test
    void t2() throws Exception {
        // GIVEN
        PlaceReqDto placeReqDto = new PlaceReqDto("아름식당", "대구 달서구", "24.5, 34");
        PlaceReqDto placeReqDto2 = new PlaceReqDto("다운식당", "대구 칠곡", "35.8203809,128.5389325");
        List<PlaceReqDto> placeReqDtos = new ArrayList<>();
        placeReqDtos.add(placeReqDto);
        placeReqDtos.add(placeReqDto2);
        PlaceReqDtoList placeReqDtoList = new PlaceReqDtoList(placeReqDtos);
        System.out.println("String.valueOf(placeReqDtoList) : " + String.valueOf(placeReqDtoList));
        System.out.println("placeReqDtoList[0] : " + String.valueOf(placeReqDtoList.getPlaceReqDtoList().get(0)));
        System.out.println("placeReqDtoList[1] : " + String.valueOf(placeReqDtoList.getPlaceReqDtoList().get(1)));

        // WHEN
        ResultActions resultActions = mvc
                .perform(post("/place/save")
//                        .with(csrf()) // csrf 토큰은 보안을 위해 POST요청에 필요하다.
                        .contentType(MediaType.MULTIPART_FORM_DATA)
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .param("placeReqDtoList[0]", String.valueOf(placeReqDtoList.getPlaceReqDtoList().get(0)))
//                                .param("placeReqDtoList[1]", String.valueOf(placeReqDtoList.getPlaceReqDtoList().get(1)))
                                .param("placeReqDtoList", String.valueOf(placeReqDtoList))
                )
                .andDo(print());

        // THEN
        resultActions
                .andExpect(status().isOk())
                .andExpect(handler().handlerType(PlaceController.class))
                .andExpect(handler().methodName("savePlaceList"));
    }

    // 장소 수정 POST /place/modify
    // 장소 삭제 POST /place/delete
}