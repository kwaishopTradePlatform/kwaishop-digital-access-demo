package com.kwaishop.digital.access.demo.proxy.request;

import lombok.Data;

/**
 * @author zhangyiying
 * Created on 2022-05-16
 */
@Data
public class SendCallbackETicket {
    /**
     * 电子凭证id
     */
    private String id;

    /**
     * 实体卡
     */
    private String code;

    /**
     * 数量
     */
    private Integer num;

    /**
     * 有效期开始时间
     */
    private Long validStartTime;

    /**
     * 有效期结束时间
     */
    private Long validEndTime;

    /**
     * 货值
     */
    private Long goodsValue;

    /**
     * 卡券密码
     */
    private Long eticketSecretKey;
}
