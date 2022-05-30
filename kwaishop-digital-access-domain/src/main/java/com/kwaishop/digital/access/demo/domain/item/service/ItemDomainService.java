package com.kwaishop.digital.access.demo.domain.item.service;

import com.kwaishop.digital.access.demo.domain.item.request.AddItemDomainReq;
import com.kwaishop.digital.access.demo.domain.item.request.DeductItemStockDomainReq;
import com.kwaishop.digital.access.demo.domain.item.request.UpdateShelfItemDomainReq;
import com.kwaishop.digital.access.demo.domain.item.response.ItemAddDomainResponse;

/**
 * @author zhangkwei
 * Created on 2022-05-17
 */
public interface ItemDomainService {
    boolean updateShelfItemStatus(UpdateShelfItemDomainReq updateShelfItemDomainReq);

    boolean deductItemStock(DeductItemStockDomainReq deductItemStockDomainReq);

    ItemAddDomainResponse addItem(AddItemDomainReq addItemDomainReq);
}
