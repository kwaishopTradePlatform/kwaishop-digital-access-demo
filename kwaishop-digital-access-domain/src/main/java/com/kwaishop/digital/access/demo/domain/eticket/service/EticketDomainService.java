package com.kwaishop.digital.access.demo.domain.eticket.service;

import com.kwaishop.digital.access.demo.domain.eticket.model.EticketsDetailDTO;
import com.kwaishop.digital.access.demo.domain.eticket.model.MerchantAvailableEticketsDTO;
import com.kwaishop.digital.access.demo.domain.eticket.request.CheckSendAvailableDomainReq;
import com.kwaishop.digital.access.demo.domain.eticket.request.DestroyETicketDomainReq;
import com.kwaishop.digital.access.demo.domain.eticket.request.EticketCheckAvailDomainReq;
import com.kwaishop.digital.access.demo.domain.eticket.request.EticketConsumeDomainReq;
import com.kwaishop.digital.access.demo.domain.eticket.request.EticketQueryDomainReq;
import com.kwaishop.digital.access.demo.domain.eticket.request.EticketSendDomainReq;
import com.kwaishop.digital.access.demo.domain.eticket.request.ReverseETicketDomainReq;
import com.kwaishop.digital.access.demo.domain.eticket.response.CheckSendAvailableDomainResp;

/**
 * @author zhangyiying
 * Created on 2022-05-12
 */
public interface EticketDomainService {

    /**
     * 电子凭证通知发码
     */
    String sendEticket(EticketSendDomainReq request);

    /**
     * 电子凭证核销回调接口
     */
    boolean consumeEticket(EticketConsumeDomainReq request);

    /**
     * 冲正券
     */
    boolean reverseETicket(ReverseETicketDomainReq reverseETicketDomainReq);

    MerchantAvailableEticketsDTO checkAvailEticket(EticketCheckAvailDomainReq request);

    /**
     * 电子凭证查询发码结果或券码状态
     */
    EticketsDetailDTO queryEticket(EticketQueryDomainReq request);

    /**
     * 电子凭证销毁
     *
     * @param request
     * @return
     */
    boolean destroyETicket(DestroyETicketDomainReq request);

    /**
     * 查询发货信息
     */
    CheckSendAvailableDomainResp checkSendAvailable(CheckSendAvailableDomainReq req);

    /**
     * 获取电子凭证id，测试使用
     *
     * @return
     */
    String getETicketId();
}
