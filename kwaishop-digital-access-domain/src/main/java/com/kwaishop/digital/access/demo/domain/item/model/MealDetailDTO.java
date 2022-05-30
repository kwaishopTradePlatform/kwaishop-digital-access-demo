package com.kwaishop.digital.access.demo.domain.item.model;

import java.util.List;

import lombok.Data;

/**
 * @author zhangyiying
 * Created on 2022-05-18
 */
@Data
public class MealDetailDTO {
    /**
     * 套餐分组，最多定义10个分组
     */
    private List<MealGroupDTO> mealGroupDTOList;

    /**
     * 最低用餐人数，最小值1，非必填
     */
    private Integer lowestPeopleNum;

    /**
     * 最高用餐人数，最大值20，非必填
     */
    private Integer highestPeopleNum;

    /**
     * 备注，非必填，最多2000字符
     */
    private String remark;
}
