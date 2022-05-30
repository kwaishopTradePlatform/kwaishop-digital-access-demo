package com.kwaishop.digital.access.demo.proxy;

import com.kwaishop.digital.access.demo.proxy.request.ApproveRefundParam;
import com.kwaishop.digital.access.demo.proxy.request.CheckSendAvailableProxyReq;
import com.kwaishop.digital.access.demo.proxy.request.DestroyETicketParam;
import com.kwaishop.digital.access.demo.proxy.request.DisagreeRefundParam;
import com.kwaishop.digital.access.demo.proxy.request.EticketCheckAvailParam;
import com.kwaishop.digital.access.demo.proxy.request.EticketConsumeParam;
import com.kwaishop.digital.access.demo.proxy.request.EticketSendCallbackProxyRequest;
import com.kwaishop.digital.access.demo.proxy.request.ListRefundOrderParam;
import com.kwaishop.digital.access.demo.proxy.request.OrderCursorListProxyReq;
import com.kwaishop.digital.access.demo.proxy.request.QueryOrderDetailParam;
import com.kwaishop.digital.access.demo.proxy.request.QueryOrderFeeParam;
import com.kwaishop.digital.access.demo.proxy.request.QueryPoiDetailParam;
import com.kwaishop.digital.access.demo.proxy.request.ReverseETicketParam;
import com.kwaishop.digital.access.demo.proxy.request.UpdateShelfItemStatusParam;
import com.kwaishop.digital.access.demo.proxy.response.CheckSendAvailableProxyResp;
import com.kwaishop.digital.access.demo.proxy.response.ItemAddProxyResponse;
import com.kwaishop.digital.access.demo.proxy.response.MerchantAvailableEticketsView;
import com.kwaishop.digital.access.demo.proxy.response.MerchantRefundListDataView;
import com.kwaishop.digital.access.demo.proxy.response.OrderDetail;
import com.kwaishop.digital.access.demo.proxy.response.OrderFeeInfo;
import com.kwaishop.digital.access.demo.proxy.response.OrderListData;
import com.kwaishop.digital.access.demo.proxy.response.QueryPoiDetailResponse;

/**
 * @author zhangkwei
 * Created on 2022-05-20
 */
public interface KwaiETicketAdaptorService {
    /**
     * 冲正电子凭证
     */
    boolean reverseETicket(ReverseETicketParam reverseETicketParam);

    /**
     * 消费电子凭证
     */
    boolean consumeETicket(EticketConsumeParam eticketConsumeParam);

    /**
     * 检查券可用性
     */
    MerchantAvailableEticketsView checkAvailETicket(EticketCheckAvailParam eticketCheckAvailParam);

    /**
     * 发码回调
     */
    void sendCallback(EticketSendCallbackProxyRequest eticketSendCallbackProxyRequest);

    /**
     * 销毁券
     */
    boolean destroyETicket(DestroyETicketParam destroyETicketParam);

    /**
     * 商品上下架管理
     */
    boolean updateShelfItemStatus(UpdateShelfItemStatusParam updateShelfItemStatusParam);

    /**
     * 获取订单列表
     */
    OrderListData listOrderByCursor(OrderCursorListProxyReq req);

    /**
     * 查询订单详情
     */
    OrderDetail queryOrderDetail(QueryOrderDetailParam queryOrderDetailParam);

    /**
     * 查询订单费用
     */
    OrderFeeInfo queryOrderFee(QueryOrderFeeParam queryOrderFeeParam);

    /**
     * 同意退款
     */
    boolean approveRefund(ApproveRefundParam approveRefundParam);

    /**
     * 拒绝退款
     */
    boolean disagreeRefund(DisagreeRefundParam disagreeRefundParam);

    /**
     * 获取售后单详情
     */
    MerchantRefundListDataView listRefundOrder(ListRefundOrderParam listRefundOrderParam);

    /**
     * 获取poi信息
     */
    QueryPoiDetailResponse getPoiDetailByOuterPoi(QueryPoiDetailParam queryPoiDetailParam);

    /**
     * 验证是否可以发货并查询发货信息
     */
    CheckSendAvailableProxyResp checkSendAvailable(CheckSendAvailableProxyReq proxyReq);

    ItemAddProxyResponse addItem(String param);
}
