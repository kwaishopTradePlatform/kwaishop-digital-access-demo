package com.kwaishop.digital.access.demo.domain.event.service;

/**
 * @author zhangyiying
 * Created on 2022-05-18
 */
public interface EventDomainService {
    /**
     * 处理消息
     * @param eventsEncoded
     */
    void acceptEvent(String eventsEncoded);
}
