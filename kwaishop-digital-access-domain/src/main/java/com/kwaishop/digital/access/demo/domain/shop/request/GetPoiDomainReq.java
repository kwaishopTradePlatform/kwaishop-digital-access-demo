package com.kwaishop.digital.access.demo.domain.shop.request;

import lombok.Data;

/**
 * @author zhangkwei
 * Created on 2022-05-17
 */
@Data
public class GetPoiDomainReq {

    private Long outerPoiId;

    private Integer source;
}
