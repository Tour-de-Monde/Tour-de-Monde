package com.ll.tourdemonde.reservation.reservation;

import com.ll.tourdemonde.place.dto.PlaceReqDto;
import com.ll.tourdemonde.place.dto.PlaceReqDtoList;
import com.ll.tourdemonde.place.service.PlaceService;
import com.ll.tourdemonde.reservation.reservation.controller.ReservationController;
import com.ll.tourdemonde.reservation.reservation.entity.Reservation;
import com.ll.tourdemonde.reservation.reservation.entity.ReservationOption;
import com.ll.tourdemonde.reservation.reservation.entity.ReservationType;
import com.ll.tourdemonde.reservation.reservation.service.ReservationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@TestMethodOrder(MethodOrderer.MethodName.class) // 메소드 이름순으로 테스트 실행
@ActiveProfiles("test")
public class ReservationControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private PlaceService placeService;
    @Autowired
    private ReservationService reservationService;

    @Test
    @Rollback(value = false)
    public void T0testInit(){
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
        ResultActions resultActions = mvc
                .perform(get("/reserve/1")) //장소ID 임의지정
                .andDo(print());

        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(handler().handlerType(ReservationController.class))
                .andExpect(handler().methodName("showReservationFromPlace"))
                .andExpect(content().string(containsString("""
                        장소""".stripIndent().trim())));
    }

    //예약 생성 GET /reserve/create
    @Test
    @DisplayName("예약 생성 GET")
    public void T3ShowCreateReservation() throws Exception{
        ResultActions resultActions = mvc
                .perform(get("/reserve/create"))
                .andDo(print());

        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(handler().handlerType(ReservationController.class))
                .andExpect(handler().methodName("createNewReservation"))
                .andExpect(content().string(containsString("""
                        판매자명""".stripIndent().trim())))
                .andExpect(content().string(containsString("""
                        type""".stripIndent().trim())));
    }

    //예약 생성 POST /reserve/create
    @Test
    @DisplayName("예약 생성 POST")
    @Rollback(value = false)
    public void T4ShowCreateReservation() throws Exception{
        ResultActions resultActions = mvc
                .perform(post("/reserve/create")
                        .param("seller", "판매자명1")
                        .param("place", "1")
                        .param("type", "LEISURE"))
                .andDo(print());

        resultActions
                .andExpect(status().is3xxRedirection()) // 성공시 redirect
                .andExpect(handler().handlerType(ReservationController.class))
                .andExpect(handler().methodName("createNewReservation"));


        Reservation reservation = reservationService.findById(1L);

        assertThat(reservation.getSellerName()).as("seller 불일치").isEqualTo("판매자명1");
        assertThat(reservation.getPlace().getName()).as("place 불일치").isIn("장소1");
        assertThat(reservation.getType()).as("type 불일치").isIn(ReservationType.LEISURE);
    }

    //예약 옵션 생성 GET /reserve/create/1/detail
    @Test
    @DisplayName("예약 옵션 생성 GET")
    public void T5createNewReservationOption() throws Exception {
        ResultActions resultActions = mvc
                .perform(get("/reserve/create/1/detail"))
                .andDo(print());

        Reservation reservation = reservationService.findById(1L);

        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(handler().handlerType(ReservationController.class))
                .andExpect(handler().methodName("createNewReservationOption"))
                .andExpect(content().string(containsString("""
                        <li>판매자명 : %s</li>""".formatted(reservation.getSellerName()))));
    }

    //예약 옵션 생성 POST /reserve/create/1/detail
    @Test
    @DisplayName("예약 옵션 생성 POST")
    public void T6createNewReservationOption() throws Exception{
        ResultActions resultActions = mvc
                .perform(post("/reserve/create/1/detail")
                        .param("reservationId", String.valueOf(1L))
                        .param("startDate", LocalDate.now().toString())
                        .param("time", "11:00")
                        .param("price", "1000000"))
                .andDo(print());

        resultActions
                .andExpect(status().is3xxRedirection())
                .andExpect(handler().handlerType(ReservationController.class))
                .andExpect(handler().methodName("createNewReservationOption"));

        ReservationOption reservationOption = reservationService.findById(1L)
                .getOptions().get(0);

        assertThat(reservationOption.getPrice())
                .isEqualTo(1000000);
    }

    //관리페이지 GET /reserve/manage/1
    //예약 수정 GET /reserve/modify/1
    //예약 수정 PUT /reserve/modify/1
    //예약 옵션 수정 GET /reserve/modify/1/detail/1
    //예약 옵션 수정 PUT /reserve/modify/1/detail/1
    //예약 삭제 DELETE /reserve/1
    //예약 옵션 삭제 DELETE /reserve/1/detail/1
}
