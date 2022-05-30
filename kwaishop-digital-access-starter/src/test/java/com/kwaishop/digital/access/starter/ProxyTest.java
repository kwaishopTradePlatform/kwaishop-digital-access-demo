package com.kwaishop.digital.access.starter;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.JsonNode;
import com.kwaishop.digital.access.demo.proxy.KwaiETicketAdaptorService;
import com.kwaishop.digital.access.demo.proxy.client.oauth.OauthAccessTokenKsClient;
import com.kwaishop.digital.access.demo.proxy.common.utils.AccessTokenUtils;
import com.kwaishop.digital.access.demo.proxy.common.utils.JsonUtils;
import com.kwaishop.digital.access.demo.proxy.request.ListRefundOrderParam;
import com.kwaishop.digital.access.demo.proxy.request.ListRefundOrderReq;
import com.kwaishop.digital.access.demo.proxy.response.AvailableEticketDetail;
import com.kwaishop.digital.access.demo.proxy.response.KsAccessTokenResponse;
import com.kwaishop.digital.access.demo.proxy.response.MerchantAvailableEticketsView;

/**
 * @author pengjianfei
 * Created on 2022-05-17
 */

@SpringBootTest(classes=Application.class)
@RunWith(SpringRunner.class)
public class ProxyTest {

    @Autowired
    KwaiETicketAdaptorService kwaiETicketAdaptorService;

    @Test
    public void testCheckAvail() {

        MerchantAvailableEticketsView merchantAvailableEticketsView = new MerchantAvailableEticketsView();
        AvailableEticketDetail availableEticketDetail = new AvailableEticketDetail();
        availableEticketDetail.setCode("121");
        availableEticketDetail.setItemId(12121L);
        availableEticketDetail.setNum(0);
        availableEticketDetail.setId("111");
        availableEticketDetail.setBuyerId(121211L);
        availableEticketDetail.setLeftNum(1L);
        availableEticketDetail.setEticketType("Code Tets");
        availableEticketDetail.setOrderId(121L);
        availableEticketDetail.setSellerId(232355L);
        availableEticketDetail.setDisplayStatus("woo");
        availableEticketDetail.setToken("afsff1212");
        AvailableEticketDetail availableEticketDetail2 = new AvailableEticketDetail();
        availableEticketDetail2.setCode("12qwq11");
        availableEticketDetail2.setItemId(12121L);
        availableEticketDetail2.setNum(0);
        availableEticketDetail2.setId("111");
        availableEticketDetail2.setBuyerId(121211L);
        availableEticketDetail2.setLeftNum(1L);
        availableEticketDetail2.setEticketType("Code Tets");
        availableEticketDetail2.setOrderId(121L);
        availableEticketDetail2.setSellerId(232355L);
        availableEticketDetail2.setDisplayStatus("woo");
        availableEticketDetail2.setToken("afsff1212");

        List<AvailableEticketDetail> list = new ArrayList<>();
        list.add(availableEticketDetail);
        list.add((availableEticketDetail2));
        merchantAvailableEticketsView.setEtickets(list);

        String responseStr = JsonUtils.toJSONString(merchantAvailableEticketsView);
        System.out.println(responseStr);

        JsonNode etickets = JsonUtils.toJSONNode(responseStr).get("etickets");
        List<AvailableEticketDetail> availableEticketDetails  =
                JsonUtils.toJavaObjectList(etickets, AvailableEticketDetail.class);
        MerchantAvailableEticketsView merchantAvailableEticketsView1 = new MerchantAvailableEticketsView();
        merchantAvailableEticketsView1.setEtickets(availableEticketDetails);

    }

    @Test
    public void  testListRefundOrder() {

        String appKey = "ks683702719562282620";
        String appSecret = "zSCiWmGOk_diMCU9k3zHcg";
        String signSecret = "7482bac6555ee7a3184bcc48f4ef05cd";
        String accessToken = "";
        String refreshToken = "ChJvYXV0aC5yZWZyZXNoVG9rZW4SsAHPvByNldtLCd3kPA9J9-HzY6f4OgXxL1VA1ey-1Q1kcrt_CntA6lR39Xymuefk3UNr4IUBNbps-i71J_JUqow28QlSzskEuLuA72n2M1AqP0IM50FN5DlVehtOr6aG2aeDvqxlV3IID_wG5ipjUYqrOPqIxDtnzBWj5JRilM3Hy50HWzXMGtO2SDBK2XCU6ajOK0L5Hs0OvlHtvY8VcED3_F7kuOoR94We-gFY4su18xoSixsDI1Ap_5iUF0o9NKotLeDEIiBrbyaw8-aUM6ldDkPsC0r_E-q9MnpTf0T47P5OyWTp6SgPMAE";

        String url = "https://gw-merchant-staging.test.gifshow.com/open/seller/order/refund/pcursor/list";
        String apiMethodName = "open.seller.order.refund.pcursor.list";

        try {
            OauthAccessTokenKsClient client = new OauthAccessTokenKsClient(appKey, appSecret, "https://gw-merchant-staging.test.gifshow.com/");
            KsAccessTokenResponse accessToken1 = client.refreshAccessToken(refreshToken);
            accessToken = accessToken1.getAccess_token();
            refreshToken = accessToken1.getRefresh_token();

        } catch (Exception e) {
            e.printStackTrace();
        }

        ListRefundOrderParam listRefundOrderParam = new ListRefundOrderParam();
        listRefundOrderParam.setBeginTime(1650844800000L);
        listRefundOrderParam.setEndTime(1650931200000L);
        listRefundOrderParam.setType(9);
        listRefundOrderParam.setPageSize(10);
        listRefundOrderParam.setCurrentPage(1L);
       // listRefundOrderParam.setSort();
        listRefundOrderParam.setQueryType(1);
        listRefundOrderParam.setNegotiateStatus(0);
        listRefundOrderParam.setPcursor(" ");
       // listRefundOrderParam.setStatus();

        ListRefundOrderReq req = new ListRefundOrderReq();
        req.setUrl(url);
        req.setMethod(apiMethodName);
        req.setVersion(1);
        req.setAccessToken(accessToken);
        req.setAppKey(appKey);
        req.setSignMethod("MD5");
        req.setSign("27b2ddec3ab555c1edaf9fd902948321");
        req.setTimestamp(1652793117345L);
        req.setParam(listRefundOrderParam);

//        System.out.println(refundAdaptorService.listRefundOrder(req));
        //     String response = getRequest(url, new HashMap<>(), params);
        //     System.out.println(response);
    }


    @Autowired
    private AccessTokenUtils accessTokenUtils;

    @Test
    public void testGetNewAccessToken() throws InterruptedException {
        while(true) {
            System.out.println(AccessTokenUtils.accessToken);
            Thread.sleep(3000);
            System.out.println(AccessTokenUtils.accessToken);
        }
     //   accessTokenUtils.setRefreshToken("rqeqeq");
    }
}
