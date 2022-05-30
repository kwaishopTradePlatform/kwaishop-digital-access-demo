package com.kwaishop.digital.access.demo.domain.refund.request;

import lombok.Data;

/**
 * @author zhangkwei
 * Created on 2022-05-13
 */
@Data
public class ApproveRefundDomainReq {
    private Long refundId;

    private String desc;

    private Long refundAmount;

    private Integer status;

    private Integer negotiateStatus;

    private Integer refundHandingWay;
}
