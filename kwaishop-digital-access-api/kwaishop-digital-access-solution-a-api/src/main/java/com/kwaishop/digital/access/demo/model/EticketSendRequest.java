package com.kwaishop.digital.access.demo.model;

import com.kwaishop.digital.access.demo.domain.eticket.request.EticketSendDomainReq;

import lombok.Data;

/**
 * @author zhangyiying
 * Created on 2022-05-12
 */
@Data
public class EticketSendRequest {
    /**
     * 快手订单号
     */
    private String oid;

    /**
     * 卖家快手id
     */
    private String sellerId;

    /**
     * 发货数量，一般对应订单的下单数量
     */
    private Integer num;

    /**
     * 快手体系商品ID
     */
    private String itemId;

    /**
     * 快手体系商品sku id
     */
    private String skuId;

    /**
     * 快手体系内的商品名称
     */
    private String itemTitle;

    /**
     * 发卡类型：虚拟卡-virtual 实体卡-delivery
     */
    private String sendType;

    /**
     * 电子凭证类型，默认DINING_OPEN_TICKET
     */
    private String eticketType;

    /**
     * 访问授权token
     */
    private String token;

    /**
     * 扩展信息
     */
    private String ext;

    /**
     * 实体卡物流公司
     */
    private String expressCode;

    /**
     * 实体卡物流单号
     */
    private String expressNo;

    /**
     * 供应商code，由快手平台注册生成
     */
    private String spCode;

    /**
     * FIXED_START_END("固定起止时间", 1), FIXED_END("购买成功后固定结束时间", 2), FIXED_PERIOD_DAY("购买成功后固定有效天数", 3);
     */
    private Integer certExpireType;

    /**
     * 有效期起始时间
     */
    private Long certStartTime;

    /**
     * 有效期结束时间
     */
    private Long certEndTime;

    /**
     * 超时天数
     */
    private Integer certExpDays;

    public EticketSendDomainReq toDomainRequest() {
        EticketSendDomainReq eticketSendDomainRequest = new EticketSendDomainReq();
        eticketSendDomainRequest.setOid(oid);
        eticketSendDomainRequest.setSellerId(sellerId);
        eticketSendDomainRequest.setNum(num);
        eticketSendDomainRequest.setItemId(itemId);
        eticketSendDomainRequest.setSkuId(skuId);
        eticketSendDomainRequest.setItemTitle(itemTitle);
        eticketSendDomainRequest.setSendType(sendType);
        eticketSendDomainRequest.setEticketType(eticketType);
        eticketSendDomainRequest.setToken(token);
        eticketSendDomainRequest.setExt(ext);
        eticketSendDomainRequest.setExpressCode(expressCode);
        eticketSendDomainRequest.setExpressNo(expressNo);
        eticketSendDomainRequest.setSpCode(spCode);
        eticketSendDomainRequest.setCertExpireType(certExpireType);
        eticketSendDomainRequest.setCertStartTime(certStartTime);
        eticketSendDomainRequest.setCertEndTime(certEndTime);
        eticketSendDomainRequest.setCertExpDays(certExpDays);
        return eticketSendDomainRequest;
    }
}
