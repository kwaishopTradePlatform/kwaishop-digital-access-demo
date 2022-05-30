package com.kwaishop.digital.access.demo.domain.eticket.request;

import java.util.List;

import lombok.Data;

/**
 * @author zhangkwei
 * Created on 2022-05-17
 */
@Data
public class ReverseETicketDomainReq {
    private Long oid;
    private String eticketType;
    private List<EticketDomain> etickets;
    private String serialNum;
    private String reason;
    private String ext;
    private String token;
}
