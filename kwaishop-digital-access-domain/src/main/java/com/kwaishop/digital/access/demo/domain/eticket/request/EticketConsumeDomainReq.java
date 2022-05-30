package com.kwaishop.digital.access.demo.domain.eticket.request;

import java.util.List;

import lombok.Data;

/**
 * @author pengjianfei
 * Created on 2022-05-13
 */
@Data
public class EticketConsumeDomainReq {

    /**
     * 快手订单号
     */
    private String oid;
    /**
     * 电子凭证
     */
    private List<EticketDomain> etickets;
    /**
     * 核销时间(消费者核销的时间)
     */
    private Long consumeTime;
    /**
     * 核销状态: 核销成功-CONSUMED 预约成功-APPOINTED
     */
    private String status;
    /**
     * 核销方式： 默认请使用 consume，其他场景请与产研沟通,门店消费-consume， 门店自提-pickup， 物流发货-delivery， 预约发货-appointment_delivery
     */
    private String consumeType;
    /**
     * 核销门店名称(门店消费、门店自提时必传)
     */
    private String storeName;
    /**
     * 核销门店地址(门店消费、门店自提时必传)
     */
    private String storeAddress;
    /**
     * 仅在consumeType为预约发货场景下生效。 物流方式：圆通/韵达/顺丰/EMS(物流发货、预约发货方式必传)
     */
    private String expressNo;
    /**
     * 仅在consumeType为预约发货场景下生效。 快递单号(预发发货方式必传)物流发货、预约发货方式必传
     */
    private String expressCode;
    /**
     * 仅在consumeType为门店自提场景下生效。 预约时间(预约发货方式必传)
     */
    private String appointmentTime;
    /**
     * 电子凭证类型，跟商家想要入驻的类目相关联，非必传，默认 DINING_OPEN_TICKET
     */
    private String eticketType;
    /**
     * 扩展信息(JSON格式)
     */
    private String ext;
    /**
     * 鉴权Token，订单操作维度，在发码通知接口中下发
     */
    private String token;
    /**
     * 核销序列号，对应某一次核销行为，必填！冲正时必须带上该值！
     */
    private String seriallNum;
    /**
     * 消费门店的poi信息，必须是快手的POI，如果是百度、腾讯等图商的POI，请先用POI转换接口，将图商POI转换成快手POI
     */
    private Long consumePoiId;
}
