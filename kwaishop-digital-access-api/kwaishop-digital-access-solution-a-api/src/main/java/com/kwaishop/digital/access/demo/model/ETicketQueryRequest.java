package com.kwaishop.digital.access.demo.model;

import com.kwaishop.digital.access.demo.domain.eticket.request.EticketQueryDomainReq;

import lombok.Data;

/**
 * @author zhangkwei
 * Created on 2022-05-17
 */
@Data
public class ETicketQueryRequest {
    private String oid;
    private String eticketId;
    private String eticketType;
    private String sendType;
    private String spCode;

    public EticketQueryDomainReq toDomainRequest() {
        EticketQueryDomainReq eticketQueryDomainReq = new EticketQueryDomainReq();
        eticketQueryDomainReq.setOid(oid);
        eticketQueryDomainReq.setEticketId(eticketId);
        eticketQueryDomainReq.setEticketType(eticketType);
        eticketQueryDomainReq.setSendType(sendType);
        eticketQueryDomainReq.setSpCode(spCode);

        return eticketQueryDomainReq;
    }
}
