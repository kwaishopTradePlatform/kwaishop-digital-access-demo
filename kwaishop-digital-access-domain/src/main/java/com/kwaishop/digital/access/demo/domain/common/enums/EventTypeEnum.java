package com.kwaishop.digital.access.demo.domain.common.enums;

/**
 * @author zhangyiying
 * Created on 2022-05-18
 */
public enum EventTypeEnum {
    UNKNOWN("unknown", "未知"),
    ORDER_ADD_ORDER("kwaishop_order_addOrder", "订单新增消息"),
    ;

    private String name;

    private String desc;

    EventTypeEnum(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    public static EventTypeEnum ofName(String name) {
        for (EventTypeEnum eventType : values()) {
            if (eventType.getName().equals(name)) {
                return eventType;
            }
        }
        return UNKNOWN;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }
}
