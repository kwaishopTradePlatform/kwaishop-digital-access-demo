package com.kwaishop.digital.access.demo.proxy.order;

import com.kwaishop.digital.access.demo.proxy.request.OrderCursorListProxyReq;
import com.kwaishop.digital.access.demo.proxy.response.OrderDetail;
import com.kwaishop.digital.access.demo.proxy.response.OrderListData;

/**
 * @author zhangyiying
 * Created on 2022-05-17
 */
public interface OrderAdaptorService {
    OrderListData listOrderByCursor(OrderCursorListProxyReq req);

    OrderDetail queryOrderDetail(Long oid);

    Long mockOrder(Long itemId, Integer itemNum);
}
