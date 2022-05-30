package com.kwaishop.digital.access.demo.proxy.request;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author zhangyiying
 * Created on 2022-05-24
 */
@Data
@AllArgsConstructor
public class OrderMockReq {
    /**
     * 快手商品id
     */
    private Long itemId;

    /**
     * 购买数量
     */
    private Integer itemNum;
}
