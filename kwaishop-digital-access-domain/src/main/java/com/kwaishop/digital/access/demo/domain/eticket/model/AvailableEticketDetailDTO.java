package com.kwaishop.digital.access.demo.domain.eticket.model;

import com.kwaishop.digital.access.demo.proxy.response.AvailableEticketDetail;

import lombok.Data;

/**
 * @author pengjianfei
 * Created on 2022-05-20
 */
@Data
public class AvailableEticketDetailDTO {
    private String code;

    private Long itemId;

    private Integer num;

    private String id;

    private Long buyerId;

    private Long leftNum;

    private String eticketType;

    private Long orderId;

    private Long sellerId;

    private String displayStatus;

    private String token;

    public AvailableEticketDetailDTO() {

    }

    public  AvailableEticketDetailDTO(AvailableEticketDetail availableEticketDetail) {
        code = availableEticketDetail.getCode();
        itemId = availableEticketDetail.getItemId();
        num = availableEticketDetail.getNum();
        id = availableEticketDetail.getId();
        buyerId = availableEticketDetail.getBuyerId();
        leftNum = availableEticketDetail.getLeftNum();
        eticketType = availableEticketDetail.getEticketType();
        orderId = availableEticketDetail.getOrderId();
        sellerId = availableEticketDetail.getSellerId();
        displayStatus = availableEticketDetail.getDisplayStatus();
        token = availableEticketDetail.getToken();
    }
}
