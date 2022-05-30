package com.kwaishop.digital.access.demo.model;

import java.util.List;
import java.util.stream.Collectors;

import com.kwaishop.digital.access.demo.domain.eticket.request.EticketDomain;
import com.kwaishop.digital.access.demo.domain.eticket.request.ReverseETicketDomainReq;

import lombok.Data;

/**
 * @author zhangkwei
 * Created on 2022-05-17
 */
@Data
public class ETicketReverseRequest {
    private Long oid;
    private String eticketType;
    private List<ETicket> etickets;
    private String serialNum;
    private String reason;
    private String ext;
    private String token;

    public ReverseETicketDomainReq toDomainReq() {
        ReverseETicketDomainReq domainReq = new ReverseETicketDomainReq();
        domainReq.setOid(oid);
        domainReq.setEticketType(eticketType);
        domainReq.setEtickets(etickets.stream().map(this::buildEticketDomain).collect(Collectors.toList()));
        domainReq.setSerialNum(serialNum);
        domainReq.setReason(reason);
        domainReq.setExt(ext);
        domainReq.setToken(token);
        return domainReq;
    }

    private EticketDomain buildEticketDomain(ETicket reverseETicket) {
        EticketDomain eticketDomain = new EticketDomain();
        eticketDomain.setId(reverseETicket.getId());
        return eticketDomain;
    }
}
