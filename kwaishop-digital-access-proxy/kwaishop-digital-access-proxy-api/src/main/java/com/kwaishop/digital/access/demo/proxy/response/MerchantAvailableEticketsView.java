package com.kwaishop.digital.access.demo.proxy.response;

import java.util.List;

import lombok.Data;

/**
 * @author pengjianfei
 * Created on 2022-05-20
 */
@Data
public class MerchantAvailableEticketsView {

    private List<AvailableEticketDetail> etickets;

}
