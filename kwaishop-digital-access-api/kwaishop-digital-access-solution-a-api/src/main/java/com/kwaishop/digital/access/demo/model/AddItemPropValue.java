package com.kwaishop.digital.access.demo.model;

import java.util.List;

import lombok.Data;

@Data
public class AddItemPropValue {
    /**
     * 属性id（规格数量上限200，规格值数量上限500）
     */
    private Long propId;

    /**
     * 单选属性 (根据open.item.category.config中propInputType和required判断是否必填)
     */
    private CategoryPropValueParam radioPropValue;

    /**
     * 多选属性 (根据open.item.category.config中propInputType和required判断是否必填)
     */
    private List<CategoryPropValueParam> checkBoxPropValuesList;

    /**
     * 文本属性值 (根据open.item.category.config中propInputType和required判断是否必填，不允许传空字符串)
     */
    private String textPropValue;

    /**
     * 时间戳属性 (根据open.item.category.config中propInputType和required判断是否必填)
     */
    private Long datetimeTimestamp;

    /**
     * 时间范围属性 (根据open.item.category.config中propInputType和required判断是否必填)
     */
    private DateRangeParam dateRange;

    /**
     * 序号，open.item.category.config返回
     */
    private Long sortNum;

    /**
     * 图片属性列表 (根据open.item.category.config中propInputType和required判断是否必填)
     */
    private List<String> imagePropValues;

    /**
     * 属性名称
     */
    private String propName;

    /**
     * 属性别名
     */
    private String propAlias;

    /**
     * 输入类型，1-文本 2-checkbox 3-数字 4-邮箱 5-日期 6-url地址 7-时间范围 8-单选框 9-图片
     */
    private Integer inputType;

    /**
     * 属性类型，1-sku属性 2-商品属性
     */
    private Integer propType;

    /**
     * 单位属性值Id，open.item.category.config返回
     */
    private Long unitPropValueId;

    /**
     * 单位属性值名称
     */
    private String unitPropValueName;
}