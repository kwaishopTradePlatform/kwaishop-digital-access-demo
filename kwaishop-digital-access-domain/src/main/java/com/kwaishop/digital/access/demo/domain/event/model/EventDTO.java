package com.kwaishop.digital.access.demo.domain.event.model;

import lombok.Data;

/**
 * @author zhangyiying
 * Created on 2022-05-18
 */
@Data
public class EventDTO {
    private String eventId;
    private Long bizId;
    private Long userId;
    private String openId;
    private String appKey;
    private String event;
    private String info;
    private Long createTime;
    private Boolean test;
}
