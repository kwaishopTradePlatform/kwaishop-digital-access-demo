package com.kwaishop.digital.access.demo.model;

import lombok.Data;

@Data
public class MealContentVO {
    /**
     * 内容项标题（20字符以内）
     */
    private String title;

    /**
     * 内容项数量（输入数字不超过4位）
     */
    private Integer count;

    /**
     * 内容项单价（单位分），最大值9999900
     */
    private Long price;
}