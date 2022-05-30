package com.kwaishop.digital.access.demo.domain.order.model;

import lombok.Data;

/**
 * @author zhangyiying
 * Created on 2022-05-17
 */
@Data
public class OrderItemInfo {
    private Long skuId;
    private Long relSkuId;
    private String skuDesc;
    private String skuNick;
    private Long itemId;
    private Long relItemId;
    private String itemTitle;
    private String itemLinkUrl;
    private String itemPicUrl;
    private Integer num;
    private Long originalPrice;
    private Long discountFee;
    private Long price;
    private Integer itemType;
    private OrderItemPrevInfo itemPrevInfo;
    private String goodsCode;
    private String warehouseCode;
    private Long orderItemId;
    private Long goodStoreCode;
    private ServiceInfo serviceInfo;
}
