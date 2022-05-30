package com.kwaishop.digital.access.demo.model;

import java.util.ArrayList;
import java.util.List;

import com.kwaishop.digital.access.demo.domain.item.request.SkuStockDeductDomain;
import com.kwaishop.digital.access.demo.domain.item.request.SkuStockOrderDomain;

import lombok.Data;

/**
 * @author zhangkwei
 * Created on 2022-05-18
 */
@Data
public class SkuStockOrder {
    private String orderId;
    private List<SkuStockDeduct> skuStock;

    public SkuStockOrderDomain toDomain() {
        SkuStockOrderDomain stockOrderDomain = new SkuStockOrderDomain();
        stockOrderDomain.setOrderId(orderId);
        List<SkuStockDeductDomain> skuStockDeductDomains = new ArrayList<>();
        for (SkuStockDeduct stockDeduct : skuStock) {
            skuStockDeductDomains.add(stockDeduct.toDomain());
        }
        stockOrderDomain.setSkuStock(skuStockDeductDomains);
        return stockOrderDomain;
    }
}
