package com.kwaishop.digital.access.demo.proxy.request;

import java.util.List;

import lombok.Data;

/**
 * @author zhangyiying
 * Created on 2022-05-16
 */
@Data
public class EticketSendCallbackProxyRequest {
    /**
     * 快手订单号
     */
    private String oid;

    /**
     * 电子凭证列表
     */
    private List<SendCallbackETicket> etickets;

    /**
     * 已发货数量
     */
    private Integer sendNum;

    /**
     * 发卡类型： 虚拟卡-virtual 实体卡-delivery
     */
    private String sendType;

    /**
     * 实体卡物流公司
     */
    private String expressCode;

    /**
     * 实体卡物流单号
     */
    private String expressNo;

    /**
     * 电子凭证类型，跟商家想要入驻的类目相关联，非必传，默认DINING_OPEN_TICKET
     */
    private String eticketType;

    /**
     * 扩展信息(JSON格式)
     */
    private String ext;

    /**
     * 鉴权Token，订单操作维度
     */
    private String token;

    /**
     * 总货值
     */
    private Long totalGoodsValue;
}
