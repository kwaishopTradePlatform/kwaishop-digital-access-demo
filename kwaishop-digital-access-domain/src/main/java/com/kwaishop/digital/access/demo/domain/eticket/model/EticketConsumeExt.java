package com.kwaishop.digital.access.demo.domain.eticket.model;

import lombok.Data;

/**
 * @author zhangyiying
 * Created on 2022-05-17
 */
@Data
public class EticketConsumeExt {
    /**
     * 核销流水号
     */
    private String serialNum;

    /**
     *
     */
    private String consumeType;

    /**
     *
     */
    private Long consumeTime;

    /**
     *
     */
    private Long appointmentTime;

    /**
     *
     */
    private String storeName;
    private String storeAddress;
    private String expressCode;
    private String expressName;
    private String expressNo;
    private String reason;
}
