package com.kwaishop.digital.access.demo.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kwaishop.digital.access.demo.domain.eticket.request.CheckSendAvailableDomainReq;
import com.kwaishop.digital.access.demo.domain.eticket.response.CheckSendAvailableDomainResp;
import com.kwaishop.digital.access.demo.domain.eticket.service.EticketDomainService;
import com.kwaishop.digital.access.demo.domain.item.model.AddSkuDTO;
import com.kwaishop.digital.access.demo.domain.item.model.AddSkuPropDTO;
import com.kwaishop.digital.access.demo.domain.item.request.AddItemDomainReq;
import com.kwaishop.digital.access.demo.domain.item.request.AddItemPropValue;
import com.kwaishop.digital.access.demo.domain.item.request.CategoryPropValueParam;
import com.kwaishop.digital.access.demo.domain.item.request.OpenApiServicePromise;
import com.kwaishop.digital.access.demo.domain.item.request.ServiceRule;
import com.kwaishop.digital.access.demo.domain.item.response.ItemAddDomainResponse;
import com.kwaishop.digital.access.demo.domain.item.service.ItemDomainService;
import com.kwaishop.digital.access.demo.domain.order.model.OrderDetailDTO;
import com.kwaishop.digital.access.demo.domain.order.service.OrderDomainService;
import com.kwaishop.digital.access.demo.model.ETicket;
import com.kwaishop.digital.access.demo.model.ETicketConsumeRequest;
import com.kwaishop.digital.access.demo.model.ETicketDestroyRequest;
import com.kwaishop.digital.access.demo.model.ETicketQueryRequest;
import com.kwaishop.digital.access.demo.model.ETicketQueryResponse;
import com.kwaishop.digital.access.demo.model.EticketCheckAvailRequest;
import com.kwaishop.digital.access.demo.model.EticketSendRequest;
import com.kwaishop.digital.access.demo.model.EticketSendResponse;
import com.kwaishop.digital.access.demo.model.OrderListRequest;
import com.kwaishop.digital.access.demo.model.ReverseETicketRequest;
import com.kwaishop.digital.access.demo.model.ServiceResponse;

/**
 * @author liuxiaoming
 *
 * 集成测试方法
 */
@RestController
@RequestMapping("/digital/a_i")
public class SolutionAIntegrationController {

    @Autowired
    OrderDomainService orderDomainService;

    @Autowired
    SolutionAController solutionAController;

    @Autowired
    ItemDomainService itemDomainService;

    @Autowired
    EticketDomainService eticketDomainService;

    // 核销流水号
    private String serialNum = "";


    /**
     * 快手调用该接口发码，然后在该接口中回调快手发码接口
     *
     * 测试使用的curl，由于调用接口很多接口的相应时间较长，测试环境下，环境可能不太稳定，
     * 建议一步一步debug的方式执行一遍，成功执行后，会返回相应的订单号
     *
     * curl --location --request POST 'http://localhost:8080/digital/a_i/eticket/test' \
     * --header 'host: eshop-app.staging.kuaishou.com' \
     * --header 'cookie: __NSWJ=B2kuqlvsBZBLsvdRkJBpFzYHM9PlRLfFGaKpZPf+nDm5E3OPMCbnaiUbAv8uTzAiAAAARQ==;kuaishou
     * .api_st=Cg9rdWFpc2hvdS5hcGkuc3QSsAEWieVYjl_kzK-eXnK8
     * -IfQx202XsWw0Sc0z0xjLY83CWTnte6diEsuoR7Tx7kydBG0TF6MrKzrknKw5fKIYCW188Wmy-laJyD1KKN9zxogC280-up68jK
     * -6iF0VUhL3Kuu5RnVjCzIGwHvnJC2wCJBzeHXQdEeuOcia_HEfOGHcxEGcdtZ196eJ5U1Tu7SC7zfBoIlZ9ElJTy8pNyhQgDIAQumeZDurIADzLuxASiQnRoSIlFvXo0DB5AO_3dvGoRAXJtjIiB24UwhKf_YRiHbWHIdNcd1HXAkYqGB8Z4bfZm6s72F_SgPMAE;token=e607590428d54edd8ae6ced2cc554503-2178681693;region_ticket=RT_A3FACD0A139ADD2EB5A0AFF3F844F8B3548EE0B617C6DF933AC0F07582F392259F232C8DA' \
     * --header 'user-agent: kwai-android aegon/2.12.0' \
     * --header 'x-ktrace-id-enabled: 0' \
     * --header 'accept-language: zh-cn' \
     * --header 'x-requestid: 164328590746732384' \
     * --header 'content-type: application/json' \
     * --header 'x-exp;' \
     * --header 'x-client-info: model=M2102J2SC;os=Android;nqe-score=36;network=WIFI;signal-strength=4;' \
     * --header 'connection: keep-alive' \
     * --data-raw ''
     *
     */
    @PostMapping("/eticket/test")
    public ServiceResponse<EticketSendResponse> test() {
        //创建商品
        ItemAddDomainResponse itemInfo = itemDomainService.addItem(createAddItemDomainReq());
        //创建订单
        Long oid = orderDomainService.mockOrder(itemInfo.getKwaiItemId(), 1);
        //查询订单列表
        solutionAController.listOrder(buildOrderListRequest());
        //查询订单详情
        ServiceResponse<OrderDetailDTO> orderDetailDTOServiceResponse = solutionAController.detailOrder(oid);
        OrderDetailDTO orderDetailDTO = orderDetailDTOServiceResponse.getData();
        // 获取token
        CheckSendAvailableDomainResp checkSendAvailableDomainResp = eticketDomainService.checkSendAvailable(buildCheckSendAvailableDomainReq(oid));
        //测试发码回调
        solutionAController.sendETicket(buildEticketSendRequest(orderDetailDTO, checkSendAvailableDomainResp.getToken()));
        //测试查询券码
        ServiceResponse<ETicketQueryResponse> eTicketQueryResponseServiceResponse = solutionAController.queryETicket(buildETicketQueryRequest(orderDetailDTO));
        ETicketQueryResponse eTicketQueryResponse = eTicketQueryResponseServiceResponse.getData();

        solutionAController.checkAvailableETicket(buildEticketCheckAvailRequest(eTicketQueryResponse));
        //测试核销券码
        solutionAController.consumeETicket(buildETicketConsumeRequest(oid + "", eticketDomainService.getETicketId(), checkSendAvailableDomainResp.getToken()));
        //测试冲正券码
        solutionAController.reverseETicket(buildReverseETicketRequest(oid, eticketDomainService.getETicketId(), checkSendAvailableDomainResp.getToken()));
        //测试再次核销券码
        solutionAController.consumeETicket(buildETicketConsumeRequest(oid + "", eticketDomainService.getETicketId(), checkSendAvailableDomainResp.getToken()));
        //测试冲正券码
        solutionAController.reverseETicket(buildReverseETicketRequest(oid, eticketDomainService.getETicketId(), checkSendAvailableDomainResp.getToken()));
        //测试销毁券码
        solutionAController.destroyETicket(buildETicketDestroyRequest(oid, eticketDomainService.getETicketId(), checkSendAvailableDomainResp.getToken()));

        EticketSendResponse response = new EticketSendResponse();
        response.setOrderNo(oid + "");
        return ServiceResponse.ok(response);

    }

    private AddItemDomainReq createAddItemDomainReq() {
        AddItemDomainReq addItemDomainReq = new AddItemDomainReq();

        addItemDomainReq.setTitle("电子凭证测试商品");
        Random random = new Random();
        addItemDomainReq.setRelItemId(Math.abs(random.nextLong()));
        addItemDomainReq.setCategoryId(3689);
        addItemDomainReq.setCategoryName("小吃快餐");
        String[] imageUrls = new String[1];
        imageUrls[0] =
                "https://kcdn.staging.kuaishou.com/bs2/image-kwaishop-product/item-2173536129"
                        + "-7f8a392fc8554a61b935794d7bba4ea5.jpg?bp=10180";
        addItemDomainReq.setImageUrls(imageUrls);
        List<AddSkuDTO> addSkuDTOs = new ArrayList<>();
        AddSkuDTO addSkuDTO = new AddSkuDTO();
        addSkuDTO.setRelSkuId(Math.abs(random.nextLong()));
        addSkuDTO.setSkuStock(99999L);
        addSkuDTO.setSkuSalePrice(1L);
        addSkuDTO.setSkuNick("TS9988");
        List<AddSkuPropDTO> addSkuPropDTOS = new ArrayList<>();
        AddSkuPropDTO addSkuPropDTO = new AddSkuPropDTO();
        addSkuPropDTO.setPropName("套餐名");
        addSkuPropDTO.setPropValueName("双人餐");
        addSkuPropDTO.setIsMainProp(0);
        addSkuPropDTO.setPropValueGroupId(0L);
        addSkuPropDTO.setPropVersion(1);
        addSkuPropDTOS.add(addSkuPropDTO);
        addSkuDTO.setSkuProps(addSkuPropDTOS);
        addSkuDTO.setSkuMarketPrice(2L);
        addSkuDTOs.add(addSkuDTO);
        addItemDomainReq.setSkuList(addSkuDTOs);
        addItemDomainReq.setPurchaseLimit(false);
        AddItemPropValue[] addItemPropValues = new AddItemPropValue[2];

        AddItemPropValue addItemPropValue = new AddItemPropValue();
        addItemPropValue.setPropId(30622L);
        CategoryPropValueParam categoryPropValueParam = new CategoryPropValueParam();
        categoryPropValueParam.setPropValueId(1101141722L);
        categoryPropValueParam.setPropValue("团购套餐");
        addItemPropValue.setRadioPropValue(categoryPropValueParam);
        addItemPropValues[0] = addItemPropValue;

        AddItemPropValue brandProp = new AddItemPropValue();
        brandProp.setPropId(102L);
        brandProp.setPropName("品牌");
        brandProp.setPropType(2);
        CategoryPropValueParam brandPropValueParam = new CategoryPropValueParam();
        brandPropValueParam.setPropValueId(272256L);
        brandPropValueParam.setPropValue("茶客记");
        brandProp.setRadioPropValue(brandPropValueParam);
        addItemPropValues[1] = brandProp;
        addItemDomainReq.setItemPropValues(addItemPropValues);

        addItemDomainReq.setDetails("商品描述");
        addItemDomainReq.setStockPartner(false);
        addItemDomainReq.setItemRemark("商品备注");
        ServiceRule serviceRule = new ServiceRule();
        serviceRule.setRefundRule("11"); //随时退过期退
        serviceRule.setPromiseDeliveryTime(-1L);
        serviceRule.setImmediatelyOnOfflineFlag(0);
        serviceRule.setDeliveryMethod("certificate");
        serviceRule.setCertMerchantCode("merchant"); // ks快手发码，merchant商家发码，couponLibrary券码库发码
        serviceRule.setCertExpireType(3);
        serviceRule.setCertExpDays(30L);
        OpenApiServicePromise openApiServicePromise = new OpenApiServicePromise();
        serviceRule.setServicePromise(openApiServicePromise);
        addItemDomainReq.setServiceRule(serviceRule);
        addItemDomainReq.setSaleTimeFlag(false);
        addItemDomainReq.setPayWay(2);
        addItemDomainReq.setMultipleStock(false);
        addItemDomainReq.setExpressTemplateId(1L);
        addItemDomainReq.setPoiIds(Arrays.asList(3002703451617993287L));
        return addItemDomainReq;
    }

    private CheckSendAvailableDomainReq buildCheckSendAvailableDomainReq(Long oid) {
        CheckSendAvailableDomainReq domainReq = new CheckSendAvailableDomainReq();
        domainReq.setOid(oid);
        domainReq.setBuyerId(2177544922L); // 测试专用买家
        domainReq.setSellerId(2173536129L); // 测试专用卖家

        return domainReq;
    }

    private OrderListRequest buildOrderListRequest() {
        OrderListRequest orderListRequest = new OrderListRequest();
        orderListRequest.setOrderViewStatus(1);
        orderListRequest.setPageSize(50);
        orderListRequest.setSort(1);
        orderListRequest.setQueryType(1);
        orderListRequest.setBeginTime(System.currentTimeMillis() - 100000);
        orderListRequest.setEndTime(System.currentTimeMillis() + 10000);
        orderListRequest.setCpsType(1);
        orderListRequest.setCursor("");
        return orderListRequest;
    }

    private EticketSendRequest buildEticketSendRequest(OrderDetailDTO orderDetailDTO, String token) {
        EticketSendRequest eticketSendRequest = new EticketSendRequest();
        eticketSendRequest.setOid(orderDetailDTO.getOrderBaseInfo().getOid() + "");
        eticketSendRequest.setSellerId(orderDetailDTO.getOrderBaseInfo().getSellerOpenId());
        eticketSendRequest.setNum(orderDetailDTO.getOrderItemInfo().getNum());
        eticketSendRequest.setItemId(orderDetailDTO.getOrderItemInfo().getItemId() + "");
        eticketSendRequest.setSkuId(orderDetailDTO.getOrderItemInfo().getSkuId() + "");
        eticketSendRequest.setItemTitle(orderDetailDTO.getOrderItemInfo().getItemTitle());
        eticketSendRequest.setSendType("virtual");
        eticketSendRequest.setEticketType("DINING_OPEN_TICKET");
        eticketSendRequest.setToken(token);
        eticketSendRequest.setExt("");
        eticketSendRequest.setSpCode("DQ");
        eticketSendRequest.setCertExpireType(1);
        eticketSendRequest.setCertStartTime(orderDetailDTO.getOrderBaseInfo().getPayTime() - 1000);
        eticketSendRequest.setCertEndTime(System.currentTimeMillis() + 2592001000L);

        return eticketSendRequest;
    }

    private ETicketQueryRequest buildETicketQueryRequest(OrderDetailDTO orderDetailDTO) {
        ETicketQueryRequest eTicketQueryRequest = new ETicketQueryRequest();
        eTicketQueryRequest.setOid(orderDetailDTO.getOrderBaseInfo().getOid() + "");
        eTicketQueryRequest.setEticketId(eticketDomainService.getETicketId());
        eTicketQueryRequest.setEticketType("DINING_OPEN_TICKET");
        eTicketQueryRequest.setSendType("");
        eTicketQueryRequest.setSpCode("DQ");
        return eTicketQueryRequest;
    }

    private EticketCheckAvailRequest buildEticketCheckAvailRequest(ETicketQueryResponse eTicketQueryResponse) {
        EticketCheckAvailRequest request = new EticketCheckAvailRequest();
        request.setSellerId(2173536129L);
        request.setBuyerId(2177544922L);
        return request;
    }

    private ReverseETicketRequest buildReverseETicketRequest(Long oid, String eTicketId, String token) {
        ReverseETicketRequest reverseETicketRequest = new ReverseETicketRequest();
        reverseETicketRequest.setOid(oid);
        reverseETicketRequest.setEticketType("DINING_OPEN_TICKET");
        ETicket eTicket = new ETicket();
        eTicket.setId(eTicketId);
        List<ETicket> eTicketList = new ArrayList<>();
        eTicketList.add(eTicket);
        reverseETicketRequest.setEtickets(eTicketList);
        reverseETicketRequest.setSerialNum(this.serialNum);
//        reverseETicketRequest.setSerialNum(serialNum);
        reverseETicketRequest.setToken(token);
        return reverseETicketRequest;
    }

    private ETicketConsumeRequest buildETicketConsumeRequest(String oid, String eTicketId, String token) {
        ETicketConsumeRequest eTicketConsumeRequest = new ETicketConsumeRequest();
        eTicketConsumeRequest.setEticketType("DINING_OPEN_TICKET");
        eTicketConsumeRequest.setConsumeType("consume");
        eTicketConsumeRequest.setStatus("CONSUMED");
        eTicketConsumeRequest.setOid(oid);
        eTicketConsumeRequest.setToken(token);
        eTicketConsumeRequest.setConsumeTime(System.currentTimeMillis());
        this.serialNum = UUID.randomUUID().toString().substring(0, 8);
        eTicketConsumeRequest.setSeriallNum(serialNum);

        ETicket eTicket = new ETicket();
        eTicket.setId(eTicketId);
        eTicket.setNum(1);
        List<ETicket> eTicketList = new ArrayList<>();
        eTicketList.add(eTicket);

        eTicketConsumeRequest.setEtickets(eTicketList);

        return eTicketConsumeRequest;
    }

    private ETicketDestroyRequest buildETicketDestroyRequest(Long oid, String eTicketId, String token) {
        ETicketDestroyRequest eTicketDestroyRequest = new ETicketDestroyRequest();
        eTicketDestroyRequest.setOid(oid + "");
        ETicket eTicket = new ETicket();
        eTicket.setId(eTicketId);
        eTicket.setNum(1);
        eTicket.setGoodsValue(1L);
        List<ETicket> eTicketList = new ArrayList<>();
        eTicketList.add(eTicket);
        eTicketDestroyRequest.setEtickets(eTicketList);
        eTicketDestroyRequest.setReason("吃坏肚子了");
        eTicketDestroyRequest.setEticketType("DINING_OPEN_TICKET");

        eTicketDestroyRequest.setToken(token);
        eTicketDestroyRequest.setSerialNum(UUID.randomUUID().toString().substring(0, 8));

        return eTicketDestroyRequest;
    }

}
