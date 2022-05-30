package com.kwaishop.digital.access.demo.model;


import java.util.List;

import lombok.Data;

@Data
public class OpenApiUnavailableTimeRule {
    /**
     * 不可用星期 1:周一、2:周二、3:周三、4:周四、5:周五、6:周六、7:周
     */
    private List<Integer> weeks;

    /**
     * 不可用星期 1:周一、2:周二、3:周三、4:周四、5:周五、6:周六、7:周
     */
    private List<Integer> holidays;

    /**
     * 不可用时间范围，最多三组，单位毫秒，每组时间起止相互互斥
     */
    private List<OpenApiTimeRange> timeRanges;
}