package com.kwaishop.digital.access.demo.model;

import java.util.List;

import com.kwaishop.digital.access.demo.domain.eticket.model.EticketsDetailDTO;

import lombok.Data;

/**
 * @author zhangkwei
 * Created on 2022-05-17
 */
@Data
public class ETicketQueryResponse {
    private String oid;
    private String sendType;
    private String eticketType;
    private List<ETicketInfo> etickets;
    private Integer sendNum;
    private String ext;
}

