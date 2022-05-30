package com.kwaishop.digital.access.demo.proxy.response;

import lombok.Data;

/**
 * @author pengjianfei
 * Created on 2022-05-20
 */
@Data
public class AvailableEticketDetail {
    private String code;

    private Long itemId;

    private Integer num;

    private String id;

    private Long buyerId;

    private Long leftNum;

    private String eticketType;

    private Long orderId;

    private Long sellerId;

    private String displayStatus;

    private String token;

}
