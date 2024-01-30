package com.ll.tourdemonde.global.rsData;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
public class RsData<T> { // Rs는 보고서라는 뜻
    String resultCode;
    int statusCode;
    String msg;
    T data;

    // 성공 메시지
    public boolean isSuccess() {
        return resultCode.startsWith("S-"); // resultCode가 S로 시작하면 성공이다.
    }

    // 실패 메시지
    public boolean isFail() {
        return !isSuccess();
    }

    public static <T> RsData<T> of(String resultCode, String msg) {
        return of(resultCode, msg, null);
    }

    public static <T> RsData<T> of(String resultCode, String msg, T data) {
        int statusCode = Integer.parseInt(resultCode.split("-", 2)[0]);

        return new RsData<>(resultCode, statusCode, msg, data);
    }
}