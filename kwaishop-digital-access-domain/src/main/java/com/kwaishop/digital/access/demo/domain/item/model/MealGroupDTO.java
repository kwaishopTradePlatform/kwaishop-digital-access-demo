package com.kwaishop.digital.access.demo.domain.item.model;

import java.util.List;

import lombok.Data;

/**
 * @author zhangyiying
 * Created on 2022-05-18
 */
@Data
public class MealGroupDTO {
    /**
     * 套餐分组名称（限制10个字符）
     */
    private String title;

    /**
     * 套餐分组，最多50组
     */
    private List<MealContentDTO> mealContentDTOList;

    /**
     * 套餐内容项 几选几（第一个几），最小值1，最大值套餐分组数量
     */
    private Integer fromNum;

    /**
     * 套餐内容项 几选几（第二个几），最小值1，最大值套餐分组数量
     */
    private Integer selectNum;
}
