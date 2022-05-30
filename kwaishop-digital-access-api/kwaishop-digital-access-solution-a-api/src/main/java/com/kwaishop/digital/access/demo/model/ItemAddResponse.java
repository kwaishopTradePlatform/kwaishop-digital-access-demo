package com.kwaishop.digital.access.demo.model;

import java.util.ArrayList;
import java.util.List;

import com.kwaishop.digital.access.demo.domain.item.model.SkuInfoBriefDTO;
import com.kwaishop.digital.access.demo.domain.item.response.ItemAddDomainResponse;

import lombok.Data;

@Data
public class ItemAddResponse {
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
    private List<SkuInfoBriefVO> skuIdMapping;

    public ItemAddResponse(){}

    public ItemAddResponse(ItemAddDomainResponse itemAddDomainResponse) {
        kwaiItemId = itemAddDomainResponse.getKwaiItemId();
        relItemId = itemAddDomainResponse.getRelItemId();
        skuIdMapping = new ArrayList<>();
        for (SkuInfoBriefDTO skuInfoBriefDTO : itemAddDomainResponse.getSkuIdMapping()) {
            skuIdMapping.add(new SkuInfoBriefVO(skuInfoBriefDTO));
        }
    }
}