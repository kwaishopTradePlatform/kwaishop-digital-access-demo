package com.kwaishop.digital.access.demo.domain.item.response;

import java.util.ArrayList;
import java.util.List;

import com.kwaishop.digital.access.demo.domain.item.model.SkuInfoBriefDTO;
import com.kwaishop.digital.access.demo.proxy.response.ItemAddProxyResponse;
import com.kwaishop.digital.access.demo.proxy.response.SkuInfoBriefResponseParam;

import lombok.Data;

/**
 * @author zhangyiying
 * Created on 2022-05-24
 */
@Data
public class ItemAddDomainResponse {
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
    private List<SkuInfoBriefDTO> skuIdMapping;

    public ItemAddDomainResponse(ItemAddProxyResponse itemAddProxyResponse) {
        kwaiItemId = itemAddProxyResponse.getKwaiItemId();
        relItemId = itemAddProxyResponse.getRelItemId();
        skuIdMapping = new ArrayList<>();
        for (SkuInfoBriefResponseParam skuInfoBriefResponseParam : itemAddProxyResponse.getSkuIdMapping()) {
            skuIdMapping.add(new SkuInfoBriefDTO(skuInfoBriefResponseParam));
        }
    }
}
