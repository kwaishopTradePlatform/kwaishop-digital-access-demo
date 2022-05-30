package com.kwaishop.digital.access.demo.model;

import com.kwaishop.digital.access.demo.domain.item.model.SkuInfoBriefDTO;
import com.kwaishop.digital.access.demo.proxy.response.SkuInfoBriefResponseParam;

import lombok.Data;

/**
 * @author zhangyiying
 * Created on 2022-05-24
 */
@Data
public class SkuInfoBriefVO {
    /**
     * 快手商品sku id
     */
    private Long kwaiSkuId;

    /**
     * 外部商品 Sku id，仅供记录外部商品sku和快手商品sku对应关系
     */
    private Long relSkuId;

    /**
     * skuNick，sku编码必须30个字符以内
     */
    private String skuNick;

    public SkuInfoBriefVO(SkuInfoBriefDTO skuInfoBriefResponseParam) {
        kwaiSkuId = skuInfoBriefResponseParam.getKwaiSkuId();
        relSkuId = skuInfoBriefResponseParam.getRelSkuId();
        skuNick = skuInfoBriefResponseParam.getSkuNick();
    }
}
