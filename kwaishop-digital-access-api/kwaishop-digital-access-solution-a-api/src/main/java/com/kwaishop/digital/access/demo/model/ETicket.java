package com.kwaishop.digital.access.demo.model;

import com.kwaishop.digital.access.demo.domain.eticket.request.EticketDomain;

import lombok.Data;

/**
 * @author zhangkwei
 * Created on 2022-05-17
 */
@Data
public class ETicket {
    private String id;
    private String code;
    private Integer num;
    private Long goodsValue;
    private String status;

    public EticketDomain toDomain() {
        EticketDomain eticketDomain = new EticketDomain();
        eticketDomain.setId(id);
        eticketDomain.setCode(code);
        eticketDomain.setNum(num);
        eticketDomain.setGoodsValue(goodsValue);
        eticketDomain.setStatus(status);
        return eticketDomain;
    }
}
