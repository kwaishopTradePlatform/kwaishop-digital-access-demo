package com.kwaishop.digital.access.demo.domain.refund.service;

import com.kwaishop.digital.access.demo.domain.refund.request.ApproveRefundDomainReq;
import com.kwaishop.digital.access.demo.domain.refund.request.DisagreeRefundDomainReq;
import com.kwaishop.digital.access.demo.domain.refund.request.ListRefundDomainReq;

/**
 * @author zhangkwei
 * Created on 2022-05-12
 */
public interface RefundDomainService {

    /**
     * 同意退款
     */
    boolean approveRefund(ApproveRefundDomainReq approveRefundDomainReq);

    void disagreeRefund(DisagreeRefundDomainReq disagreeRefundDomainReq);

    void listRefundOrder(ListRefundDomainReq listRefundDomainReq);
}
