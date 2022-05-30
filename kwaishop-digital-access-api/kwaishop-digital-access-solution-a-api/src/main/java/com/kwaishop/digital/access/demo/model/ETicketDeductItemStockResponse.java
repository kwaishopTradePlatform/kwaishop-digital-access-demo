package com.kwaishop.digital.access.demo.model;

import java.util.Map;

import lombok.Data;

/**
 * @author zhangkwei
 * Created on 2022-05-18
 */
@Data
public class ETicketDeductItemStockResponse {
    private Boolean success;
    private Map<String, String> data;
}
