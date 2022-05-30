package com.kwaishop.digital.access.demo.domain.order.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kwaishop.digital.access.demo.domain.common.error.DigitalErrorCode;
import com.kwaishop.digital.access.demo.domain.common.error.ServiceException;
import com.kwaishop.digital.access.demo.domain.order.model.OrderAddEventDTO;
import com.kwaishop.digital.access.demo.domain.order.model.OrderDetailDTO;
import com.kwaishop.digital.access.demo.domain.order.model.OrderListDTO;
import com.kwaishop.digital.access.demo.domain.order.request.OrderListDomainRequest;
import com.kwaishop.digital.access.demo.domain.order.service.OrderDomainService;
import com.kwaishop.digital.access.demo.proxy.KwaiETicketAdaptorService;
import com.kwaishop.digital.access.demo.proxy.order.OrderAdaptorService;
import com.kwaishop.digital.access.demo.proxy.request.OrderCursorListProxyReq;
import com.kwaishop.digital.access.demo.proxy.request.QueryOrderDetailParam;
import com.kwaishop.digital.access.demo.proxy.response.OrderDetail;
import com.kwaishop.digital.access.demo.proxy.response.OrderListData;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangyiying
 * Created on 2022-05-17
 */
@Service
@Slf4j
@Lazy
public class OrderDomainServiceImpl implements OrderDomainService {

    private static final Integer PAGE_SIZE = 50;

    private static final String NO_MORE = "nomore";

    @Autowired
    private KwaiETicketAdaptorService kwaiETicketAdaptorService;

    @Autowired
    private OrderAdaptorService orderAdaptorService;

    @Override
    public OrderListDTO listOrder(OrderListDomainRequest request) {
        try {
            OrderCursorListProxyReq proxyReq = request.toProxyReq();
            OrderListData orderListData = kwaiETicketAdaptorService.listOrderByCursor(proxyReq);
            return new OrderListDTO(orderListData);
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            log.error("listOrder error:", e);
            throw new ServiceException(DigitalErrorCode.LIST_ORDER_ERROR);
        }
    }

    @Override
    public void syncOrder(Long beginTime, Long endTime) {
        OrderListDomainRequest request = new OrderListDomainRequest();
        request.setBeginTime(beginTime);
        request.setEndTime(endTime);
        request.setOrderViewStatus(1);
        request.setPageSize(PAGE_SIZE);
        String cursor = StringUtils.EMPTY;
        while (!NO_MORE.equals(cursor)) {
            OrderListDTO orderListDTO = this.listOrder(request);
            //todo 落库
            cursor = orderListDTO.getCursor();
        }
    }

    @Override
    public void acceptNewOrder(String orderEvent) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            OrderAddEventDTO orderAddEventDTO = objectMapper.readValue(orderEvent, OrderAddEventDTO.class);
            OrderDetailDTO orderDetailDTO = this.queryOrderDetail(orderAddEventDTO.getOid());
            //todo 落库
        } catch (Exception e) {
            log.error("accept order event error:", e);
            throw new ServiceException(DigitalErrorCode.PARSE_EVENT_ERROR, "处理订单新增消息异常");
        }
    }

    @Override
    public OrderDetailDTO queryOrderDetail(Long oid) {
        OrderDetail orderDetail = kwaiETicketAdaptorService.queryOrderDetail(buildQueryOrderDetailReq(oid));;
        return new OrderDetailDTO(orderDetail);
    }

    private QueryOrderDetailParam buildQueryOrderDetailReq(Long oid) {
        QueryOrderDetailParam queryOrderDetailParam = new QueryOrderDetailParam();
        queryOrderDetailParam.setOid(oid);

        return queryOrderDetailParam;
    }

    @Override
    public Long mockOrder(Long itemId, Integer itemNum) {
        try {
            Long oid = orderAdaptorService.mockOrder(itemId, itemNum);
            return oid;
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            log.error("mockOrder error:", e);
            throw new ServiceException(DigitalErrorCode.MOCK_ORDER_ERROR);
        }
    }


}
