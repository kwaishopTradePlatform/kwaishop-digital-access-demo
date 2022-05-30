package com.kwaishop.digital.access.demo.proxy.response;

import lombok.Data;

/**
 * @author zhangkwei
 * Created on 2022-05-24
 */
@Data
public class OrderItemInfo {
    private String itemPicUrl;
    private Long originalPrice;
    private Long orderItemId;
    private Integer num;
    private Long itemId;
    private Long relItemId;
    private String skuNick;
    private Long skuId;
    private String itemTitle;
}
