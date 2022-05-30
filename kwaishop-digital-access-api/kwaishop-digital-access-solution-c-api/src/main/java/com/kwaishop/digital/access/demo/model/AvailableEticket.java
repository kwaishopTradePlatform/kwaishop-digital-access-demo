package com.kwaishop.digital.access.demo.model;

import com.kwaishop.digital.access.demo.domain.eticket.request.AvailableEticketDomain;

import lombok.Data;

/**
 * @author pengjianfei
 * Created on 2022-05-19
 */
@Data
public class AvailableEticket {

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

    public AvailableEticketDomain toDomainRequest() {
        AvailableEticketDomain availableEticketDomain = new AvailableEticketDomain();

        availableEticketDomain.setId(id);
        availableEticketDomain.setCode(code);
        availableEticketDomain.setNum(num);

        return availableEticketDomain;
    }
}
