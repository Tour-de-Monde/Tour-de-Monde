package com.ll.tourdemonde.reservation.reservation;

import com.ll.tourdemonde.global.rsData.RsData;
import com.ll.tourdemonde.global.security.CustomUserDetailsService;
import com.ll.tourdemonde.member.service.MemberService;
import com.ll.tourdemonde.place.dto.PlaceDto;
import com.ll.tourdemonde.place.entity.Place;
import com.ll.tourdemonde.place.service.PlaceService;
import com.ll.tourdemonde.reservation.controller.ReservationController;
import com.ll.tourdemonde.reservation.entity.Reservation;
import com.ll.tourdemonde.reservation.entity.ReservationOption;
import com.ll.tourdemonde.reservation.entity.ReservationType;
import com.ll.tourdemonde.reservation.service.ReservationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    @Autowired
    private MemberService memberService;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    public UserDetails getUserDetails() {
        return customUserDetailsService.loadUserByUsername("admin");
    }

    public UserDetails getAdminDetails() {
        return customUserDetailsService.loadUserByUsername("admin");
    }

    @Test
    @Rollback(value = false)
    public void T00testInit() {
        IntStream.range(1, 4).mapToObj(i -> new PlaceDto("장소" + i, "33.1, 37.1" + i))
                .forEach(placeService::save);
        memberService.createMember("user1", "1234", "user@user.com", "user1", null, "000-0000-0000", "user1");
        memberService.createMember("admin", "1234", "admin@user.com", "admin", null, "000-0000-1234", "admin");
    }

    @Test
    @DisplayName("테스트 실행 확인")
    public void T00() {
        System.out.println("테스트 실행 확인");
    }

    //GET /reserve
    @Test
    @DisplayName("1. 샘플페이지 출력")
    public void T01ShowSample() throws Exception {
        ResultActions resultActions = mvc
                .perform(get("/reserve"))
                .andDo(print());

    }

    //장소페이지 GET /reserve/1
    @Test
    @DisplayName("2. 장소페이지(id=1) GET")
    public void T02ShowplacePage() throws Exception {
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
    @DisplayName("3. 예약 생성 GET")
    public void T03ShowCreateReservation() throws Exception {
        ResultActions resultActions = mvc
                .perform(get("/reserve/1/create").with(csrf()).with(user(getUserDetails())))
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
    @DisplayName("4. 예약 생성 POST")
    @Rollback(value = false)
    public void T04ShowCreateReservation() throws Exception {
        ResultActions resultActions = mvc
                .perform(post("/reserve/1/create")
                        .param("seller", "admin")
                        .param("place", String.valueOf(1L))
                        .param("type", "LEISURE")
                        .param("flag", "continue")
                        .with(csrf()).with(user(getUserDetails())))
                .andDo(print());

        resultActions
                .andExpect(status().is3xxRedirection()) // 성공시 redirect
                .andExpect(handler().handlerType(ReservationController.class))
                .andExpect(handler().methodName("createNewReservation"));


        Reservation reservation = reservationService.findById(1L);

        assertThat(reservation.getSeller().getUsername()).as("seller 불일치").isEqualTo("admin");
        assertThat(reservation.getPlace().getName()).as("place 불일치").isIn("장소1");
        assertThat(reservation.getType()).as("type 불일치").isIn(ReservationType.LEISURE);
    }

    //예약 옵션 생성 GET /reserve/create/1/detail
    @Test
    @DisplayName("5. 예약 옵션 생성 GET")
    public void T05createNewReservationOption() throws Exception {
        ResultActions resultActions = mvc
                .perform(get("/reserve/create/1/detail")
                        .with(csrf()).with(user(getUserDetails())))
                .andDo(print());

        Reservation reservation = reservationService.findById(1L);

        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(handler().handlerType(ReservationController.class))
                .andExpect(handler().methodName("createNewReservationOption"))
                .andExpect(content().string(containsString("""
                        <li>판매자명 : %s</li>""".formatted(reservation.getSeller().getUsername())
                        .stripIndent().trim())));
    }

    //예약 옵션 생성 POST /reserve/create/1/detail
    @Test
    @DisplayName("6. 예약 옵션 생성 POST")
    @Rollback(value = false)
    public void T06createNewReservationOption() throws Exception {
        ResultActions resultActions = mvc
                .perform(post("/reserve/create/1/detail")
                        .param("reservationId", String.valueOf(1L))
                        .param("startDate", LocalDate.now().toString())
                        .param("time", "11:00")
                        .param("adultPrice", "10000")
                        .param("childPrice", "5000")
                        .with(csrf()).with(user(getUserDetails())))
                .andDo(print());

        resultActions
                .andExpect(status().is3xxRedirection())
                .andExpect(handler().handlerType(ReservationController.class))
                .andExpect(handler().methodName("createNewReservationOption"));

        ReservationOption reservationOption = reservationService.findById(1L)
                .getOptions().get(0);

        assertThat(reservationOption.getAdultPrice())
                .isEqualTo(10000);
    }

    //관리페이지 GET /reserve/manage/1
    @Test
    @DisplayName("7. 관리페이지 GET")
    public void T07ManageReservation() throws Exception {
        ResultActions resultActions = mvc
                .perform(get("/reserve/manage/1")
                        .with(csrf()).with(user(getUserDetails())))
                .andDo(print());

        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(handler().handlerType(ReservationController.class))
                .andExpect(handler().methodName("manageReservation"))
                .andExpect(content().string(containsString("""
                        예약서비스 목록""".stripIndent().trim())));
    }

    //예약 수정 GET /reserve/modify/1
    @Test
    @DisplayName("8. 예약수정 GET")
    public void T08ModifyReservation() throws Exception {
        ResultActions resultActions = mvc
                .perform(get("/reserve/modify/1")
                        .with(csrf()).with(user(getUserDetails())))
                .andDo(print());

        Reservation reservation = reservationService.findById(1L);

        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(handler().handlerType(ReservationController.class))
                .andExpect(handler().methodName("modifyReservation"))
                .andExpect(content().string(containsString("""
                        <input type="hidden" name="seller" value="%s">""".formatted(reservation.getSeller().getUsername())
                        .stripIndent().trim())));

    }

    //예약 수정 PUT /reserve/modify/1
    @Test
    @DisplayName("9. 예약 수정 PUT")
    public void T09ModifyReservation() throws Exception {
        ResultActions resultActions = mvc
                .perform(put("/reserve/modify/1")
                        .param("seller", "user1")
                        .param("place", "1")
                        .param("type", "ACCOMMODATE")
                        .with(csrf()).with(user(getUserDetails())))
                .andDo(print());

        resultActions
                .andExpect(status().is3xxRedirection())
                .andExpect(handler().handlerType(ReservationController.class))
                .andExpect(handler().methodName("modifyReservation"));

        Reservation reservation = reservationService.findById(1L);

        assertThat(reservation.getType())
                .isEqualTo(ReservationType.ACCOMMODATE);
    }

    //예약 옵션 수정 GET /reserve/modify/1/detail/1
    @Test
    @DisplayName("10. 예약 옵션 수정 GET")
    public void T10ModifyOption() throws Exception {
        ResultActions resultActions = mvc
                .perform(get("/reserve/modify/1/detail/1")
                        .with(csrf()).with(user(getUserDetails())))
                .andDo(print());

        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(handler().handlerType(ReservationController.class))
                .andExpect(handler().methodName("modifyOption"))
                .andExpect(content().string(containsString("""
                        <li>판매자명 : admin</li>""".stripIndent().trim())))
                .andExpect(content().string(containsString("""
                        type="time" name="time" pattern="[0-9]{2}:[0-9]{2}" """.stripIndent().trim())));

    }

    //예약 옵션 수정 PUT /reserve/modify/1/detail/1
    @Test
    @DisplayName("11. 예약 수정 PUT")
    public void T11Modify() throws Exception {
        ResultActions resultActions = mvc
                .perform(put("/reserve/modify/1/detail/1")
                        .param("reservationId", "1")
                        .param("startDate", LocalDate.now().toString())
                        .param("time", "12:00")
                        .param("adultPrice", "10")
                        .param("childPrice", "10")
                        .with(csrf()).with(user(getUserDetails())))
                .andDo(print());

        resultActions
                .andExpect(status().is3xxRedirection())
                .andExpect(handler().handlerType(ReservationController.class))
                .andExpect(handler().methodName("modifyOption"));

        ReservationOption reservationOption = reservationService.findById(1L)
                .getOptions().get(0);

        assertThat(reservationOption.getTime())
                .isEqualTo("12:00");
    }

    //예약 삭제 DELETE /reserve/1
    @Test
    @DisplayName("12. 예약 삭제 DELETE")
    public void T12deleteReservation() throws Exception {
        ResultActions resultActions = mvc
                .perform(delete("/reserve/delete/1")
                        .with(csrf()).with(user(getUserDetails())))
                .andDo(print());

        resultActions
                .andExpect(status().is3xxRedirection())
                .andExpect(handler().handlerType(ReservationController.class))
                .andExpect(handler().methodName("deleteReservation"));

        Place place = placeService.findById(1L);
        RsData<List<Reservation>> list = reservationService.findAllByPlace(place);

        assertThat(list.getData()).isNull();
    }

    //예약 옵션 삭제 DELETE /reserve/1/detail/1
    @Test
    @DisplayName("13. 예약옵션 삭제 DELETE")
    public void T13DeleteOption() throws Exception {
        ResultActions resultActions = mvc
                .perform(delete("/reserve/delete/1/detail/1")
                        .with(csrf()).with(user(getUserDetails())))
                .andDo(print());

        resultActions
                .andExpect(status().is3xxRedirection())
                .andExpect(handler().handlerType(ReservationController.class))
                .andExpect(handler().methodName("deleteReservationOption"));

        List<ReservationOption> options = reservationService.findById(1L).getOptions();

        assertThat(options.isEmpty()).as("비었는지 확인").isTrue();
    }

    @Test
    @DisplayName("14. 예약하기")
    public void T14Reserve() {
        System.out.println("아직 미구현");
    }
}
