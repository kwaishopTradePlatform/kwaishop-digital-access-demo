package com.kwaishop.digital.access.demo.domain.eticket.request;

import lombok.Data;

/**
 * @author zhangyiying
 * Created on 2022-05-17
 */
@Data
public class EticketQueryDomainReq {
    /**
     * 快手订单号
     */
    private String oid;

    /**
     * 电子凭证id
     */
    private String eticketId;

    /**
     * 业务类型
     */
    private String eticketType;

    /**
     * 发卡类型：虚拟卡-virtual （默认）实体卡-delivery
     */
    private String sendType;

    /**
     * 服务商code
     */
    private String spCode;
}
