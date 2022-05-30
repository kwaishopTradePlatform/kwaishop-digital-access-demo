package com.kwaishop.digital.access.demo.model;

import java.util.ArrayList;
import java.util.List;

import com.kwaishop.digital.access.demo.domain.eticket.request.AvailableEticketDomain;
import com.kwaishop.digital.access.demo.domain.eticket.request.EticketCheckAvailDomainReq;

import lombok.Data;

/**
 * @author pengjianfei
 * Created on 2022-05-17
 */
@Data
public class EticketCheckAvailRequest {

    /**
     * 卖家编号
     */
    private Long sellerId;
    /**
     * 买家编号
     */
    private Long buyerId;
    /**
     * 订单编号
     */
    private Long orderId;
    /**
     * 电子凭证类型 eticketType
     */
    private String eticketType;
    /**
     * eTicketList
     */
    private List<AvailableEticket> eTicketList;

    public EticketCheckAvailDomainReq toDomainRequest() {
        EticketCheckAvailDomainReq eticketCheckAvailDomainReq = new EticketCheckAvailDomainReq();

        eticketCheckAvailDomainReq.setSellerId(sellerId);
        eticketCheckAvailDomainReq.setBuyerId(buyerId);
        eticketCheckAvailDomainReq.setOrderId(orderId);
        eticketCheckAvailDomainReq.setEticketType(eticketType);
        List<AvailableEticketDomain> eticketDomains = new ArrayList<>();
        for (AvailableEticket availableEticket : eTicketList) {
            eticketDomains.add(availableEticket.toDomainRequest());
        }
        eticketCheckAvailDomainReq.setETicketList(eticketDomains);

        return eticketCheckAvailDomainReq;
    }
}
