package com.kwaishop.digital.access.demo.domain.order.service;

import com.kwaishop.digital.access.demo.domain.order.model.OrderDetailDTO;
import com.kwaishop.digital.access.demo.domain.order.model.OrderListDTO;
import com.kwaishop.digital.access.demo.domain.order.request.OrderListDomainRequest;

/**
 * @author zhangyiying
 * Created on 2022-05-17
 */
public interface OrderDomainService {
    /**
     * 获取订单列表
     */
    OrderListDTO listOrder(OrderListDomainRequest request);

    /**
     * 同步指定时间段的订单
     */
    void syncOrder(Long beginTime, Long endTime);

    /**
     * 处理订单新增消息
     */
    void acceptNewOrder(String orderEvent);

    /**
     * 查询订单详情
     */
    OrderDetailDTO queryOrderDetail(Long oid);

    /**
     * mock生成订单，用于测试环境自助联调
     */
    Long mockOrder(Long itemId, Integer itemNum);
}
