package com.kwaishop.digital.access.demo.proxy.request;

import java.util.List;

import lombok.Data;

/**
 * @author zhangkwei
 * Created on 2022-05-16
 */
@Data
public class DeductItemStockParam {
    private String stockPartner;

    private String province;

    private String city;

    private String area;

    private List<SkuStockOrder> orderSkuStockList;

    private String number;

    private String orderId;

    private String skuId;

    private String address;

    private String ksSkuId;
}
