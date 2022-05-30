package com.kwaishop.digital.access.demo.domain.shop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.kwaishop.digital.access.demo.domain.shop.request.GetPoiDomainReq;
import com.kwaishop.digital.access.demo.domain.shop.response.GetPoiDomainResp;
import com.kwaishop.digital.access.demo.domain.shop.service.ShopDomainService;
import com.kwaishop.digital.access.demo.proxy.KwaiETicketAdaptorService;
import com.kwaishop.digital.access.demo.proxy.request.BaseReq;
import com.kwaishop.digital.access.demo.proxy.request.QueryPoiDetailParam;
import com.kwaishop.digital.access.demo.proxy.response.QueryPoiDetailResponse;

/**
 * @author zhangkwei
 * Created on 2022-05-17
 */
@Service
public class ShopDomainServiceImpl implements ShopDomainService {

    private static final String GET_POI_BY_OUTER_POI_URL = "open.shop.poi.getPoiDetailByOuterPoi";

    @Autowired
    private KwaiETicketAdaptorService kwaiETicketAdaptorService;

    @Value("${kwaishop.accessToken}")
    private String accessToken;

    @Value("${kwaishop.appKey}")
    private String appKey;

    @Value("${kwaishop.signMethod}")
    private String signMethod;

    @Value("${kwaishop.url}")
    private String url;

    private static final int version = 1;

    @Override
    public GetPoiDomainResp getPoiDetail(GetPoiDomainReq getPoiDomainReq) {
        QueryPoiDetailResponse response = kwaiETicketAdaptorService.getPoiDetailByOuterPoi(buildQueryPoiDetailReq(getPoiDomainReq));
        return null;
    }

    private QueryPoiDetailParam buildQueryPoiDetailReq(GetPoiDomainReq getPoiDomainReq) {
        QueryPoiDetailParam queryPoiDetailParam = new QueryPoiDetailParam();
        queryPoiDetailParam.setOuterPoiId(getPoiDomainReq.getOuterPoiId());
        queryPoiDetailParam.setSource(getPoiDomainReq.getSource());

        return queryPoiDetailParam;
    }

    private void buildBaseReq(BaseReq baseReq) {
        baseReq.setAccessToken(accessToken);
        baseReq.setAppKey(appKey);
        baseReq.setMethod(GET_POI_BY_OUTER_POI_URL);
        baseReq.setSignMethod(signMethod);
        baseReq.setVersion(version);
        baseReq.setTimestamp(System.currentTimeMillis());
        baseReq.setUrl(url);
    }
}
