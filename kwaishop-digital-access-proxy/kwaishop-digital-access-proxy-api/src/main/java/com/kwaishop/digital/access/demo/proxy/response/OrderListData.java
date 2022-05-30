package com.kwaishop.digital.access.demo.proxy.response;

import java.util.List;

import lombok.Data;

/**
 * @author zhangyiying
 * Created on 2022-05-17
 */
@Data
public class OrderListData {
    private Integer pageSize;
    private Long beginTime;
    private Long endTime;
    private String cursor;
    private List<OrderDetail> orderList;
}
