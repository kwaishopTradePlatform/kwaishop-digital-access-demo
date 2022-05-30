package com.kwaishop.digital.access.demo.proxy.response;

import lombok.Data;

/**
 * @author zhangkwei
 * Created on 2022-05-19
 */
@Data
public class QueryPoiDetailResponse {
    private String outerPoiId;
    private Integer source;
    private String poiId;
    private String name;
    private String longitude;
    private String latitude;
    private String address;
    private String country;
    private String province;
    private String city;
    private String district;
    private Long provinceCode;
    private Long cityCode;
    private Long districtCode;
}
