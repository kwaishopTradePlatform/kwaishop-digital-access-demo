package com.kwaishop.digital.access.demo.proxy.request;

import java.util.List;

import lombok.Data;

/**
 * @author zhangkwei
 * Created on 2022-05-16
 */
@Data
public class ReverseETicketParam {
    private Long oid;

    private String eticketType;

    private List<Eticket> etickets;

    private String serialNum;

    private String reason;

    private String ext;

    private String token;
}

