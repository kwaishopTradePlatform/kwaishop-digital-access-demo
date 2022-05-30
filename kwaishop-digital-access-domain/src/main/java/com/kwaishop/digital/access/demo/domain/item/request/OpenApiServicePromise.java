package com.kwaishop.digital.access.demo.domain.item.request;

import lombok.Data;

/**
 * @author zhangyiying
 * Created on 2022-05-18
 */
@Data
public class OpenApiServicePromise {
    /**
     * 坏了包退
     */
    private Boolean freshRotRefund;

    /**
     * 破损包退
     */
    private Boolean brokenRefund;

    /**
     * 破损包退
     */
    private Boolean allergyRefund;
}
