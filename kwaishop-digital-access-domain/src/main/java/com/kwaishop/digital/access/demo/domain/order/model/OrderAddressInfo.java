package com.kwaishop.digital.access.demo.domain.order.model;

import lombok.Data;

/**
 * @author zhangyiying
 * Created on 2022-05-17
 */
@Data
public class OrderAddressInfo {
    private String consignee;
    private String encryptedConsignee;
    private String desensitiseConsignee;
    private String mobile;
    private String encryptedMobile;
    private String desensitiseMobile;
    private Long provinceCode;
    private String province;
    private Long cityCode;
    private String city;
    private Long districtCode;
    private String district;
    private String address;
    private String encryptedAddress;
    private String desensitiseAddress;
    private RechargeAccountDTO rechargeAccount;
}
