package com.kwaishop.digital.access.demo.model;

import java.util.List;

import lombok.Data;

/**
 * @author pengjianfei
 * Created on 2022-05-24
 */
@Data
public class ETicketInfo {
    private String id;
    private String code;
    private String status;
    private Integer num;
    private Long validStartTime;
    private Long validEndTime;
    private List<ETicketConsumeDetail> eticketConsumeDetails;
}
