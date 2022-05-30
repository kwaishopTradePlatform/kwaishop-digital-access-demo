package com.kwaishop.digital.access.demo.domain.shop.service;

import com.kwaishop.digital.access.demo.domain.shop.request.GetPoiDomainReq;
import com.kwaishop.digital.access.demo.domain.shop.response.GetPoiDomainResp;

/**
 * @author zhangkwei
 * Created on 2022-05-17
 */
public interface ShopDomainService {
    GetPoiDomainResp getPoiDetail(GetPoiDomainReq getPoiDomainReq);
}
