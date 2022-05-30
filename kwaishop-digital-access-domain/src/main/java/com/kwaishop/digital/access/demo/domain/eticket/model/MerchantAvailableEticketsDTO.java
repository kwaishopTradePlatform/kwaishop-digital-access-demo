package com.kwaishop.digital.access.demo.domain.eticket.model;

import java.util.ArrayList;
import java.util.List;

import com.kwaishop.digital.access.demo.proxy.response.AvailableEticketDetail;
import com.kwaishop.digital.access.demo.proxy.response.MerchantAvailableEticketsView;

import lombok.Data;

/**
 * @author pengjianfei
 * Created on 2022-05-20
 */
@Data
public class MerchantAvailableEticketsDTO {

    private List<AvailableEticketDetailDTO> etickets;

    public MerchantAvailableEticketsDTO() {}

    public MerchantAvailableEticketsDTO(MerchantAvailableEticketsView eticketsView) {
        etickets = new ArrayList<>();
        for (AvailableEticketDetail availableEticketDetail: eticketsView.getEtickets()){
            etickets.add(new AvailableEticketDetailDTO(availableEticketDetail));
        }
    }

}
