package com.kwaishop.digital.access.demo.domain.eticket.request;

import lombok.Data;

/**
 * @author zhangyiying
 * Created on 2022-05-25
 */
@Data
public class CheckSendAvailableDomainReq {

    private Long sellerId;

    private Long buyerId;

    private Long oid;
}
