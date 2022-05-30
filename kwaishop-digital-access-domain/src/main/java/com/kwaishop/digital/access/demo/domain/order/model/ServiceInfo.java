package com.kwaishop.digital.access.demo.domain.order.model;

import lombok.Data;

/**
 * @author zhangyiying
 * Created on 2022-05-17
 */
@Data
public class ServiceInfo {
    private String serviceRule;
    private Boolean freight;
    private Integer freightProviderType;
    private Boolean firstOrderGuarantee;
    private Integer instantDelivery;
    private Boolean instantRefund;
    private CompensateFake compensateFake;
    private ServiceRuleInfoDTO serviceRuleInfo;
}
