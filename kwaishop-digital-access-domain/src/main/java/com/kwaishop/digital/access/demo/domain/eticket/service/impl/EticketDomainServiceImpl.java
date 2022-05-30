package com.kwaishop.digital.access.demo.domain.eticket.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kwaishop.digital.access.demo.domain.common.enums.EticketStatusEnum;
import com.kwaishop.digital.access.demo.domain.common.error.DigitalErrorCode;
import com.kwaishop.digital.access.demo.domain.common.error.ServiceException;
import com.kwaishop.digital.access.demo.domain.eticket.model.ETicket;
import com.kwaishop.digital.access.demo.domain.eticket.model.EticketsDetailDTO;
import com.kwaishop.digital.access.demo.domain.eticket.model.MerchantAvailableEticketsDTO;
import com.kwaishop.digital.access.demo.domain.eticket.request.AvailableEticketDomain;
import com.kwaishop.digital.access.demo.domain.eticket.request.CheckSendAvailableDomainReq;
import com.kwaishop.digital.access.demo.domain.eticket.request.DestroyETicketDomainReq;
import com.kwaishop.digital.access.demo.domain.eticket.request.EticketCheckAvailDomainReq;
import com.kwaishop.digital.access.demo.domain.eticket.request.EticketConsumeDomainReq;
import com.kwaishop.digital.access.demo.domain.eticket.request.EticketDomain;
import com.kwaishop.digital.access.demo.domain.eticket.request.EticketQueryDomainReq;
import com.kwaishop.digital.access.demo.domain.eticket.request.EticketSendDomainReq;
import com.kwaishop.digital.access.demo.domain.eticket.request.ReverseETicketDomainReq;
import com.kwaishop.digital.access.demo.domain.eticket.response.CheckSendAvailableDomainResp;
import com.kwaishop.digital.access.demo.domain.eticket.service.EticketDomainService;
import com.kwaishop.digital.access.demo.domain.order.model.OrderDetailDTO;
import com.kwaishop.digital.access.demo.domain.order.service.OrderDomainService;
import com.kwaishop.digital.access.demo.proxy.KwaiETicketAdaptorService;
import com.kwaishop.digital.access.demo.proxy.request.AvailableEticket;
import com.kwaishop.digital.access.demo.proxy.request.CheckSendAvailableProxyReq;
import com.kwaishop.digital.access.demo.proxy.request.DestroyETicketParam;
import com.kwaishop.digital.access.demo.proxy.request.Eticket;
import com.kwaishop.digital.access.demo.proxy.request.EticketCheckAvailParam;
import com.kwaishop.digital.access.demo.proxy.request.EticketConsumeParam;
import com.kwaishop.digital.access.demo.proxy.request.EticketSendCallbackProxyRequest;
import com.kwaishop.digital.access.demo.proxy.request.ListRefundOrderParam;
import com.kwaishop.digital.access.demo.proxy.request.QueryOrderDetailParam;
import com.kwaishop.digital.access.demo.proxy.request.QueryOrderFeeParam;
import com.kwaishop.digital.access.demo.proxy.request.ReverseETicketParam;
import com.kwaishop.digital.access.demo.proxy.request.SendCallbackETicket;
import com.kwaishop.digital.access.demo.proxy.response.CheckSendAvailableProxyResp;
import com.kwaishop.digital.access.demo.proxy.response.MerchantAvailableEticketsView;
import com.kwaishop.digital.access.demo.proxy.response.MerchantRefundListDataView;
import com.kwaishop.digital.access.demo.proxy.response.OrderDetail;
import com.kwaishop.digital.access.demo.proxy.response.OrderFeeInfo;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangyiying
 * Created on 2022-05-12
 */
@Service
@Slf4j
public class EticketDomainServiceImpl implements EticketDomainService {

    @Autowired
    private KwaiETicketAdaptorService kwaiETicketAdaptorService;

    @Autowired
    private OrderDomainService orderDomainService;

    private static String eTicketIdForTest = "";

    @Override
    public String sendEticket(EticketSendDomainReq request) {
        // 1、查询快手订单相关信息
        OrderDetail orderDetail =
                kwaiETicketAdaptorService.queryOrderDetail(buildQueryOrderDetailReq(Long.parseLong(request.getOid())));
        // 2、查询订单费用先关信息
        OrderFeeInfo orderFeeInfo =
                kwaiETicketAdaptorService.queryOrderFee(buildQueryOrderDeeReq(Long.parseLong(request.getOid())));
        // 3、接受到快手的发码请求，进行相关卡券生成落库操作
        sendTicket(request, orderDetail, orderFeeInfo);
        // 4、电子凭证发码回调
        String orderNo = getOrderNo(request.getOid());
        sendEticketCallback(request);
        return orderNo;
    }


    @Override
    public boolean reverseETicket(ReverseETicketDomainReq reverseETicketDomainReq) {
        return kwaiETicketAdaptorService.reverseETicket(buildReverseETicketParam(reverseETicketDomainReq));
    }

    private String getOrderNo(String oid) {
        //todo 改成实际生成供应商订单号的逻辑
        return oid;
    }

    @Override
    public boolean consumeEticket(EticketConsumeDomainReq request) {
        // 1、卡券核销，isv内部落库等操作处理

        // 2、核销发码回调
        return kwaiETicketAdaptorService.consumeETicket(buildEticketConsumeParam(request));
    }

    @Override
    public MerchantAvailableEticketsDTO checkAvailEticket(EticketCheckAvailDomainReq request) {
        try {
            MerchantAvailableEticketsView merchantAvailableEticketsView =
                    kwaiETicketAdaptorService.checkAvailETicket(buildEticketCheckAvailParam(request));
            return new MerchantAvailableEticketsDTO(merchantAvailableEticketsView);
        } catch (Exception e) {
            log.error("checkAvailEticket error:", e);
            throw new ServiceException(DigitalErrorCode.CHECK_AVAILABLE_ETICKET_ERROR);
        }
    }

    private EticketCheckAvailParam buildEticketCheckAvailParam(EticketCheckAvailDomainReq eticketCheckAvailDomainReq) {

        EticketCheckAvailParam eticketCheckAvailParam = new EticketCheckAvailParam();

        eticketCheckAvailParam.setSellerId(eticketCheckAvailDomainReq.getSellerId());
        eticketCheckAvailParam.setBuyerId(eticketCheckAvailDomainReq.getBuyerId());
        if (eticketCheckAvailDomainReq.getOrderId() != null) {
            eticketCheckAvailParam.setOrderId(eticketCheckAvailDomainReq.getOrderId());
        }
        if (eticketCheckAvailDomainReq.getEticketType() != null) {
            eticketCheckAvailParam.setEticketType(eticketCheckAvailDomainReq.getEticketType());
        }

        if (eticketCheckAvailDomainReq.getETicketList() != null) {
            List<AvailableEticketDomain> availableEticketDomains = eticketCheckAvailDomainReq.getETicketList();
            List<AvailableEticket> availableEtickets = new ArrayList<>();
            for (AvailableEticketDomain availableEticketDomain : availableEticketDomains) {
                availableEtickets.add(buildEAvailableEticket(availableEticketDomain));
            }
            eticketCheckAvailParam.setETicketList(availableEtickets);
        }
        return eticketCheckAvailParam;
    }

    private AvailableEticket buildEAvailableEticket(AvailableEticketDomain eticketDomain) {
        AvailableEticket eticket = new AvailableEticket();

        eticket.setId(eticketDomain.getId());
        eticket.setCode(eticketDomain.getCode());
        eticket.setNum(eticketDomain.getNum());

        return eticket;
    }

    @Override
    public EticketsDetailDTO queryEticket(EticketQueryDomainReq request) {
        // 1、isv根据参数查询本地库中的券信息
        EticketsDetailDTO eticketsDetailDTO = new EticketsDetailDTO();
        eticketsDetailDTO.setOid(request.getOid());
        eticketsDetailDTO.setSendType(request.getSendType());
        eticketsDetailDTO.setEticketType(request.getEticketType());
        eticketsDetailDTO.setSendNum(0);
        List<ETicket> eTickets = queryETickets(request.getOid());
        eticketsDetailDTO.setEtickets(eTickets);
        return eticketsDetailDTO;
    }

    @Override
    public boolean destroyETicket(DestroyETicketDomainReq request) {
        // 1、查询订单详情
        OrderDetail orderDetail =
                kwaiETicketAdaptorService.queryOrderDetail(buildQueryOrderDetailReq(Long.parseLong(request.getOid())));
        // 2、查询退款单详情
        MerchantRefundListDataView merchantRefundListDataView =
                kwaiETicketAdaptorService.listRefundOrder(buildListRefundOrderReq(request));
        // 3、逻辑校验后更新本地数据
        destroyTicket(request, orderDetail, merchantRefundListDataView);
        // 4、回调快手销毁接口
        return kwaiETicketAdaptorService.destroyETicket(buildDestroyETicketReq(request));
    }

    @Override
    public CheckSendAvailableDomainResp checkSendAvailable(CheckSendAvailableDomainReq req) {
        CheckSendAvailableProxyReq proxyReq = new CheckSendAvailableProxyReq();
        proxyReq.setOid(req.getOid());
        proxyReq.setBuyerId(req.getBuyerId());
        proxyReq.setSellerId(req.getSellerId());
        CheckSendAvailableProxyResp proxyResp = kwaiETicketAdaptorService.checkSendAvailable(proxyReq);
        return new CheckSendAvailableDomainResp(proxyResp);
    }

    @Override
    public String getETicketId() {
        return eTicketIdForTest;
    }

    private List<ETicket> queryETickets(String oid) {
        //todo 查询真实的卡券
        List<ETicket> eTickets = new ArrayList<>();
        ETicket eTicket = new ETicket();
        eTicket.setId(UUID.randomUUID().toString());
        eTicket.setStatus(EticketStatusEnum.UNUSED.name());
        eTicket.setNum(1);
        eTicket.setValidStartTime(0L);
        eTicket.setValidEndTime(0L);
        eTicket.setEticketConsumeDetails(null);
        eTickets.add(eTicket);
        return eTickets;
    }

    private EticketConsumeParam buildEticketConsumeParam(EticketConsumeDomainReq eticketConsumeDomainRequest) {

        EticketConsumeParam eticketConsumeParam = new EticketConsumeParam();
        // 快手订单号
        eticketConsumeParam.setOid(eticketConsumeDomainRequest.getOid());
        List<EticketDomain> eticketDomains = eticketConsumeDomainRequest.getEtickets();
        List<Eticket> eticketRqs = new ArrayList<>();
        for (int i = 0; i < eticketDomains.size(); i++) {
            eticketRqs.add(buildEticket(eticketDomains.get(i)));
        }
        // 电子凭证
        eticketConsumeParam.setEtickets(eticketRqs);
        // 消费者核销时间
        eticketConsumeParam.setConsumeTime(eticketConsumeDomainRequest.getConsumeTime());
        // 核销状态: 核销成功-CONSUMED 预约成功-APPOINTED
        eticketConsumeParam.setStatus(eticketConsumeDomainRequest.getStatus());
        // 消费类型，门店消费-consume， 门店自提-pickup， 物流发货-delivery， 预约发货-appointment_delivery
        eticketConsumeParam.setConsumeType(eticketConsumeDomainRequest.getConsumeType());
        // 核销门店名称(门店消费、门店自提时必传)
        eticketConsumeParam.setStoreName(eticketConsumeDomainRequest.getStoreName());
        // 核销门店地址(门店消费、门店自提时必传)
        eticketConsumeParam.setStoreAddress(eticketConsumeDomainRequest.getStoreAddress());
        String consumeType = eticketConsumeDomainRequest.getConsumeType();
        if ("appointment_delivery".equals(consumeType)) {
            // 仅在consumeType为预约发货场景下生效。 物流方式：圆通/韵达/顺丰/EMS(物流发货、预约发货方式必传)
            eticketConsumeParam.setExpressNo(eticketConsumeDomainRequest.getExpressNo());
            // 仅在consumeType为预约发货场景下生效。 快递单号(预发发货方式必传)物流发货、预约发货方式必传)
            eticketConsumeParam.setExpressCode(eticketConsumeDomainRequest.getExpressCode());
        }
        if ("pickup".equals(consumeType)) {
            // 仅在consumeType为门店自提场景下生效。 预约时间(预约发货方式必传)
            eticketConsumeParam.setAppointmentTime(eticketConsumeDomainRequest.getAppointmentTime());
        }
        // 电子凭证类型，跟商家想要入驻的类目相关联，非必传，默认 DINING_OPEN_TICKET
        eticketConsumeParam.setEticketType(eticketConsumeDomainRequest.getEticketType());
        // 扩展信息(JSON格式)
        eticketConsumeParam.setExt(eticketConsumeDomainRequest.getExt());
        // 鉴权Token，订单操作维度，在发码通知接口中下发
        eticketConsumeParam.setToken(eticketConsumeDomainRequest.getToken());
        // 核销序列号，对应某一次核销行为，必填！冲正时必须带上该值！
        eticketConsumeParam.setSeriallNum(eticketConsumeDomainRequest.getSeriallNum());
        //消费门店的poi信息，必须是快手的POI，如果是百度、腾讯等图商的POI，请先用POI转换接口，将图商POI转换成快手POI
        eticketConsumeParam.setConsumePoiId(eticketConsumeDomainRequest.getConsumePoiId());

        return eticketConsumeParam;
    }

    private ReverseETicketParam buildReverseETicketParam(ReverseETicketDomainReq reverseETicketDomainReq) {
        ReverseETicketParam reverseETicketParam = new ReverseETicketParam();

        reverseETicketParam.setOid(reverseETicketDomainReq.getOid());
        reverseETicketParam.setEticketType(reverseETicketDomainReq.getEticketType());
        reverseETicketParam.setEtickets(reverseETicketDomainReq.getEtickets().stream().map(this::buildEticket).collect(
                Collectors.toList()));
        reverseETicketParam.setSerialNum(reverseETicketDomainReq.getSerialNum());
        reverseETicketParam.setReason(reverseETicketDomainReq.getReason());
        reverseETicketParam.setExt(reverseETicketDomainReq.getExt());
        reverseETicketParam.setToken(reverseETicketDomainReq.getToken());

        return reverseETicketParam;
    }

    private QueryOrderDetailParam buildQueryOrderDetailReq(Long oid) {
        QueryOrderDetailParam queryOrderDetailParam = new QueryOrderDetailParam();
        queryOrderDetailParam.setOid(oid);

        return queryOrderDetailParam;
    }

    private QueryOrderFeeParam buildQueryOrderDeeReq(Long oid) {
        QueryOrderFeeParam queryOrderFeeParam = new QueryOrderFeeParam();
        queryOrderFeeParam.setOid(oid);

        return queryOrderFeeParam;
    }

    private DestroyETicketParam buildDestroyETicketReq(DestroyETicketDomainReq destroyETicketDomainReq) {
        DestroyETicketParam destroyETicketParam = new DestroyETicketParam();
        destroyETicketParam.setOid(destroyETicketDomainReq.getOid());
        destroyETicketParam.setEtickets(destroyETicketDomainReq.getEtickets().stream().map(this::buildEticket).collect(
                Collectors.toList()));
        destroyETicketParam.setReason(destroyETicketDomainReq.getReason());
        destroyETicketParam.setEticketType(destroyETicketDomainReq.getEticketType());
        destroyETicketParam.setExt(destroyETicketDomainReq.getExt());
        destroyETicketParam.setToken(destroyETicketDomainReq.getToken());
        destroyETicketParam.setSerialNum(destroyETicketDomainReq.getSerialNum());

        return destroyETicketParam;
    }

    private Eticket buildEticket(EticketDomain eticketDomain) {
        Eticket eticketRq = new Eticket();
        //电子凭证id
        eticketRq.setId(eticketDomain.getId());
        //电子凭证Code
        eticketRq.setCode(eticketDomain.getCode());
        //核销数量
        eticketRq.setNum(eticketDomain.getNum());
        //销毁总货值
        eticketRq.setGoodsValue(eticketDomain.getGoodsValue());
        //核销状态
        eticketRq.setStatus(eticketDomain.getStatus());

        return eticketRq;
    }

    public void sendEticketCallback(EticketSendDomainReq request) {
        List<SendCallbackETicket> etickets = generateEtickets(request);
        EticketSendCallbackProxyRequest proxyRequest = buildProxyRequest(request, etickets);
        kwaiETicketAdaptorService.sendCallback(proxyRequest);
    }

    private List<SendCallbackETicket> generateEtickets(EticketSendDomainReq request) {
        List<SendCallbackETicket> eTickets = new ArrayList<>();
        SendCallbackETicket eTicket = new SendCallbackETicket();
        String eTicketId = UUID.randomUUID().toString().substring(0, 8); // 根据自己的规则生成券id
        eTicketIdForTest = eTicketId; // 测试使用，正式使用时可以去掉
        eTicket.setId(eTicketId);
        eTicket.setNum(request.getNum());
        eTicket.setGoodsValue(1L);
        eTicket.setValidStartTime(request.getCertStartTime());
        eTicket.setValidEndTime(request.getCertEndTime());
        eTickets.add(eTicket);
        return eTickets;
    }

    // todo:发码数量信息通过查询订单接口获取
    // todo:ETicket 需要根据订单接口填充相关的ETicket对象
    // todo: goodValue：设置成1，用来表示券的数量和 totalGoodValue一般设置成订单上的商品数量，
    public EticketSendCallbackProxyRequest buildProxyRequest(EticketSendDomainReq domainRequest,
            List<SendCallbackETicket> etickets) {
        OrderDetailDTO orderDetailDTO = orderDomainService.queryOrderDetail(Long.valueOf(domainRequest.getOid()));
        EticketSendCallbackProxyRequest proxyRequest = new EticketSendCallbackProxyRequest();
        proxyRequest.setOid(domainRequest.getOid());
        proxyRequest.setEticketType(domainRequest.getEticketType());
        proxyRequest.setSendNum(orderDetailDTO.getOrderItemInfo().getNum());
        //所有卡券的goodValue相加必须等于totalGoodsValue，用于计算货值比。卡券等值的话一般建议总货值totalGoodsValue设置为订单上的商品数量，etickets里每张卡券的goodValue
        // 设置为1。
        proxyRequest.setTotalGoodsValue(Long.valueOf(orderDetailDTO.getOrderItemInfo().getNum()));
        proxyRequest.setEtickets(etickets);
        proxyRequest.setSendType(domainRequest.getSendType());
        proxyRequest.setToken(domainRequest.getToken());
        return proxyRequest;
    }

    private ListRefundOrderParam buildListRefundOrderReq(DestroyETicketDomainReq request) {
        ListRefundOrderParam listRefundOrderParam = new ListRefundOrderParam();
        // isv根据自己业务需求，构造指定退款单参数

        return listRefundOrderParam;
    }

    // isv 根据快手的发货请求落库操作
    private void sendTicket(EticketSendDomainReq request, OrderDetail orderDetail, OrderFeeInfo orderFeeInfo) {

    }

    // isv 根据快手的销毁券请求落库操作
    private void destroyTicket(DestroyETicketDomainReq request, OrderDetail orderDetail,
            MerchantRefundListDataView merchantRefundListDataView) {

    }
}
