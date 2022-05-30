package com.kwaishop.digital.access.demo.model;

import lombok.Data;

@Data
public class DateRangeParam {
    /**
     * 起始时间（毫秒）
     */
    private Long startTimeTimestamp;

    /**
     * 结束时间（毫秒）
     */
    private Long endTimeTimestamp;
}