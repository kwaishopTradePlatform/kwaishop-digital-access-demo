package com.kwaishop.digital.access.demo.domain.eticket.request;

import lombok.Data;

/**
 * @author pengjianfei
 * Created on 2022-05-17
 */
@Data
public class AvailableEticketDomain {
    /**
     * 电子凭证编号
     */
    private String id;
    /**
     * 电子凭证实体编号
     */
    private String code;
    /**
     * 核销数量
     */
    private Integer num;
}
