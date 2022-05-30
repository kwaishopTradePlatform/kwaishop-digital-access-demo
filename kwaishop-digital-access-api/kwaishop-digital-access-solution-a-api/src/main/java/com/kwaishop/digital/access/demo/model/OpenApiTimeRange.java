package com.kwaishop.digital.access.demo.model;

import lombok.Data;

@Data
public class OpenApiTimeRange {
    /**
     * 开始时间时间戳 时间为00:00:00
     */
    private Long startTime;

    /**
     * 结束时间时间戳 时间为 23:59:59
     */
    private Long endTime;
}
