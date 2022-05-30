package com.kwaishop.digital.access.demo.domain.item.request;

import lombok.Data;

/**
 * @author zhangyiying
 * Created on 2022-05-18
 */
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
