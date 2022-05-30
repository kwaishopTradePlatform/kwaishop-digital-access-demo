package com.kwaishop.digital.access.demo.proxy.request;

import lombok.Data;

/**
 * @author zhangkwei
 * Created on 2022-05-16
 */
@Data
public class UpdateShelfItemStatusParam {
    private Long kwaiItemId;
    private Integer shelfStatus;
}
