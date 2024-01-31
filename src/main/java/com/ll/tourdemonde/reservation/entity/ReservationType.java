package com.ll.tourdemonde.reservation.entity;

import lombok.Getter;

@Getter
public enum ReservationType {
    LEISURE("leisure"), ACCOMMODATE("accommodate"), RESTAURANT("restaurant");

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
}
