package com.kwaishop.digital.access.demo.model;

import java.util.List;
import java.util.stream.Collectors;

import com.kwaishop.digital.access.demo.domain.eticket.request.DestroyETicketDomainReq;
import com.kwaishop.digital.access.demo.domain.eticket.request.EticketDomain;

import lombok.Data;

/**
 * @author zhangkwei
 * Created on 2022-05-17
 */
@Data
public class ETicketDestroyRequest {
    private String oid;
    private List<ETicket> etickets;
    private String reason;
    private String eticketType;
    private String ext;
    private String token;

    public DestroyETicketDomainReq toDomainRequest() {
        DestroyETicketDomainReq domainReq = new DestroyETicketDomainReq();
        domainReq.setOid(oid);
        domainReq.setEtickets(etickets.stream().map(this::toEticketDomain).collect(Collectors.toList()));
        domainReq.setReason(reason);
        domainReq.setEticketType(eticketType);
        domainReq.setExt(ext);
        domainReq.setToken(token);
        return domainReq;
    }

    private EticketDomain toEticketDomain(ETicket eTicket) {
        EticketDomain eticketDomain = new EticketDomain();
        eticketDomain.setId(eTicket.getId());
        eticketDomain.setCode(eTicket.getCode());
        eticketDomain.setNum(eTicket.getNum());
        eticketDomain.setGoodsValue(eTicket.getGoodsValue());
        eticketDomain.setStatus(eTicket.getStatus());

        return eticketDomain;
    }

}
