package com.kwaishop.digital.access.demo.domain.eticket.model;

import java.util.List;

import lombok.Data;

/**
 * @author zhangyiying
 * Created on 2022-05-17
 */
@Data
public class EticketsDetailDTO {
    /**
     * 快手订单号
     */
    private String oid;

    /**
     * 发卡类型： 虚拟卡-virtual 实体卡-delivery
     *
     * @see com.kwaishop.digital.access.demo.domain.common.enums.SendTypeEnum
     */
    private String sendType;

    /**
     * 业务类型
     */
    private String eticketType;

    /**
     * 电子凭证列表
     */
    private List<ETicket> etickets;

    /**
     * 已经发货数量，适配服务商多次发货场景
     */
    private Integer sendNum;

    /**
     * 拓展字段
     */
    private String ext;
}
