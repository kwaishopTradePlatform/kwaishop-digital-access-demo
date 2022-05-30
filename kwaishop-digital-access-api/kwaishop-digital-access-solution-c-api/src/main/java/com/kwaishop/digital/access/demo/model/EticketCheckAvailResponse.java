package com.kwaishop.digital.access.demo.model;

import java.util.List;

import lombok.Data;

/**
 * @author pengjianfei
 * Created on 2022-05-19
 */
@Data
public class EticketCheckAvailResponse {
    List<AvailableEticketDetail> etickets;
}
