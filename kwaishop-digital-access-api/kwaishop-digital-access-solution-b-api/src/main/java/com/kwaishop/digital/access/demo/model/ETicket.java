package com.kwaishop.digital.access.demo.model;

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
}
