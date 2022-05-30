package com.kwaishop.digital.access.demo.model;

import com.kwaishop.digital.access.demo.domain.item.request.SkuStockDeductDomain;

import lombok.Data;

/**
 * @author zhangkwei
 * Created on 2022-05-18
 */
@Data
public class SkuStockDeduct {
    private String num;
    private String skuId;

    public SkuStockDeductDomain toDomain() {
        SkuStockDeductDomain stockDeductDomain = new SkuStockDeductDomain();
        stockDeductDomain.setSkuId(skuId);
        stockDeductDomain.setNum(num);
        return stockDeductDomain;
    }
}
