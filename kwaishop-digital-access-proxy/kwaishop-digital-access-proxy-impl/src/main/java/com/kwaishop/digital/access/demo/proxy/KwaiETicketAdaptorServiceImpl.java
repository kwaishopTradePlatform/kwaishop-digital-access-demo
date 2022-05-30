package com.kwaishop.digital.access.demo.proxy;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.kwaishop.digital.access.demo.proxy.common.utils.JsonUtils;
import com.kwaishop.digital.access.demo.proxy.request.ApproveRefundParam;
import com.kwaishop.digital.access.demo.proxy.request.BaseProxyReq;
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
import com.kwaishop.digital.access.demo.proxy.response.AvailableEticketDetail;
import com.kwaishop.digital.access.demo.proxy.response.BaseProxyResp;
import com.kwaishop.digital.access.demo.proxy.response.CheckSendAvailableProxyResp;
import com.kwaishop.digital.access.demo.proxy.response.ItemAddProxyResponse;
import com.kwaishop.digital.access.demo.proxy.response.MerchantAvailableEticketsView;
import com.kwaishop.digital.access.demo.proxy.response.MerchantRefundListDataView;
import com.kwaishop.digital.access.demo.proxy.response.OrderAddress;
import com.kwaishop.digital.access.demo.proxy.response.OrderBaseInfo;
import com.kwaishop.digital.access.demo.proxy.response.OrderDetail;
import com.kwaishop.digital.access.demo.proxy.response.OrderFeeInfo;
import com.kwaishop.digital.access.demo.proxy.response.OrderItemInfo;
import com.kwaishop.digital.access.demo.proxy.response.OrderListData;
import com.kwaishop.digital.access.demo.proxy.response.QueryPoiDetailResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangkwei
 * Created on 2022-05-20
 */
@Service
@Slf4j
public class KwaiETicketAdaptorServiceImpl implements KwaiETicketAdaptorService {

    @Autowired
    private OpenAdaptorService openAdaptorService;

    private static final String ETICKET_CONSUME_METHOD_NAME = "integration.callback.virtual.eticket.consume";
    private static final String ETICKET_CHECK_AVAILABLE_METHOD_NAME = "open.virtual.eticket.checkavailable";
    private static final String ETICKET_CHECK_SEND_AVAILABLE_METHOD_NAME = "open.virtual.eticket.check.send.available";
    private static final String ETICKET_ORDER_FEE_METHOD_NAME = "open.seller.order.fee.detail";
    private static final String ETICKET_ORDER_DETAIL_METHOD_NAME = "open.order.detail";
    private static final String ETICKET_REVERSE_METHOD_NAME = "integration.callback.virtual.eticket.reverse";
    private static final String ETICKET_SEND_CALLBACK_METHOD_NAME = "integration.callback.virtual.eticket.send";
    private static final String ETICKET_DESTROY_METHOD_NAME = "integration.callback.virtual.eticket.destroy";
    private static final String ETICKET_SHELF_ITEM_UPDATE_METHOD_NAME = "open.item.shelf.status.update";
    private static final String ETICKET_LIST_ORDER_METHOD_NAME = "open.order.cursor.list";
    private static final String ETICKET_REFUND_APPROVE_METHOD_NAME = "open.seller.order.refund.approve";
    private static final String ETICKET_REFUND_DISAGREE_METHOD_NAME = "open.seller.order.refund.disagree.refund";
    private static final String ETICKET_GET_POI_METHOD_NAME = "open.shop.poi.getPoiDetailByOuterPoi";
    private static final String ETICCKET_REFUND_PCURSOR_LIST = "open.seller.order.refund.pcursor.list";
    private static final String ADD_ITEM_METHOD_NAME = "open.item.new";

    private static final Integer SUCCESS = 1;

    @Override
    public boolean reverseETicket(ReverseETicketParam reverseETicketParam) {
        BaseProxyReq<ReverseETicketParam> baseProxyReq = new BaseProxyReq<>();
        baseProxyReq.setApiName(ETICKET_REVERSE_METHOD_NAME);
        baseProxyReq.setHttpMethod(BaseProxyReq.postMethod());
        baseProxyReq.setParam(reverseETicketParam);
        BaseProxyResp baseProxyResp = openAdaptorService.execute(baseProxyReq);
        return baseProxyResp.isSuccess();
    }

    @Override
    public boolean consumeETicket(EticketConsumeParam eticketConsumeParam) {
        BaseProxyReq<EticketConsumeParam> baseProxyReq = new BaseProxyReq<>();
        baseProxyReq.setApiName(ETICKET_CONSUME_METHOD_NAME);
        baseProxyReq.setHttpMethod(BaseProxyReq.postMethod());
        baseProxyReq.setParam(eticketConsumeParam);
        BaseProxyResp baseProxyResp = openAdaptorService.execute(baseProxyReq);

        return baseProxyResp.isSuccess();
    }

    @Override
    public MerchantAvailableEticketsView checkAvailETicket(EticketCheckAvailParam eticketCheckAvailParam) {
        BaseProxyReq<EticketCheckAvailParam> baseProxyReq = new BaseProxyReq<>();
        baseProxyReq.setApiName(ETICKET_CHECK_AVAILABLE_METHOD_NAME);
        baseProxyReq.setHttpMethod(BaseProxyReq.postMethod());
        baseProxyReq.setParam(eticketCheckAvailParam);
        BaseProxyResp baseProxyResp = openAdaptorService.execute(baseProxyReq);

        if (baseProxyResp.isSuccess()) {
            JsonNode etickets = JsonUtils.toJSONNode(baseProxyResp.getData()).get("etickets");
            List<AvailableEticketDetail> availableEticketDetails =
                    JsonUtils.toJavaObjectList(etickets, AvailableEticketDetail.class);
            MerchantAvailableEticketsView merchantAvailableEticketsView = new MerchantAvailableEticketsView();
            merchantAvailableEticketsView.setEtickets(availableEticketDetails);
            return merchantAvailableEticketsView;
        }

        return null;
    }

    @Override
    public void sendCallback(EticketSendCallbackProxyRequest eticketSendCallbackProxyRequest) {
        BaseProxyReq<EticketSendCallbackProxyRequest> baseProxyReq = new BaseProxyReq<>();
        baseProxyReq.setApiName(ETICKET_SEND_CALLBACK_METHOD_NAME);
        baseProxyReq.setHttpMethod(BaseProxyReq.postMethod());
        baseProxyReq.setParam(eticketSendCallbackProxyRequest);
        openAdaptorService.execute(baseProxyReq);
    }

    @Override
    public boolean destroyETicket(DestroyETicketParam destroyETicketParam) {
        BaseProxyReq<DestroyETicketParam> baseProxyReq = new BaseProxyReq<>();
        baseProxyReq.setApiName(ETICKET_DESTROY_METHOD_NAME);
        baseProxyReq.setHttpMethod(BaseProxyReq.postMethod());
        baseProxyReq.setParam(destroyETicketParam);

        BaseProxyResp baseProxyResp = openAdaptorService.execute(baseProxyReq);
        return baseProxyResp.isSuccess();
    }

    @Override
    public boolean updateShelfItemStatus(UpdateShelfItemStatusParam updateShelfItemStatusParam) {
        BaseProxyReq<UpdateShelfItemStatusParam> baseProxyReq = new BaseProxyReq<>();
        baseProxyReq.setApiName(ETICKET_SHELF_ITEM_UPDATE_METHOD_NAME);
        baseProxyReq.setHttpMethod(BaseProxyReq.postMethod());
        baseProxyReq.setParam(updateShelfItemStatusParam);

        BaseProxyResp baseProxyResp = openAdaptorService.execute(baseProxyReq);
        return baseProxyResp.isSuccess();
    }

    @Override
    public OrderListData listOrderByCursor(OrderCursorListProxyReq req) {
        BaseProxyReq<OrderCursorListProxyReq> baseProxyReq = new BaseProxyReq<>();
        baseProxyReq.setApiName(ETICKET_LIST_ORDER_METHOD_NAME);
        baseProxyReq.setHttpMethod(BaseProxyReq.postMethod());
        baseProxyReq.setParam(req);

        BaseProxyResp baseProxyResp = openAdaptorService.execute(baseProxyReq);

        if (baseProxyResp.isSuccess()) {
            String responseStr = baseProxyResp.getData();
            JsonNode data = JsonUtils.toJSONNode(responseStr).get("data");
            return JsonUtils.toJavaObject(data, OrderListData.class);
        }

        return null;
    }

    @Override
    public OrderDetail queryOrderDetail(QueryOrderDetailParam queryOrderDetailParam) {
        BaseProxyReq<QueryOrderDetailParam> baseProxyReq = new BaseProxyReq<>();
        baseProxyReq.setParam(queryOrderDetailParam);
        baseProxyReq.setHttpMethod(BaseProxyReq.getMethod());
        baseProxyReq.setApiName(ETICKET_ORDER_DETAIL_METHOD_NAME);
        BaseProxyResp baseProxyResp = openAdaptorService.execute(baseProxyReq);
        if (baseProxyResp.isSuccess()) {
            OrderDetail orderDetail = new OrderDetail();
            String responseStr = baseProxyResp.getData();
            JsonNode data = JsonUtils.toJSONNode(responseStr).get("data");
            JsonNode orderBaseInfoJsonNode = data.get("orderBaseInfo");
            OrderBaseInfo orderBaseInfo = JsonUtils.toJavaObject(orderBaseInfoJsonNode, OrderBaseInfo.class);
            JsonNode orderAddressJsonNode = data.get("orderAddress");
            OrderAddress orderAddress = JsonUtils.toJavaObject(orderAddressJsonNode, OrderAddress.class);
            JsonNode orderItemInfoJsonNode = data.get("orderItemInfo");
            OrderItemInfo orderItemInfo = new OrderItemInfo();
            orderItemInfo.setItemPicUrl(orderItemInfoJsonNode.get("itemPicUrl").toString());
            orderItemInfo.setOriginalPrice(orderItemInfoJsonNode.get("originalPrice").asLong());
            orderItemInfo.setOrderItemId(orderItemInfoJsonNode.get("orderItemId").asLong());
            orderItemInfo.setNum(orderItemInfoJsonNode.get("num").asInt());
            orderItemInfo.setItemId(orderItemInfoJsonNode.get("itemId").asLong());
            orderItemInfo.setRelItemId(orderItemInfoJsonNode.get("relItemId").asLong());
            orderItemInfo.setSkuNick(orderItemInfoJsonNode.get("skuNick").toString());
            orderItemInfo.setSkuId(orderItemInfoJsonNode.get("skuId").asLong());
            orderItemInfo.setItemTitle(orderItemInfoJsonNode.get("itemTitle").toString());
//            OrderItemInfo orderItemInfo = JsonUtils.toJavaObject(orderItemInfoJsonNode, OrderItemInfo.class);

            orderDetail.setOrderBaseInfo(orderBaseInfo);
            orderDetail.setOrderAddress(orderAddress);
            orderDetail.setOrderItemInfo(orderItemInfo);

            return orderDetail;
        }
        return null;
    }

    @Override
    public OrderFeeInfo queryOrderFee(QueryOrderFeeParam queryOrderFeeParam) {
        BaseProxyReq<QueryOrderFeeParam> baseProxyReq = new BaseProxyReq<>();
        baseProxyReq.setParam(queryOrderFeeParam);
        baseProxyReq.setHttpMethod(BaseProxyReq.getMethod());
        baseProxyReq.setApiName(ETICKET_ORDER_FEE_METHOD_NAME);
        BaseProxyResp baseProxyResp = openAdaptorService.execute(baseProxyReq);
        if (baseProxyResp.isSuccess()) {
            JsonNode data = JsonUtils.toJSONNode(baseProxyResp.getData()).get("data");
            return JsonUtils.toJavaObject(data, OrderFeeInfo.class);
        }

        return null;
    }

    @Override
    public boolean approveRefund(ApproveRefundParam approveRefundParam) {
        BaseProxyReq<ApproveRefundParam> baseProxyReq = new BaseProxyReq<>();
        baseProxyReq.setParam(approveRefundParam);
        baseProxyReq.setHttpMethod(BaseProxyReq.getMethod());
        baseProxyReq.setApiName(ETICKET_REFUND_APPROVE_METHOD_NAME);
        BaseProxyResp baseProxyResp = openAdaptorService.execute(baseProxyReq);

        return baseProxyResp.isSuccess();

    }

    @Override
    public boolean disagreeRefund(DisagreeRefundParam disagreeRefundParam) {
        BaseProxyReq<DisagreeRefundParam> baseProxyReq = new BaseProxyReq<>();
        baseProxyReq.setParam(disagreeRefundParam);
        baseProxyReq.setHttpMethod(BaseProxyReq.getMethod());
        baseProxyReq.setApiName(ETICKET_REFUND_DISAGREE_METHOD_NAME);
        BaseProxyResp baseProxyResp = openAdaptorService.execute(baseProxyReq);

        return baseProxyResp.isSuccess();
    }

    @Override
    public MerchantRefundListDataView listRefundOrder(ListRefundOrderParam listRefundOrderParam) {
        BaseProxyReq<ListRefundOrderParam> baseProxyReq = new BaseProxyReq<>();
        baseProxyReq.setParam(listRefundOrderParam);
        baseProxyReq.setHttpMethod(BaseProxyReq.getMethod());
        baseProxyReq.setApiName(ETICCKET_REFUND_PCURSOR_LIST);
        BaseProxyResp baseProxyResp = openAdaptorService.execute(baseProxyReq);

        if (baseProxyResp.isSuccess()) {
            JsonNode data = JsonUtils.toJSONNode(baseProxyResp.getData()).get("data");
            return JsonUtils.toJavaObject(data, MerchantRefundListDataView.class);
        }

        return null;
    }

    @Override
    public QueryPoiDetailResponse getPoiDetailByOuterPoi(QueryPoiDetailParam queryPoiDetailParam) {
        BaseProxyReq<QueryPoiDetailParam> baseProxyReq = new BaseProxyReq<>();
        baseProxyReq.setParam(queryPoiDetailParam);
        baseProxyReq.setHttpMethod(BaseProxyReq.getMethod());
        baseProxyReq.setApiName(ETICKET_GET_POI_METHOD_NAME);
        BaseProxyResp baseProxyResp = openAdaptorService.execute(baseProxyReq);

        if (baseProxyResp.isSuccess()) {
            JsonNode data = JsonUtils.toJSONNode(baseProxyResp.getData()).get("data");
            return JsonUtils.toJavaObject(data, QueryPoiDetailResponse.class);
        }

        return null;
    }

    @Override
    public CheckSendAvailableProxyResp checkSendAvailable(CheckSendAvailableProxyReq proxyReq) {
        BaseProxyReq<CheckSendAvailableProxyReq> baseProxyReq = new BaseProxyReq<>();
        baseProxyReq.setApiName(ETICKET_CHECK_SEND_AVAILABLE_METHOD_NAME);
        baseProxyReq.setHttpMethod(BaseProxyReq.postMethod());
        baseProxyReq.setParam(proxyReq);
        BaseProxyResp baseProxyResp = openAdaptorService.execute(baseProxyReq);

        if (baseProxyResp.isSuccess()) {
            JsonNode data = JsonUtils.toJSONNode(baseProxyResp.getData()).get("data");
            return JsonUtils.toJavaObject(data, CheckSendAvailableProxyResp.class);
        }

        return null;
    }

    @Override
    public ItemAddProxyResponse addItem(String param) {
        BaseProxyReq<String> baseProxyReq = new BaseProxyReq<>();
        baseProxyReq.setApiName(ADD_ITEM_METHOD_NAME);
        baseProxyReq.setHttpMethod(BaseProxyReq.postMethod());
        baseProxyReq.setParam(param);
        BaseProxyResp baseProxyResp = openAdaptorService.execute(baseProxyReq);
        JsonNode data = JsonUtils.toJSONNode(baseProxyResp.getData()).get("data");
        ItemAddProxyResponse resp = JsonUtils.toJavaObject(data, ItemAddProxyResponse.class);
        return resp;
    }
}
