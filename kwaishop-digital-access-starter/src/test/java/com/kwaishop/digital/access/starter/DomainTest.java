package com.kwaishop.digital.access.starter;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.kwaishop.digital.access.demo.domain.eticket.request.AvailableEticketDomain;
import com.kwaishop.digital.access.demo.domain.eticket.request.EticketCheckAvailDomainReq;
import com.kwaishop.digital.access.demo.domain.eticket.request.EticketConsumeDomainReq;
import com.kwaishop.digital.access.demo.domain.eticket.request.EticketDomain;
import com.kwaishop.digital.access.demo.domain.eticket.request.EticketSendDomainReq;
import com.kwaishop.digital.access.demo.domain.eticket.request.ReverseETicketDomainReq;
import com.kwaishop.digital.access.demo.domain.eticket.service.EticketDomainService;
import com.kwaishop.digital.access.demo.domain.order.service.OrderDomainService;
import com.kwaishop.digital.access.demo.domain.refund.request.ApproveRefundDomainReq;
import com.kwaishop.digital.access.demo.domain.refund.request.ListRefundDomainReq;
import com.kwaishop.digital.access.demo.domain.refund.service.RefundDomainService;
import com.kwaishop.digital.access.demo.domain.shop.request.GetPoiDomainReq;
import com.kwaishop.digital.access.demo.domain.shop.service.ShopDomainService;
import com.kwaishop.digital.access.demo.proxy.KwaiETicketAdaptorService;
import com.kwaishop.digital.access.demo.proxy.request.QueryOrderDetailReq;

/**
 * @author zhangkwei
 * Created on 2022-05-13
 */
@SpringBootTest(classes=Application.class)
@RunWith(SpringRunner.class)
public class DomainTest {

    @Autowired
    private RefundDomainService refundDomainService;

    @Autowired
    private EticketDomainService eticketDomainService;

    @Autowired
    private ShopDomainService shopDomainService;

    @Autowired
    private KwaiETicketAdaptorService kwaiETicketAdaptorService;

    @Autowired
    private OrderDomainService orderDomainService;


    @Test
    public void approveRefundTest() {
        ApproveRefundDomainReq req = new ApproveRefundDomainReq();
        refundDomainService.approveRefund(req);
    }

    @Test
    public void sendTest() {
        EticketSendDomainReq domainReq = new EticketSendDomainReq();
        domainReq.setOid("2212500000034012");
        domainReq.setSellerId("2180400124");
        domainReq.setNum(5);
        domainReq.setItemId("404306488124");
        domainReq.setSkuId("315918058124");
        domainReq.setItemId("");
        eticketDomainService.sendEticket(domainReq);
    }

    @Test
    public void reverseTest() {
        ReverseETicketDomainReq domainReq = new ReverseETicketDomainReq();
        domainReq.setOid(2212500000034012L);
        domainReq.setEticketType("DINING_KWAI_TICKET");

        EticketDomain eticketDomain = new EticketDomain();
        eticketDomain.setId("9753582");
        List<EticketDomain> eticketDomains = new ArrayList<>();
        eticketDomains.add(eticketDomain);

        domainReq.setEtickets(eticketDomains);
        domainReq.setSerialNum("0FEBE6F7081B525F");
        domainReq.setReason("吃坏肚子了");
        domainReq.setToken("1223");
        eticketDomainService.reverseETicket(domainReq);
    }

    @Test
    public void testListRefundOrder() {
        ListRefundDomainReq req = new ListRefundDomainReq();
        req.setBeginTime(1650844800000L);
        req.setEndTime(1650931200000L);
        req.setType(9);
        req.setPageSize(10);
        req.setCurrentPage(1L);
        // listRefundOrderParam.setSort();
        req.setQueryType(1);
        req.setNegotiateStatus(0);
        req.setPcursor(" ");
        refundDomainService.listRefundOrder(req);

    }

    @Test
    public void queryOrderDetail() {
        orderDomainService.queryOrderDetail(2214300000001343L);
    }

    @Test
    public void getPoiDetailTest() {
        GetPoiDomainReq domainReq = new GetPoiDomainReq();
        domainReq.setOuterPoiId(3002665371509236266L);
        domainReq.setSource(1);
        shopDomainService.getPoiDetail(domainReq);
    }

    @Test
    public void testConsumeEticket() {

        EticketDomain eticketDomain = new EticketDomain();
        eticketDomain.setId("12313");
        eticketDomain.setCode("2131412");
        eticketDomain.setNum(1);
        eticketDomain.setStatus("FAILED");
        eticketDomain.setGoodsValue(321L);
        List<EticketDomain> list1 = new ArrayList<>();
        list1.add(eticketDomain);

        EticketConsumeDomainReq request = new EticketConsumeDomainReq();
        request.setOid("2214300000001343");
        request.setEtickets(list1);
        request.setConsumeTime(1543843735000L);
        request.setStatus("CONSUMED");
        request.setConsumeType("consume");
        request.setStoreName("XXX线下店");
        request.setStoreAddress("XXX路CCC号");
        request.setExpressNo("EMS");
        request.setExpressCode("12412412");
        request.setAppointmentTime("123124124234523");
        request.setEticketType("CRAB_TICKET");
        request.setExt("{\"key\":\"value\"}");
        request.setToken("sdjafljasdlflnlxjlfjasldjfljkawpoeirjmsdlmvc");
        request.setSeriallNum("aaaaa");
        request.setConsumePoiId(1L);

        System.out.println(eticketDomainService.consumeEticket(request));
    }

    @Test
    public void testCheckAvailableEticket() {
        AvailableEticketDomain obj1 = new AvailableEticketDomain();
        obj1.setId("111");
        obj1.setCode("112");
        obj1.setNum(2);
        List<AvailableEticketDomain> availableEticketDomains = new ArrayList<>();
        availableEticketDomains.add(obj1);

        EticketCheckAvailDomainReq request = new EticketCheckAvailDomainReq();
        request.setSellerId(211L);
        request.setBuyerId(211L);
        request.setOrderId(112L);
        request.setEticketType("22");
        request.setETicketList(availableEticketDomains);

        System.out.println(eticketDomainService.checkAvailEticket(request));

    }
}
