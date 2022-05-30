package com.kwaishop.digital.access.demo.domain.common.enums;

import lombok.Getter;

/**
 * @author zhangyiying
 * Created on 2022-05-17
 */
@Getter
public enum SendTypeEnum {
    VIRTUAL("virtual", "虚拟卡"),

    DELIVERY("delivery", "实体卡"),
    ;

    private String name;

    private String desc;

    SendTypeEnum(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }
}
