package com.kwaishop.digital.access.demo.model;

import java.util.List;

import lombok.Data;

@Data
public class AddSkuVO {
    /**
     * 三方skuId
     */
    private Long relSkuId;

    /**
     * Sku 库存。0 <= 数值 <= 9999999
     */
    private Long skuStock;

    /**
     * Sku 价格，单位为分。1 <= 数值 <=类目配置价格上限
     */
    private Long skuSalePrice;

    /**
     * sku编码，分仓商品必传，SKU编码仅支持中英文、数字以及特殊字符
     */
    private String skuNick;

    /**
     * 商品规格多属性规格参数。目前支持三级属性，并且需要传三级属性的完整笛卡尔积。录入多规格商品时，需要包含所有规格的组合
     */
    private List<AddSkuPropVO> skuProps;

    /**
     * 商品划线价 注意：划线价必须大于当前商品的最高单价，并且 划线价不可超过单价的10倍
     */
    private Long skuMarketPrice;

    /**
     * 货品Id，多仓商品支持字段
     */
    private String goodsId;

    /**
     * sku条形码
     */
    private String gtinCode;

    /**
     * 套餐详情，商品类型为团购时，才可使用该字段，非必传
     */
    private MealDetailVO mealDetail;
}