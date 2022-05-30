package com.kwaishop.digital.access.demo.domain.refund.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kwaishop.digital.access.demo.domain.refund.request.ApproveRefundDomainReq;
import com.kwaishop.digital.access.demo.domain.refund.request.DisagreeRefundDomainReq;
import com.kwaishop.digital.access.demo.domain.refund.request.ListRefundDomainReq;
import com.kwaishop.digital.access.demo.domain.refund.service.RefundDomainService;
import com.kwaishop.digital.access.demo.proxy.KwaiETicketAdaptorService;
import com.kwaishop.digital.access.demo.proxy.request.ApproveRefundParam;
import com.kwaishop.digital.access.demo.proxy.request.DisagreeRefundParam;
import com.kwaishop.digital.access.demo.proxy.request.ListRefundOrderParam;

/**
 * @author zhangkwei
 * Created on 2022-05-12
 */
@Service
public class RefundDomainServiceImpl implements RefundDomainService {

    @Autowired
    private KwaiETicketAdaptorService kwaiETicketAdaptorService;

    public boolean approveRefund(ApproveRefundDomainReq approveRefundDomainReq) {
        return kwaiETicketAdaptorService.approveRefund(buildApproveRefundReq(approveRefundDomainReq));
    }

    public void disagreeRefund(DisagreeRefundDomainReq disagreeRefundDomainReq) {
        kwaiETicketAdaptorService.disagreeRefund(buildDisagreeRefundReq(disagreeRefundDomainReq));
    }

    @Override
    public void listRefundOrder(ListRefundDomainReq listRefundDomainReq) {
        kwaiETicketAdaptorService.listRefundOrder(buildListRefundOrderReq(listRefundDomainReq));
    }

    private ApproveRefundParam buildApproveRefundReq(ApproveRefundDomainReq approveRefundDomainReq) {
        ApproveRefundParam approveRefundParam = new ApproveRefundParam();

        approveRefundParam.setRefundId(approveRefundDomainReq.getRefundId());
        approveRefundParam.setDesc(approveRefundDomainReq.getDesc());
        approveRefundParam.setRefundAmount(approveRefundDomainReq.getRefundAmount());
        approveRefundParam.setStatus(approveRefundDomainReq.getStatus());
        approveRefundParam.setNegotiateStatus(approveRefundDomainReq.getNegotiateStatus());
        approveRefundParam.setRefundHandingWay(approveRefundDomainReq.getRefundHandingWay());

        return approveRefundParam;
    }

    private DisagreeRefundParam buildDisagreeRefundReq(DisagreeRefundDomainReq disagreeRefundDomainReq) {
        DisagreeRefundParam disagreeRefundParam = new DisagreeRefundParam();
        disagreeRefundParam.setRefundId(disagreeRefundDomainReq.getRefundId());
        disagreeRefundParam.setSellerDisagreeReason(disagreeRefundDomainReq.getSellerDisagreeReason());
        disagreeRefundParam.setSellerDisagreeDesc(disagreeRefundDomainReq.getSellerDisagreeDesc());
        String[] images = new String[disagreeRefundDomainReq.getSellerDisagreeImages().size()];
        disagreeRefundDomainReq.getSellerDisagreeImages().toArray(images);
        disagreeRefundParam.setSellerDisagreeImages(images);
        disagreeRefundParam.setStatus(disagreeRefundDomainReq.getStatus());
        disagreeRefundParam.setNegotiateStatus(disagreeRefundDomainReq.getNegotiateStatus());

        return disagreeRefundParam;
    }

    private ListRefundOrderParam buildListRefundOrderReq(ListRefundDomainReq listRefundDomainReq) {
        ListRefundOrderParam listRefundOrderParam = new ListRefundOrderParam();

        listRefundOrderParam.setBeginTime(listRefundDomainReq.getBeginTime());
        listRefundOrderParam.setEndTime(listRefundDomainReq.getEndTime());
        listRefundOrderParam.setType(listRefundDomainReq.getType());
        listRefundOrderParam.setPageSize(listRefundDomainReq.getPageSize());
        listRefundOrderParam.setCurrentPage(listRefundDomainReq.getCurrentPage());
        listRefundOrderParam.setSort(listRefundDomainReq.getSort());
        listRefundOrderParam.setQueryType(listRefundDomainReq.getQueryType());
        listRefundOrderParam.setNegotiateStatus(listRefundDomainReq.getNegotiateStatus());
        listRefundOrderParam.setPcursor(listRefundDomainReq.getPcursor());
        listRefundOrderParam.setStatus(listRefundDomainReq.getStatus());

        return listRefundOrderParam;
    }

}
