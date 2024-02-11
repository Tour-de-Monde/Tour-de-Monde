package com.ll.tourdemonde.reservation.entity;

import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
public enum ReservationType {
    LEISURE("레져"), ACCOMMODATE("숙박"), RESTAURANT("식당");

    private final String type;

    ReservationType(String type) {
        this.type = type;
    }

    public boolean isLeisure() {
        return this == LEISURE;
    }

    public boolean isAccommodate() {
        return this == ACCOMMODATE;
    }

    public boolean isRestaurant() {
        return this == RESTAURANT;
    }

    // 상수를 key로, 값을 value로 하여 map 추출
    public static Map<String, String> getMapValues(){
        return Arrays
                .stream(ReservationType.values())
                .collect(Collectors.toMap(
                        Enum::toString,
                        ReservationType::getType));
    }
}
