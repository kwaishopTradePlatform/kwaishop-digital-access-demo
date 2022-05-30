package com.kwaishop.digital.access.demo.domain.order.model;

import lombok.Data;

/**
 * @author zhangyiying
 * Created on 2022-05-18
 */
@Data
public class OrderAddEventDTO {
    /**
     * 订单id
     */
    private Long oid;

    /**
     * 商家id
     */
    private Long sellerId;

    /**
     * 买家openId
     */
    private String openId;

    /**
     * 订单创建时间
     */
    private Long createTime;
    /**
     * 买家openId
     */
    private String buyerOpenId;
}
