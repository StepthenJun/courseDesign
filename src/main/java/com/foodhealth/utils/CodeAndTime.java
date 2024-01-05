package com.foodhealth.utils;

import lombok.Data;

@Data
public class CodeAndTime {
    private String code;
    private long timeStamp;

    public CodeAndTime(String code, long timeStamp) {
        this.code = code;
        this.timeStamp = timeStamp;
    }

}
