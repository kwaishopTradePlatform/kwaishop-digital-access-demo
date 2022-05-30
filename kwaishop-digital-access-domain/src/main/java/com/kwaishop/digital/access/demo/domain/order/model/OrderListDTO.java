package com.kwaishop.digital.access.demo.domain.order.model;

import java.util.ArrayList;
import java.util.List;

import com.kwaishop.digital.access.demo.proxy.response.OrderDetail;
import com.kwaishop.digital.access.demo.proxy.response.OrderListData;

import lombok.Data;

/**
 * @author zhangyiying
 * Created on 2022-05-17
 */
@Data
public class OrderListDTO {
    private Integer pageSize;
    private Long beginTime;
    private Long endTime;
    private String cursor;
    private List<OrderDetailDTO> orderList;

    public OrderListDTO(OrderListData orderListData) {
        this.pageSize = orderListData.getPageSize();
        this.beginTime = orderListData.getBeginTime();
        this.endTime = orderListData.getEndTime();
        this.cursor = orderListData.getCursor();
        this.orderList = new ArrayList<>();
        for (OrderDetail order : orderListData.getOrderList()) {
            orderList.add(new OrderDetailDTO(order));
        }
    }
}
