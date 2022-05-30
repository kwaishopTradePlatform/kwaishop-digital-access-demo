package com.kwaishop.digital.access.demo.model;

import com.kwaishop.digital.access.demo.domain.order.request.OrderListDomainRequest;

import lombok.Data;

@Data
public class OrderListRequest {
    /**
     * 订单状态，0未知 1 全部 2 待付款 3 待发货 4 待收货（已发货）5 已收货 6 交易成功订单 7 已关闭订单
     */
    private Integer orderViewStatus;

    /**
     * 每页请求数量 最多一页50条
     */
    private Integer pageSize;

    /**
     * 1时间降序 2时间升序 默认降序
     */
    private Integer sort;

    /**
     * 1按创建时间查找 2按更新时间查找 默认创建时间
     */
    private Integer queryType;

    /**
     * 订单生成时间的开始时间，单位毫秒， 不能小于90天前，且需要小于结束时间
     */
    private Long beginTime;

    /**
     * 订单生成时间的截止时间，单位毫秒，与开始时间的时间范围不大于7天
     */
    private Long endTime;

    /**
     * 分销类型 0-全部 1-普通订单 2-分销订单
     */
    private Integer cpsType;

    /**
     * 游标内容 第一次传空串，之后传上一次的cursor返回值，若返回“nomore”则标识到底
     */
    private String cursor;

    public OrderListDomainRequest toDomainReq() {
        OrderListDomainRequest proxyReq = new OrderListDomainRequest();
        proxyReq.setOrderViewStatus(orderViewStatus);
        proxyReq.setPageSize(pageSize);
        proxyReq.setSort(sort);
        proxyReq.setQueryType(queryType);
        proxyReq.setBeginTime(beginTime);
        proxyReq.setEndTime(endTime);
        proxyReq.setCpsType(cpsType);
        proxyReq.setCursor(cursor);
        return proxyReq;
    }
}