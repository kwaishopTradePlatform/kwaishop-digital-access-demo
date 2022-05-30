package com.kwaishop.digital.access.demo.proxy.request;

import lombok.Data;

/**
 * @author pengjianfei
 * Created on 2022-05-13
 */
@Data
public class Eticket {
    /**
     * 电子凭证ID
     */
    private String id;
    /**
     * 电子凭证Code（实体卡的Code）
     */
    private String code;
    /**
     * 核销数量(订单数量)
     */
    private Integer num;
    /**
     * 销毁的总货值
     */
    private Long goodsValue;
    /**
     * 销毁成功/失败
     */
    private String status;
}
