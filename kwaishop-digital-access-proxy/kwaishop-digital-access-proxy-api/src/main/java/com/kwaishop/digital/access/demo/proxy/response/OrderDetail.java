package com.kwaishop.digital.access.demo.proxy.response;

import lombok.Data;

/**
 * @author zhangyiying
 * Created on 2022-05-18
 */
@Data
public class OrderDetail {
    private OrderBaseInfo orderBaseInfo;
    private OrderAddress orderAddress;
    private OrderItemInfo orderItemInfo;
}
