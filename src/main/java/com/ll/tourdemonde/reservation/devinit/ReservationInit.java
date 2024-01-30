package com.ll.tourdemonde.reservation.devinit;

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
public class ReservationInit implements ApplicationRunner {

    private final PlaceService placeService;
    private final ReservationService reservationService;


    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        IntStream.range(1, 4).mapToObj(i -> {
            return new PlaceDto("장소" + i, "23.1, 35." + i);}
                )
                .map(placeDto -> {
                    placeService.save(placeDto);
                    return null;
                });
    }
}
