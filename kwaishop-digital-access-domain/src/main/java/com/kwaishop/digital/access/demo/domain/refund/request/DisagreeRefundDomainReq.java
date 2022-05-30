package com.kwaishop.digital.access.demo.domain.refund.request;

import java.util.List;

import lombok.Data;

/**
 * @author zhangkwei
 * Created on 2022-05-16
 */
@Data
public class DisagreeRefundDomainReq {
    private Long refundId;
    private Integer sellerDisagreeReason;
    private String sellerDisagreeDesc;
    private List<String> sellerDisagreeImages;
    private Integer status;
    private Integer negotiateStatus;
}
