package com.ll.tourdemonde.reservation.entity;

import com.ll.tourdemonde.reservation.entity.converter.LocalTimeConverter;
import com.ll.tourdemonde.post.entity.BaseTime;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reservation_details")
public class ReservationOption extends BaseTime {
    // 판매자가 설정한 예약의 세부사항 - 예약을 진행할 경우 이 옵션이 선택됨.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_Id")
    private Reservation reservation;

    @NotNull(message = "시작날짜는 빈칸일 수 없습니다.")
    private LocalDateTime startDate; // 예약 날짜

    private LocalDateTime endDate;

    @Convert(converter = LocalTimeConverter.class)
    @NotNull
    private LocalTime time; // 예약시간 옵션

    @NotNull
    private Long price; //옵션의 가격

    @Builder.Default
    private boolean occupied = false; //예약여부 확인

    // 예약의 옵션 내용을 변경
    public ReservationOption modifyValues(LocalDateTime startDate, LocalDateTime endDate, LocalTime time, Long price) {
        if(!this.startDate.equals(startDate)){
            this.startDate = startDate;
        }

        if (!this.endDate.equals(endDate)) {
            this.endDate = endDate;
        }

        if (!this.time.equals(time)) {
            this.time = time;
        }

        if (!this.price.equals(price)) {
            this.price = price;
        }
        return this;
    }

    public void completeReservation(){
        this.occupied = true;
    }

    public void cancleReservation(){
        this.occupied = false;
    }
}
