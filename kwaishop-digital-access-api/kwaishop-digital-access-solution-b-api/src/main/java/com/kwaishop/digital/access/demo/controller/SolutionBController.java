package com.kwaishop.digital.access.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kwaishop.digital.access.demo.domain.eticket.model.EticketConsumeExt;
import com.kwaishop.digital.access.demo.domain.eticket.model.EticketsDetailDTO;
import com.kwaishop.digital.access.demo.domain.eticket.service.EticketDomainService;
import com.kwaishop.digital.access.demo.model.ETicketConsumeDetail;
import com.kwaishop.digital.access.demo.model.ETicketConsumeRequest;
import com.kwaishop.digital.access.demo.model.ETicketConsumeResponse;
import com.kwaishop.digital.access.demo.model.ETicketDestroyRequest;
import com.kwaishop.digital.access.demo.model.ETicketDestroyResponse;
import com.kwaishop.digital.access.demo.model.ETicketInfo;
import com.kwaishop.digital.access.demo.model.EticketQueryRequest;
import com.kwaishop.digital.access.demo.model.EticketQueryResponse;
import com.kwaishop.digital.access.demo.model.EticketSendRequest;
import com.kwaishop.digital.access.demo.model.EticketSendResponse;
import com.kwaishop.digital.access.demo.model.ServiceResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangyiying
 * Created on 2022-05-12
 */
@RestController
@Slf4j
@RequestMapping("/digital/b")
public class SolutionBController {

    @Autowired
    private EticketDomainService eticketDomainService;

    private static final int failCode = -1;
    /**
     * 快手调用该接口发码，然后在该接口中回调快手发码接口
     * 方向：快手发码 -> isv 发码
     * todo : 所有api增加plant uml
     * todo ：所有的domain服务都增加一个controller接口，用于联调
     */
    @PostMapping("/eticket/send")
    public ServiceResponse<EticketSendResponse> sendETicket(@RequestBody EticketSendRequest request) {
        try {
            String orderNo = eticketDomainService.sendEticket(request.toDomainRequest());
            EticketSendResponse response = new EticketSendResponse();
            response.setOrderNo(orderNo);
            return ServiceResponse.ok(response);
        } catch (Throwable e) {
            log.error("sendETicket error. oid:{}", request.getOid(), e);
            return ServiceResponse.error(failCode, "调用发货接口失败,oid:" + request.getOid());
        }

    }

    /**
     * 快手调用该接口查询isv的券接口
     * 方向：快手 -> isv
     *
     * @param request
     * @return
     */
    @PostMapping("/eticket/query")
    public ServiceResponse<EticketQueryResponse> queryETicket(@RequestBody EticketQueryRequest request) {
        try {
            EticketsDetailDTO eticketsDetailDTO = eticketDomainService.queryEticket(request.toDomainRequest());
            EticketQueryResponse response = buildETicketQueryResponse(eticketsDetailDTO);
            return ServiceResponse.ok(response);
        } catch (Throwable e) {
            log.error("queryETicket error, oid:{}", request.getOid(), e);
            return ServiceResponse.error(failCode, "调用券查询接口失败,oid:" + request.getOid());
        }
    }

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
     * 快手电子凭证销毁
     * 方向： 快手 -> isv
     *
     * @param request
     * @return
     */
    @PostMapping("/eticket/destroy")
    public ServiceResponse<ETicketDestroyResponse> destroyETicket(@RequestBody ETicketDestroyRequest request) {
        try {
            ETicketDestroyResponse response = new ETicketDestroyResponse();
            response.setSuccess(eticketDomainService.destroyETicket(request.toDomainRequest()));
            return ServiceResponse.ok(response);
        } catch (Throwable e) {
            log.error("destroyETicket error. oid:{}", request.getOid(), e);
            return ServiceResponse.error(failCode, "调用销毁券接口失败,oid:" + request.getOid());
        }
    }

    // 按需构建
    private EticketQueryResponse buildETicketQueryResponse(EticketsDetailDTO eticketsDetailDTO) {
        EticketQueryResponse response = new EticketQueryResponse();
        response.setOid(eticketsDetailDTO.getOid());
        response.setSendType(eticketsDetailDTO.getSendType());
        response.setEticketType(eticketsDetailDTO.getEticketType());
        response.setEtickets(eticketsDetailDTO.getEtickets().stream().map(this::toETicketInfo).collect(Collectors.toList()));
        response.setSendNum(eticketsDetailDTO.getSendNum());
        response.setExt(eticketsDetailDTO.getExt());
        return response;
    }


    private ETicketInfo toETicketInfo(com.kwaishop.digital.access.demo.domain.eticket.model.ETicket eTicket) {
        ETicketInfo eTicketInfo = new ETicketInfo();

        eTicketInfo.setId(eTicket.getId());
        eTicketInfo.setCode(eTicket.getCode());
        eTicketInfo.setStatus(eTicket.getStatus());
        eTicketInfo.setNum(eTicket.getNum());
        eTicketInfo.setValidStartTime(eTicket.getValidStartTime());
        eTicketInfo.setValidEndTime(eTicket.getValidEndTime());
        List<EticketConsumeExt> eticketConsumeExts = eTicket.getEticketConsumeDetails();
        List<ETicketConsumeDetail> eTicketConsumeDetails = new ArrayList<>();
        if (eticketConsumeExts != null) {
            for (EticketConsumeExt eTicketConsumeExt : eticketConsumeExts) {
                eTicketConsumeDetails.add(toETicketConsumeDetail(eTicketConsumeExt));
            }
        }
        eTicketInfo.setEticketConsumeDetails(eTicketConsumeDetails);
        return eTicketInfo;
    }

    private ETicketConsumeDetail toETicketConsumeDetail(EticketConsumeExt eticketConsumeExt) {
        ETicketConsumeDetail eTicketConsumeDetail = new ETicketConsumeDetail();
        eTicketConsumeDetail.setSerialNum(eticketConsumeExt.getSerialNum());
        eTicketConsumeDetail.setConsumeType(eticketConsumeExt.getConsumeType());
        eTicketConsumeDetail.setConsumeTime(eticketConsumeExt.getConsumeTime());
        eTicketConsumeDetail.setAppointmentTime(eticketConsumeExt.getAppointmentTime());
        eTicketConsumeDetail.setStoreName(eticketConsumeExt.getStoreName());
        eTicketConsumeDetail.setStoreAddress(eticketConsumeExt.getStoreAddress());
        eTicketConsumeDetail.setExpressCode(eticketConsumeExt.getExpressCode());
        eTicketConsumeDetail.setExpressName(eticketConsumeExt.getExpressName());
        eTicketConsumeDetail.setExpressNo(eticketConsumeExt.getExpressNo());
        eTicketConsumeDetail.setReason(eticketConsumeExt.getReason());

        return eTicketConsumeDetail;
    }

}

