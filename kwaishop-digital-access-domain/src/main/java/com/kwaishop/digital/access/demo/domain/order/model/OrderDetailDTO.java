package com.kwaishop.digital.access.demo.domain.order.model;

import java.util.List;

import com.kwaishop.digital.access.demo.proxy.response.OrderDetail;

import lombok.Data;

/**
 * @author zhangyiying
 * Created on 2022-05-17
 */
@Data
public class OrderDetailDTO {
    private OrderBaseInfo orderBaseInfo;
    private OrderItemInfo orderItemInfo;
    private List<OrderRefundInfo> orderRefundList;
    private List<OrderLogisticsInfo> orderLogisticsInfo;
    private OrderNote orderNote;
    private OrderAddressInfo orderAddress;

    // 按需设置相应属性
    public OrderDetailDTO(OrderDetail order) {

        orderBaseInfo = new OrderBaseInfo();
        orderBaseInfo.setOid(order.getOrderBaseInfo().getOid());
        orderBaseInfo.setSellerOpenId(order.getOrderBaseInfo().getSellerNick());
        orderBaseInfo.setPayTime(order.getOrderBaseInfo().getPayTime());

        orderItemInfo = new OrderItemInfo();
        orderItemInfo.setNum(order.getOrderItemInfo().getNum());
        orderItemInfo.setItemId(order.getOrderItemInfo().getItemId());
        orderItemInfo.setSkuId(order.getOrderItemInfo().getSkuId());
        orderItemInfo.setItemTitle(order.getOrderItemInfo().getItemTitle());

    }
}
