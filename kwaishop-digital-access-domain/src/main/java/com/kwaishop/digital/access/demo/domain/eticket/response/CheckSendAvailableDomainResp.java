package com.kwaishop.digital.access.demo.domain.eticket.response;

import com.kwaishop.digital.access.demo.proxy.response.CheckSendAvailableProxyResp;

import lombok.Data;

/**
 * @author zhangyiying
 * Created on 2022-05-25
 */
@Data
public class CheckSendAvailableDomainResp {
    /**
     * 订单id
     */
    private String oid;

    /**
     * 卖家id
     */
    private String sellerId;

    /**
     * 发货数量
     */
    private Integer num;

    /**
     * 快手商品id
     */
    private String itemId;

    /**
     * 快手sku id
     */
    private String skuId;

    /**
     * 商品标题
     */
    private String itemTitle;

    /**
     * 发货类型
     */
    private String sendType;

    /**
     * 电子凭证类型
     */
    private String eticketType;

    /**
     * 回调token
     */
    private String token;

    /**
     * 拓展信息
     */
    private String ext;

    /**
     * 物流编号
     */
    private String expressCode;

    /**
     * 物流单
     */
    private String expressNo;

    /**
     * 服务商code
     */
    private String spCode;

    /**
     * 过期类型
     */
    private Integer certExpireType;

    /**
     * 有效期开始时间
     */
    private Long certStartTime;

    /**
     * 有效期结束时间
     */
    private Long certEndTime;

    /**
     * 过期天数
     */
    private Integer certExpDays;

    public CheckSendAvailableDomainResp(CheckSendAvailableProxyResp proxyResp) {
        this.oid = proxyResp.getOid();
        this.sellerId = proxyResp.getSellerId();
        this.num = proxyResp.getNum();
        this.itemId = proxyResp.getItemId();
        this.skuId = proxyResp.getSkuId();
        this.itemTitle = proxyResp.getItemTitle();
        this.sendType = proxyResp.getSendType();
        this.eticketType = proxyResp.getEticketType();
        this.token = proxyResp.getToken();
        this.ext = proxyResp.getExt();
        this.expressCode = proxyResp.getExpressCode();
        this.expressNo = proxyResp.getExpressNo();
        this.spCode = proxyResp.getSpCode();
        this.certExpireType = proxyResp.getCertExpireType();
        this.certStartTime = proxyResp.getCertStartTime();
        this.certEndTime = proxyResp.getCertEndTime();
        this.certExpDays = proxyResp.getCertExpDays();
    }
}
