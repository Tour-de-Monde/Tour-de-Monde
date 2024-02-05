package com.ll.tourdemonde.global.devinit;

import com.ll.tourdemonde.member.service.MemberService;
import com.ll.tourdemonde.place.dto.PlaceDto;
import com.ll.tourdemonde.place.service.PlaceService;
import com.ll.tourdemonde.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.IntStream;

@Profile("dev")
@RequiredArgsConstructor
@Configuration
public class DevInit implements ApplicationRunner {

    private final PlaceService placeService;
    private final MemberService memberService;
    private final ReservationService reservationService;


    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        IntStream.range(1, 4).mapToObj(i -> new PlaceDto("장소" + i, "33.1, 37.1" + i))
                .forEach(placeService::save);

//        // 멤버생성 - unique 값들로 인해 에러를 계속 뱉음. 사용 중단
//        IntStream.range(1,4)
//                .forEach(i -> memberService.createMember("user"+i, "1234",
//                        "user"+i+"@user.com","user"+i,
//                        LocalDate.of(2020,1,1),"010-0000-000"+i));
    }
}
