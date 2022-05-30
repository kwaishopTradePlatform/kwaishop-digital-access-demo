package com.kwaishop.digital.access.demo.domain.item.model;

import lombok.Data;

/**
 * @author zhangyiying
 * Created on 2022-05-18
 */
@Data
public class AddSkuPropDTO {
    /**
     * 规格名称
     */
    private String propName;

    /**
     * 规格值名称
     */
    private String propValueName;

    /**
     * 图片url, 如果是主属性图片必传，图片url。0 < 长度 <= 2000，图片大小不超过2M，支持png/jpeg/webp/bmp格式
     */
    private String imageUrl;

    /**
     * 1是 0 否 ，是否是主属性，主属性标记为关联sku规格图片
     */
    private Integer isMainProp;

    /**
     * 规格值分组id
     */
    private Long propValueGroupId;

    /**
     * 销售属性版本 自定义1，使用类目模板传2
     */
    private Integer propVersion;
}
