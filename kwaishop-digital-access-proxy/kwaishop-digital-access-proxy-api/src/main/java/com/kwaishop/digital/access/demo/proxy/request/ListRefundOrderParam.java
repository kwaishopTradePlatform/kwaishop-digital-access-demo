package com.kwaishop.digital.access.demo.proxy.request;

import lombok.Data;

/**
 * @author zhangkwei
 * Created on 2022-05-16
 */
@Data
public class ListRefundOrderParam {
    private Long beginTime;
    private Long endTime;
    private Integer type;
    private Integer pageSize;
    private Long currentPage;
    private Integer sort;
    private Integer queryType;
    private Integer negotiateStatus;
    private String pcursor;
    private Integer status;
}
