package com.kwaishop.digital.access.demo.domain.item.request;

import java.util.List;

import lombok.Data;

@Data
public class SkuStockOrderDomain {
    private String orderId;

    private List<SkuStockDeductDomain> skuStock;
}