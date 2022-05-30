package com.kwaishop.digital.access.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kwaishop.digital.access.demo.domain.eticket.model.AvailableEticketDetailDTO;
import com.kwaishop.digital.access.demo.domain.eticket.model.MerchantAvailableEticketsDTO;
import com.kwaishop.digital.access.demo.domain.eticket.service.EticketDomainService;
import com.kwaishop.digital.access.demo.model.AvailableEticketDetail;
import com.kwaishop.digital.access.demo.model.ETicketConsumeRequest;
import com.kwaishop.digital.access.demo.model.ETicketConsumeResponse;
import com.kwaishop.digital.access.demo.model.EticketCheckAvailRequest;
import com.kwaishop.digital.access.demo.model.EticketCheckAvailResponse;
import com.kwaishop.digital.access.demo.model.ServiceResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangyiying
 * Created on 2022-05-06
 */
@RestController
@Slf4j
@RequestMapping("/digital/c")
public class SolutionCController {
    @Autowired
    private EticketDomainService eticketDomainService;

    private static final int failCode = -1;
    /**
     * 快手不会主动调用，由具体的门店核销的时候，调用该接口
     * 方向：门店pos机等 -> isv -> 快手
     * @param request
     * @return
     */
    @PostMapping("/eticket/consume")
    public ServiceResponse<ETicketConsumeResponse> consumeETicket(@RequestBody ETicketConsumeRequest request) {
        try {
            ETicketConsumeResponse response = new ETicketConsumeResponse();
            response.setSuccess(eticketDomainService.consumeEticket(request.toDomainRequest()));
            return ServiceResponse.ok(response);
        } catch (Throwable e) {
            log.error("consumeETicket error. oid:{}", request.getOid(), e);
            return ServiceResponse.error(failCode, "调用核销接口失败,oid:" + request.getOid());
        }
    }

    /**
     * 主动向快手发起请求，校验卡券是否可用
     * @param request
     * @return
     */
    @PostMapping("/eticket/checkavailable")
    public ServiceResponse<EticketCheckAvailResponse> checkAvailableETicket(@RequestBody
            EticketCheckAvailRequest request) {
        try {
            MerchantAvailableEticketsDTO merchantAvailableEticketsDTO = eticketDomainService.checkAvailEticket(request.toDomainRequest());
            EticketCheckAvailResponse response = buildEticketCheckAvailResp(merchantAvailableEticketsDTO);
            return ServiceResponse.ok(response);
        } catch (Throwable e) {
            log.error("eticket checkavailable error. orderId:{}", request.getOrderId(), e);
            return ServiceResponse.error(failCode, "检查电子凭证是否有效失败, orderId: "+ request.getOrderId());
        }
    }


    private AvailableEticketDetail buildAvailableEticket(AvailableEticketDetailDTO dto) {
        AvailableEticketDetail availableEticket = new AvailableEticketDetail();
        availableEticket.setCode(dto.getCode());
        availableEticket.setItemId(dto.getItemId());
        availableEticket.setNum(dto.getNum());
        availableEticket.setId(dto.getId());
        availableEticket.setBuyerId(dto.getBuyerId());
        availableEticket.setLeftNum(dto.getLeftNum());
        availableEticket.setEticketType(dto.getEticketType());
        availableEticket.setOrderId(dto.getOrderId());
        availableEticket.setSellerId(dto.getSellerId());
        availableEticket.setDisplayStatus(dto.getDisplayStatus());
        availableEticket.setToken(dto.getToken());

        return availableEticket;
    }

    private EticketCheckAvailResponse buildEticketCheckAvailResp(MerchantAvailableEticketsDTO dto) {
        EticketCheckAvailResponse response = new EticketCheckAvailResponse();
        List<AvailableEticketDetailDTO> etickets = dto.getEtickets();
        List<AvailableEticketDetail> eticketDetails =  new ArrayList<>();

        for (AvailableEticketDetailDTO detailDTO : etickets) {
            eticketDetails.add(buildAvailableEticket(detailDTO));
        }
        response.setEtickets(eticketDetails);
        return response;
    }
}
