package com.kwaishop.digital.access.demo.model;

import java.util.ArrayList;
import java.util.List;

import com.kwaishop.digital.access.demo.domain.eticket.request.EticketConsumeDomainReq;
import com.kwaishop.digital.access.demo.domain.eticket.request.EticketDomain;

import lombok.Data;

/**
 * @author zhangkwei
 * Created on 2022-05-17
 */
@Data
public class ETicketConsumeRequest {
    private Long oid;
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
        domainReq.setOid(oid.toString());
        List<EticketDomain> eticketDomains = new ArrayList<>();
        for (ETicket eTicket : etickets) {
            eticketDomains.add(eTicket.toDomainRequest());
        }
        domainReq.setEtickets(eticketDomains);
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
}
