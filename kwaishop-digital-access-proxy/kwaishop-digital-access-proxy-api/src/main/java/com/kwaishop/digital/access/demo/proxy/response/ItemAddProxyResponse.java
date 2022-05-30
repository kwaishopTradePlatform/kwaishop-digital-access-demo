package com.kwaishop.digital.access.demo.proxy.response;

import java.util.List;

import lombok.Data;

/**
 * @author zhangyiying
 * Created on 2022-05-24
 */
@Data
public class ItemAddProxyResponse {
    /**
     * 快手商品id
     */
    private Long kwaiItemId;

    /**
     * 外部商品id，仅供记录外部商品和快手商品对应关系
     */
    private Long relItemId;

    /**
     * sku列表
     */
    private List<SkuInfoBriefResponseParam> skuIdMapping;
}
