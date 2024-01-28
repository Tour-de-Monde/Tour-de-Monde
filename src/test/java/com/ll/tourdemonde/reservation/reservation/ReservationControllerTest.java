package com.ll.tourdemonde.reservation.reservation;

import com.ll.tourdemonde.place.dto.PlaceReqDto;
import com.ll.tourdemonde.place.dto.PlaceReqDtoList;
import com.ll.tourdemonde.place.service.PlaceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
public class ReservationControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private PlaceService placeService;

    @BeforeEach
    public void testInit(){
        PlaceReqDtoList list = new PlaceReqDtoList(new ArrayList<>());
        list.setPlaceReqDtoList(IntStream.range(1, 4).mapToObj(i -> {
            return new PlaceReqDto("장소" + i, "서울시 강남구 " + i + "동", "23.1, 35." + i);
        }).collect(Collectors.toList()));
        placeService.save(list);
    }

    @Test
    @DisplayName("테스트 실행 확인")
    public void T0(){
        System.out.println("테스트 실행 확인");
    }

    //GET /reserve
    @Test
    @DisplayName("샘플페이지 출력")
    public void T1ShowSample() throws Exception {
        ResultActions resultActions = mvc
                .perform(get("/reserve"))
                .andDo(print());

    }

    //장소페이지 GET /reserve/1
    @Test
    @DisplayName("장소페이지(id=1) GET")
    public void T2ShowplacePage() throws Exception{
        long id = 1;
        ResultActions resultActions = mvc
                .perform(get("/reserve/1"))
                .andDo(print());
    }
    //예약 생성 GET /reserve/create
    //예약 생성 POST /reserve/create
    //예약 옵션 생성 GET /reserve/create/1/detail
    //예약 옵션 생성 POST /reserve/create/1/detail
    //관리페이지 GET /reserve/manage/1
    //예약 수정 GET /reserve/modify/1
    //예약 수정 PUT /reserve/modify/1
    //예약 옵션 수정 GET /reserve/modify/1/detail/1
    //예약 옵션 수정 PUT /reserve/modify/1/detail/1
    //예약 삭제 DELETE /reserve/1
    //예약 옵션 삭제 DELETE /reserve/1/detail/1
}
