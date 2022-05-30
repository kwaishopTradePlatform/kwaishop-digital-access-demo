package com.kwaishop.digital.access.demo.proxy.request;

import java.util.List;

import lombok.Data;

/**
 * @author zhangkwei
 * Created on 2022-05-16
 */
@Data
public class SkuStockOrder {
    private String orderId;

    private List<SkuStockDeduct> skuStock;
}
