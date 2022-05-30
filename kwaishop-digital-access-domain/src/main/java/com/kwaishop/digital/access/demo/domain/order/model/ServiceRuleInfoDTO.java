package com.kwaishop.digital.access.demo.domain.order.model;

import lombok.Data;

/**
 * @author zhangyiying
 * Created on 2022-05-17
 */
@Data
public class ServiceRuleInfoDTO {
    private String depositRule;
    private String refundRule;
    private Long deliverGoodsIntervalTime;
    private Long theDayOfDeliverGoodsTime;
    private Boolean saleFlag;
    private Long promiseDeliveryTime;
    private Boolean immediatelyOnOfflineFlag;
    private String deliveryMethod;
    private Integer supportVerification;
    private String certMerchantCode;
    private Integer certExpireType;
    private Long certStartTime;
    private Long certEndTime;
    private Long certExpDays;
}
