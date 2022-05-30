package com.kwaishop.digital.access.demo.proxy.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.kwaishop.digital.access.demo.proxy.OpenAdaptorService;
import com.kwaishop.digital.access.demo.proxy.common.utils.JsonUtils;
import com.kwaishop.digital.access.demo.proxy.request.BaseProxyReq;
import com.kwaishop.digital.access.demo.proxy.request.OrderCursorListProxyReq;
import com.kwaishop.digital.access.demo.proxy.request.OrderDetailReq;
import com.kwaishop.digital.access.demo.proxy.request.OrderMockReq;
import com.kwaishop.digital.access.demo.proxy.response.BaseProxyResp;
import com.kwaishop.digital.access.demo.proxy.response.OrderDetail;
import com.kwaishop.digital.access.demo.proxy.response.OrderListData;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangyiying
 * Created on 2022-05-17
 */
@Service
@Slf4j
public class OrderAdaptorServiceImpl implements OrderAdaptorService {

    @Autowired
    private OpenAdaptorService openAdaptorService;

    private static final String LIST_ORDER_METHOD_NAME = "open.order.cursor.list";
    private static final String QUERY_ORDER_DETAIL_METHOD_NAME = "open.order.detail";
    private static final String MOCK_ORDER_METHOD_NAME = "open.virtual.mock.order";

    @Override
    public OrderListData listOrderByCursor(OrderCursorListProxyReq req) {
        BaseProxyReq<OrderCursorListProxyReq> baseProxyReq = new BaseProxyReq<>();
        baseProxyReq.setApiName(LIST_ORDER_METHOD_NAME);
        baseProxyReq.setHttpMethod(BaseProxyReq.postMethod());
        baseProxyReq.setParam(req);
        BaseProxyResp baseProxyResp = openAdaptorService.execute(baseProxyReq);
        JsonNode data = JsonUtils.toJSONNode(baseProxyResp.getData()).get("data");
        return JsonUtils.toJavaObject(data, OrderListData.class);
    }

    @Override
    public OrderDetail queryOrderDetail(Long oid) {
        BaseProxyReq<OrderDetailReq> baseProxyReq = new BaseProxyReq<>();
        baseProxyReq.setApiName(QUERY_ORDER_DETAIL_METHOD_NAME);
        baseProxyReq.setHttpMethod(BaseProxyReq.postMethod());
        OrderDetailReq req = new OrderDetailReq();
        req.setOid(oid);
        baseProxyReq.setParam(req);
        BaseProxyResp baseProxyResp = openAdaptorService.execute(baseProxyReq);
        JsonNode data = JsonUtils.toJSONNode(baseProxyResp.getData()).get("data");
        return JsonUtils.toJavaObject(data, OrderDetail.class);
    }

    @Override
    public Long mockOrder(Long itemId, Integer itemNum) {
        BaseProxyReq<OrderMockReq> baseProxyReq = new BaseProxyReq<>();
        baseProxyReq.setApiName(MOCK_ORDER_METHOD_NAME);
        baseProxyReq.setHttpMethod(BaseProxyReq.postMethod());
        OrderMockReq param = new OrderMockReq(itemId, itemNum);
        baseProxyReq.setParam(param);
        BaseProxyResp baseProxyResp = openAdaptorService.execute(baseProxyReq);
        Long data = JsonUtils.toJSONNode(baseProxyResp.getData()).get("oid").asLong();
        return data;
    }
}
