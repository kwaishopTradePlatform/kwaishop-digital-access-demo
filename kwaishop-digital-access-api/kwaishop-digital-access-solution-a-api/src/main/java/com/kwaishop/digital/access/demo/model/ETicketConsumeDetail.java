package com.kwaishop.digital.access.demo.model;

import lombok.Data;

/**
 * @author pengjianfei
 * Created on 2022-05-24
 */
@Data
public class ETicketConsumeDetail {
    private String serialNum;
    private String consumeType;
    private Long consumeTime;
    private Long appointmentTime;
    private String storeName;
    private String storeAddress;
    private String expressCode;
    private String expressName;
    private String expressNo;
    private String reason;
}
