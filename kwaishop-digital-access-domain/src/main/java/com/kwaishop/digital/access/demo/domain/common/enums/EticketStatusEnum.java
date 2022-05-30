package com.kwaishop.digital.access.demo.domain.common.enums;

import lombok.Getter;

/**
 * @author zhangyiying
 * Created on 2022-05-17
 */
@Getter
public enum EticketStatusEnum {
    UNUSED("未使用"),
    CONSUMED("已消费"),
    DESTROYED("已销毁"),
    ;

    private String desc;

    EticketStatusEnum(String desc) {
        this.desc = desc;
    }
}
