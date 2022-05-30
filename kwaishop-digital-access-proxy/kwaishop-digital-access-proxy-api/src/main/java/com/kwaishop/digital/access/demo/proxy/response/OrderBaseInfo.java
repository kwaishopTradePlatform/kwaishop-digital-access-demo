package com.kwaishop.digital.access.demo.proxy.response;

import lombok.Data;

/**
 * @author zhangkwei
 * Created on 2022-05-24
 */
@Data
public class OrderBaseInfo {
    private Long oid;
    private String buyerNick;
    private Long payTime;
    private String sellerNick;
}
