package com.kwaishop.digital.access.demo.proxy.request;

import lombok.Data;

/**
 * @author zhangkwei
 * Created on 2022-05-12
 */
@Data
public class ApproveRefundParam {

    private Long refundId;

    private String desc;

    private Long refundAmount;

    private Integer status;

    private Integer negotiateStatus;

    private Integer refundHandingWay;
}
