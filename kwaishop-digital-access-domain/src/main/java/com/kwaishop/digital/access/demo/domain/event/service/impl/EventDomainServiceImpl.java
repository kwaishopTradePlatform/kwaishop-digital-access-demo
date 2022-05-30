package com.kwaishop.digital.access.demo.domain.event.service.impl;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kwaishop.digital.access.demo.domain.common.enums.EventTypeEnum;
import com.kwaishop.digital.access.demo.domain.common.error.DigitalErrorCode;
import com.kwaishop.digital.access.demo.domain.common.error.ServiceException;
import com.kwaishop.digital.access.demo.domain.common.utils.PlatformEventSecurityUtil;
import com.kwaishop.digital.access.demo.domain.event.model.EventDTO;
import com.kwaishop.digital.access.demo.domain.event.service.EventDomainService;
import com.kwaishop.digital.access.demo.domain.order.service.OrderDomainService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangyiying
 * Created on 2022-05-18
 */
@Service
@Slf4j
@Lazy
public class EventDomainServiceImpl implements EventDomainService {

    @Value("${kwaishop.eventSecretmvnbu}")
    private String privateKey;

    @Autowired
    private OrderDomainService orderDomainService;

    @Override
    public void acceptEvent(String eventsEncoded) {
        String events = PlatformEventSecurityUtil.decode(eventsEncoded, privateKey);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            EventDTO eventDTO = objectMapper.readValue(events, EventDTO.class);
            if (eventDTO.getTest()) {
                return;
            }
            String event = eventDTO.getEvent();
            EventTypeEnum eventTypeEnum = EventTypeEnum.ofName(event);
            switch (eventTypeEnum) {
                case ORDER_ADD_ORDER:
                    orderDomainService.acceptNewOrder(events);
                    break;
                case UNKNOWN:
                    throw new ServiceException(DigitalErrorCode.PARAM_INVALID, "消息类型无法识别");
                default:
                    break;
            }
        } catch (IOException e) {
            log.error("read event error,", e);
            throw new ServiceException(DigitalErrorCode.PARSE_EVENT_ERROR);
        }
    }
}
