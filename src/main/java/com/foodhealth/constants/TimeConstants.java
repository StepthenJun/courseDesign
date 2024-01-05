package com.foodhealth.constants;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public abstract class TimeConstants {
    public static final LocalDateTime BREAK_FAST_TIME = LocalDateTime
            .of(LocalDate.now(), LocalTime.of(9,0));
    public static final LocalDateTime LUNCH_TIME = LocalDateTime
            .of(LocalDate.now(), LocalTime.of(17,0));
    public static final LocalDateTime DINNER_TIME = LocalDateTime
            .of(LocalDate.now(), LocalTime.of(18,0));
}
