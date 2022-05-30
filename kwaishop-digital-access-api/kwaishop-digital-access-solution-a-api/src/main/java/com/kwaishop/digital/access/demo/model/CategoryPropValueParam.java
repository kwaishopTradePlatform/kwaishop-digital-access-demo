package com.kwaishop.digital.access.demo.model;

import lombok.Data;

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
