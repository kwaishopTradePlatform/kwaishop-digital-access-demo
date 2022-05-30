package com.kwaishop.digital.access.demo.domain.eticket.model;

import java.util.List;

import lombok.Data;

/**
 * @author zhangyiying
 * Created on 2022-05-17
 */
@Data
public class ETicket {
    /**
     * 电子凭证ID
     */
    private String id;

    /**
     * 电子凭证Code（实体卡的Code），非实体卡可不填
     */
    private String code;

    /**
     * 状态，未使用：UNUSED； 已消费(至少消费1次)：CONSUMED； 已销毁：DESTROYED
     */
    private String status;

    /**
     * 总核销数量
     */
    private Integer num;

    /**
     * 有效期-开始时间，时间戳
     */
    private Long validStartTime;

    /**
     * 有效期-结束时间，时间戳
     */
    private Long validEndTime;

    /**
     * 核销拓展信息
     */
    private List<EticketConsumeExt> eticketConsumeDetails;
}
