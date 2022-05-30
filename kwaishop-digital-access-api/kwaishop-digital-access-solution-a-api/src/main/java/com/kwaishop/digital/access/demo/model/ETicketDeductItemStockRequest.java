package com.kwaishop.digital.access.demo.model;

import java.util.ArrayList;
import java.util.List;

import com.kwaishop.digital.access.demo.domain.item.request.DeductItemStockDomainReq;
import com.kwaishop.digital.access.demo.domain.item.request.SkuStockOrderDomain;

import lombok.Data;

/**
 * @author zhangkwei
 * Created on 2022-05-18
 */
@Data
public class ETicketDeductItemStockRequest {
    private String stockPartner;
    private String province;
    private String city;
    private String area;
    private List<SkuStockOrder> orderSkuStockList;
    private String timestamp;
    private String number;
    private String orderId;
    private String skuId;
    private String address;
    private String ksSkuId;
    private String town;

    public DeductItemStockDomainReq toDomainReq() {
        DeductItemStockDomainReq domainReq = new DeductItemStockDomainReq();
        domainReq.setProvince(province);
        domainReq.setCity(city);
        domainReq.setArea(area);
        List<SkuStockOrderDomain> stockOrderDomains = new ArrayList<>();
        for (SkuStockOrder stockOrder : orderSkuStockList) {
            stockOrderDomains.add(stockOrder.toDomain());
        }
        domainReq.setOrderSkuStockList(stockOrderDomains);
        domainReq.setNumber(number);
        domainReq.setOrderId(orderId);
        domainReq.setSkuId(skuId);
        domainReq.setAddress(address);
        domainReq.setKsSkuId(ksSkuId);
        return domainReq;
    }
}
