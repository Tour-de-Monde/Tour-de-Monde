package com.ll.tourdemonde.global.exception;

import com.ll.tourdemonde.global.rsData.RsData;
import lombok.Getter;

public class GlobalException extends RuntimeException {
    // GlobalException 클래스는 RuntimeException을 확장하여 전역 예외를 정의

    @Getter
    private RsData<?> rsData;

    public GlobalException(String resultCode, String msg) {
        super(resultCode + ": " + msg);

        rsData = RsData.of(resultCode, msg);
    }
}
