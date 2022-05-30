package com.kwaishop.digital.access.demo.proxy.response;

import lombok.Data;

/**
 * @author zhangkwei
 * Created on 2022-05-16
 */
@Data
public class MerchantRefundInfoView {
    private Long oid;
    private Long refundId;
    private Integer handlingWay;
    private Integer negotiateStatus;
    private Long refundFee;
    private Long skuId;
    private Integer refundReason;
    private Integer status;
    private Long buyerId;
    private Integer refundType;
    private Long sellerId;
    private String refundDesc;
    private Long submitTime;
    private Long relItemId;
    private Long negotiateUpdateTime;
    private Long updateTime;
    private Long createTime;
    private Long relSkuId;
    private String skuNick;
    private Long logisticsId;
    private Long endTime;
    private Long itemId;
    private Integer receiptStatus;
}
