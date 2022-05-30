package com.kwaishop.digital.access.demo.model;

import java.util.List;
import java.util.stream.Collectors;

import com.kwaishop.digital.access.demo.domain.eticket.request.EticketConsumeDomainReq;
import com.kwaishop.digital.access.demo.domain.eticket.request.EticketDomain;

import lombok.Data;

/**
 * @author zhangkwei
 * Created on 2022-05-17
 */
@Data
public class ETicketConsumeRequest {
    private String oid;
    private List<ETicket> etickets;
    private Long consumeTime;
    private String status;
    private String consumeType;
    private String storeName;
    private String storeAddress;
    private String expressNo;
    private String expressCode;
    private String appointmentTime;
    private String eticketType;
    private String ext;
    private String token;
    private String seriallNum;
    private Long consumePoiId;

    public EticketConsumeDomainReq toDomainRequest() {
        EticketConsumeDomainReq domainReq = new EticketConsumeDomainReq();
        domainReq.setOid(oid);
        domainReq.setEtickets(etickets.stream().map(this::toETicketDomain).collect(Collectors.toList()));
        domainReq.setConsumeTime(consumeTime);
        domainReq.setStatus(status);
        domainReq.setConsumeType(consumeType);
        domainReq.setStoreName(storeName);
        domainReq.setStoreAddress(storeAddress);
        domainReq.setExpressNo(expressNo);
        domainReq.setExpressCode(expressCode);
        domainReq.setAppointmentTime(appointmentTime);
        domainReq.setEticketType(eticketType);
        domainReq.setExt(ext);
        domainReq.setToken(token);
        domainReq.setSeriallNum(seriallNum);
        domainReq.setConsumePoiId(consumePoiId);

        return domainReq;
    }

    public EticketDomain toETicketDomain(ETicket eTicket) {
        EticketDomain eticketDomain = new EticketDomain();
        eticketDomain.setId(eTicket.getId());
        eticketDomain.setCode(eTicket.getCode());
        eticketDomain.setNum(eTicket.getNum());
        eticketDomain.setGoodsValue(eTicket.getGoodsValue());
        eticketDomain.setStatus(eTicket.getStatus());
        return eticketDomain;
    }
}
