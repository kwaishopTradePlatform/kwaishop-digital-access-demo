package com.kwaishop.digital.access.demo.proxy.response;

import lombok.Data;

/**
 * @author zhangkwei
 * Created on 2022-05-24
 */
@Data
public class OrderAddress {
    private String province;
    private String city;
    private Integer cityCode;
    private Integer provinceCode;
}
