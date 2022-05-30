package com.kwaishop.digital.access.demo.domain.item.request;

import java.util.List;


import lombok.Data;

/**
 * @author zhangkwei
 * Created on 2022-05-17
 */
@Data
public class DeductItemStockDomainReq {
    private String province;
    private String city;
    private String area;
    private List<SkuStockOrderDomain> orderSkuStockList;
    private String number;
    private String orderId;
    private String skuId;
    private String address;
    private String ksSkuId;
}
