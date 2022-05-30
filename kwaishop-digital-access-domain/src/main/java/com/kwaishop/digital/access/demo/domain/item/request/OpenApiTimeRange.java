package com.kwaishop.digital.access.demo.domain.item.request;

import lombok.Data;

/**
 * @author zhangyiying
 * Created on 2022-05-18
 */
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
