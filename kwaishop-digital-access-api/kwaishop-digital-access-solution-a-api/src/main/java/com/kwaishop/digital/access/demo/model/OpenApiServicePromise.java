package com.kwaishop.digital.access.demo.model;

import lombok.Data;

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