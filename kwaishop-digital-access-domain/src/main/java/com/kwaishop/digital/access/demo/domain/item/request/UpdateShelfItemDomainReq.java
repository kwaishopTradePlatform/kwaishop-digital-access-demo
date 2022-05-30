package com.kwaishop.digital.access.demo.domain.item.request;

import lombok.Data;

/**
 * @author zhangkwei
 * Created on 2022-05-17
 */
@Data
public class UpdateShelfItemDomainReq {
    private Long kwaiItemId;

    private Integer shelfStatus;
}
