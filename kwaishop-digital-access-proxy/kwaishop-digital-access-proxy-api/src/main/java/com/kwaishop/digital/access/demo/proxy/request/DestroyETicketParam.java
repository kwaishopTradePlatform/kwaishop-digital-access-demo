package com.kwaishop.digital.access.demo.proxy.request;

import java.util.List;

import lombok.Data;

/**
 * @author zhangkwei
 * Created on 2022-05-19
 */
@Data
public class DestroyETicketParam {
    private String oid;
    private List<Eticket> etickets;
    private String reason;
    private String eticketType;
    private String ext;
    private String token;
    private String serialNum;
}
