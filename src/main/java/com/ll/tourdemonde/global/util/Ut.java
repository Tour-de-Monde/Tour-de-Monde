package com.ll.tourdemonde.global.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Ut {
    public static LocalDateTime stringToLocalDateTime(String string){
        // html input은 String으로 날짜만 반환한다. ex) 2024-01-24
        LocalDate date = LocalDate.parse(string, DateTimeFormatter.ISO_LOCAL_DATE);
        // 00시 00분으로 설정
        LocalTime time = LocalTime.of(0,0,0);
        // LocalDateTime으로 parse
        return date.atTime(time);
    }
}
