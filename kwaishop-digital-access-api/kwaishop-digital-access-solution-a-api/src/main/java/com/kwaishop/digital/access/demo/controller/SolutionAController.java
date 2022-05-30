package com.kwaishop.digital.access.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kwaishop.digital.access.demo.domain.eticket.model.AvailableEticketDetailDTO;
import com.kwaishop.digital.access.demo.domain.eticket.model.EticketConsumeExt;
import com.kwaishop.digital.access.demo.domain.eticket.model.EticketsDetailDTO;
import com.kwaishop.digital.access.demo.domain.eticket.model.MerchantAvailableEticketsDTO;
import com.kwaishop.digital.access.demo.domain.eticket.service.EticketDomainService;
import com.kwaishop.digital.access.demo.domain.item.request.AddItemDomainReq;
import com.kwaishop.digital.access.demo.domain.item.response.ItemAddDomainResponse;
import com.kwaishop.digital.access.demo.domain.item.service.ItemDomainService;
import com.kwaishop.digital.access.demo.domain.order.model.OrderDetailDTO;
import com.kwaishop.digital.access.demo.domain.order.model.OrderListDTO;
import com.kwaishop.digital.access.demo.domain.order.request.OrderListDomainRequest;
import com.kwaishop.digital.access.demo.domain.order.service.OrderDomainService;
import com.kwaishop.digital.access.demo.model.AddItemRequest;
import com.kwaishop.digital.access.demo.model.AvailableEticketDetail;
import com.kwaishop.digital.access.demo.model.ETicket;
import com.kwaishop.digital.access.demo.model.ETicketConsumeDetail;
import com.kwaishop.digital.access.demo.model.ETicketConsumeRequest;
import com.kwaishop.digital.access.demo.model.ETicketConsumeResponse;
import com.kwaishop.digital.access.demo.model.ETicketDeductItemStockRequest;
import com.kwaishop.digital.access.demo.model.ETicketDeductItemStockResponse;
import com.kwaishop.digital.access.demo.model.ETicketDestroyRequest;
import com.kwaishop.digital.access.demo.model.ETicketDestroyResponse;
import com.kwaishop.digital.access.demo.model.ETicketInfo;
import com.kwaishop.digital.access.demo.model.ETicketQueryRequest;
import com.kwaishop.digital.access.demo.model.ETicketQueryResponse;
import com.kwaishop.digital.access.demo.model.EticketCheckAvailRequest;
import com.kwaishop.digital.access.demo.model.EticketCheckAvailResponse;
import com.kwaishop.digital.access.demo.model.EticketSendRequest;
import com.kwaishop.digital.access.demo.model.EticketSendResponse;
import com.kwaishop.digital.access.demo.model.ItemAddResponse;
import com.kwaishop.digital.access.demo.model.OrderListRequest;
import com.kwaishop.digital.access.demo.model.OrderMockRequest;
import com.kwaishop.digital.access.demo.model.OrderMockResponse;
import com.kwaishop.digital.access.demo.model.ReverseETicketRequest;
import com.kwaishop.digital.access.demo.model.ReverseETicketResponse;
import com.kwaishop.digital.access.demo.model.ServiceResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangyiying
 * Created on 2022-05-06
 */
@RestController
@RequestMapping("/digital/a")
@Slf4j
public class SolutionAController {

    @Autowired
    private EticketDomainService eticketDomainService;

    @Autowired
    private ItemDomainService itemDomainService;

    @Autowired
    private OrderDomainService orderDomainService;

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
    public ServiceResponse<ETicketQueryResponse> queryETicket(@RequestBody ETicketQueryRequest request) {
        try {
            EticketsDetailDTO eticketsDetailDTO = eticketDomainService.queryEticket(request.toDomainRequest());
            ETicketQueryResponse response = buildETicketQueryResponse(eticketsDetailDTO);
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

    /**
     * 库存扣减
     * 方向：快手 -> isv
     *
     * @param request
     * @return
     */
    @PostMapping("/eticket/deduct")
    public ServiceResponse<ETicketDeductItemStockResponse> deductItemStock(@RequestBody ETicketDeductItemStockRequest request) {
        try {
            ETicketDeductItemStockResponse response = new ETicketDeductItemStockResponse();
            response.setSuccess(itemDomainService.deductItemStock(request.toDomainReq()));
            return ServiceResponse.ok(response);
        } catch (Throwable e) {
            log.error("deductItemStock error. oid:{}", request.getOrderId(), e);
            return ServiceResponse.error(failCode, "调用库存扣减接口失败,oid:" + request.getOrderId());
        }
    }

    @PostMapping("/item/add")
    public ServiceResponse<ItemAddResponse> addItem(@RequestBody AddItemDomainReq request) {
        try {
            ItemAddDomainResponse itemAddDomainResponse = itemDomainService.addItem(request);
            ItemAddResponse response = new ItemAddResponse(itemAddDomainResponse);
            return ServiceResponse.ok(response);
        } catch (Throwable e) {
            log.error("deductItemStock error. CategoryId:{}", request.getCategoryId(), e);
            return ServiceResponse.error(failCode, "调用addItem失败,CategoryId:" + request.getCategoryId());
        }
    }

    @PostMapping("/order/mock")
    public ServiceResponse<OrderMockResponse> mockOrder(@RequestBody OrderMockRequest request) {
        try {
            Long oid = orderDomainService.mockOrder(request.getItemId(), request.getItemNum());
            OrderMockResponse response = new OrderMockResponse();
            response.setOid(oid);
            return ServiceResponse.ok(response);
        } catch (Throwable e) {
            log.error("MockOrder error. ItemId:{}", request.getItemId(), e);
            return ServiceResponse.error(failCode, "调用订单Mock接口失败,ItemId:" + request.getItemId());
        }
    }

    @GetMapping("/order/list")
    public ServiceResponse<OrderListDTO> listOrder(@RequestBody OrderListRequest request) {
        try {
            OrderListDTO orderListDTO = orderDomainService.listOrder(request.toDomainReq());
            return ServiceResponse.ok(orderListDTO);
        } catch (Throwable e) {
            log.error("listOrder error.", e);
            return ServiceResponse.error(failCode, "获取订单列表失败");
        }
    }

    @GetMapping("/order/detail")
    public ServiceResponse<OrderDetailDTO> detailOrder(@RequestBody Long oid) {
        try {
            OrderDetailDTO orderDetailDTO = orderDomainService.queryOrderDetail(oid);
            return ServiceResponse.ok(orderDetailDTO);
        } catch (Throwable e) {
            log.error("detailOrder error. oid:{}", oid, e);
            return ServiceResponse.error(failCode, "获取订单详情失败, oid: "+ oid);
        }
    }

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

    @PostMapping("/eticket/reverse")
    public ServiceResponse<ReverseETicketResponse> reverseETicket(@RequestBody
            ReverseETicketRequest request) {
        try {
            ReverseETicketResponse response = new ReverseETicketResponse();
            Boolean success = eticketDomainService.reverseETicket(request.toDomainReq());
            response.setSuccess(success);
            return ServiceResponse.ok(response);
        } catch (Throwable e) {
            log.error("reverseETicket error. oid:{}", request.getOid(), e);
            return ServiceResponse.error(failCode, "电子凭证冲正失败, oid: "+ request.getOid());
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

    // 按需构建
    private ETicketQueryResponse buildETicketQueryResponse(EticketsDetailDTO eticketsDetailDTO) {
        ETicketQueryResponse response = new ETicketQueryResponse();
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

    private ETicket toETicket(com.kwaishop.digital.access.demo.domain.eticket.model.ETicket eTicket) {
        ETicket ticket = new ETicket();
        ticket.setId(eTicket.getId());
        ticket.setCode(eTicket.getCode());
        ticket.setNum(eTicket.getNum());
        ticket.setStatus(eTicket.getStatus());
        return ticket;
    }

}
