package com.kwaishop.digital.access.demo.model;

import java.util.ArrayList;
import java.util.List;

import com.kwaishop.digital.access.demo.domain.eticket.request.EticketDomain;
import com.kwaishop.digital.access.demo.domain.eticket.request.ReverseETicketDomainReq;

import lombok.Data;

@Data
public class ReverseETicketRequest {
    private Long oid;
    private String eticketType;
    private List<ETicket> etickets;
    private String serialNum;
    private String reason;
    private String ext;
    private String token;

    public ReverseETicketDomainReq toDomainReq() {
        ReverseETicketDomainReq reverseETicketDomainReq = new ReverseETicketDomainReq();
        reverseETicketDomainReq.setOid(oid);
        reverseETicketDomainReq.setEticketType(eticketType);
        List<EticketDomain> eticketDomains = new ArrayList<>();
        for (ETicket eTicket : etickets) {
            eticketDomains.add(eTicket.toDomain());
        }
        reverseETicketDomainReq.setEtickets(eticketDomains);
        reverseETicketDomainReq.setSerialNum(serialNum);
        reverseETicketDomainReq.setReason(reason);
        reverseETicketDomainReq.setExt(ext);
        reverseETicketDomainReq.setToken(token);
        return reverseETicketDomainReq;
    }
}