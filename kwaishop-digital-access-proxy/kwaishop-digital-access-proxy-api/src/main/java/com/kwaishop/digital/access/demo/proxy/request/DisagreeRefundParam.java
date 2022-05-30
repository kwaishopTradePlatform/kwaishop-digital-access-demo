package com.kwaishop.digital.access.demo.proxy.request;

import lombok.Data;

/**
 * @author zhangkwei
 * Created on 2022-05-16
 */
@Data
public class DisagreeRefundParam {
    private Long refundId;

    private Integer sellerDisagreeReason;

    private String sellerDisagreeDesc;

    private String[] sellerDisagreeImages;

    private Integer status;

    private Integer negotiateStatus;
}
