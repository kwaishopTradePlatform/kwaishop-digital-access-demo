package com.kwaishop.digital.access.demo.proxy.response;

import java.util.List;

import lombok.Data;

/**
 * @author zhangkwei
 * Created on 2022-05-16
 */
@Data
public class MerchantRefundListDataView {
    private Long currentPage;
    private Integer pageSize;
    private Long totalPage;
    private Long totalSize;
    private Long beginTime;
    private Long endTime;
    private String pcursor;
    private List<MerchantRefundInfoView> refundOrderInfoList;
}
