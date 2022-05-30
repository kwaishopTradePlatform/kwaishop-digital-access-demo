package com.kwaishop.digital.access.demo.domain.item.request;

import lombok.Data;

/**
 * @author zhangyiying
 * Created on 2022-05-18
 */
@Data
public class CategoryPropValueParam {
    /**
     * 属性值id
     */
    private Long propValueId;

    /**
     * 属性值
     */
    private String propValue;
}
