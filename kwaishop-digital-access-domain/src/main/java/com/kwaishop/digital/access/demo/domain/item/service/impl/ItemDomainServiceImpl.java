package com.kwaishop.digital.access.demo.domain.item.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kwaishop.digital.access.demo.domain.common.error.DigitalErrorCode;
import com.kwaishop.digital.access.demo.domain.common.error.ServiceException;
import com.kwaishop.digital.access.demo.domain.item.request.AddItemDomainReq;
import com.kwaishop.digital.access.demo.domain.item.request.DeductItemStockDomainReq;
import com.kwaishop.digital.access.demo.domain.item.request.UpdateShelfItemDomainReq;
import com.kwaishop.digital.access.demo.domain.item.response.ItemAddDomainResponse;
import com.kwaishop.digital.access.demo.domain.item.service.ItemDomainService;
import com.kwaishop.digital.access.demo.proxy.KwaiETicketAdaptorService;
import com.kwaishop.digital.access.demo.proxy.request.UpdateShelfItemStatusParam;
import com.kwaishop.digital.access.demo.proxy.response.ItemAddProxyResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangkwei
 * Created on 2022-05-17
 */
@Service
@Slf4j
@Lazy
public class ItemDomainServiceImpl implements ItemDomainService {


    @Autowired
    private KwaiETicketAdaptorService kwaiETicketAdaptorService;

    @Override
    public boolean updateShelfItemStatus(UpdateShelfItemDomainReq updateShelfItemDomainReq) {
        kwaiETicketAdaptorService.updateShelfItemStatus(buildUpdateShelfItemStatusReq(updateShelfItemDomainReq));
        return false;
    }

    @Override
    public boolean deductItemStock(DeductItemStockDomainReq deductItemStockDomainReq) {
        // 1、isv内部系统逻辑处理，库存扣减等
        return true;
    }

    @Override
    public ItemAddDomainResponse addItem(AddItemDomainReq addItemDomainReq) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            ItemAddProxyResponse itemAddProxyResponse =
                    kwaiETicketAdaptorService.addItem(objectMapper.writeValueAsString(addItemDomainReq));
            return new ItemAddDomainResponse(itemAddProxyResponse);
        } catch (ServiceException exception) {
            throw exception;
        } catch (Exception e) {
            log.error("addItem error:", e);
            throw new ServiceException(DigitalErrorCode.ADD_ITEM_ERROR);
        }
    }


    private UpdateShelfItemStatusParam buildUpdateShelfItemStatusReq(UpdateShelfItemDomainReq updateShelfItemDomainReq) {
        UpdateShelfItemStatusParam updateShelfItemStatusParam = new UpdateShelfItemStatusParam();
        updateShelfItemStatusParam.setKwaiItemId(updateShelfItemDomainReq.getKwaiItemId());
        updateShelfItemStatusParam.setShelfStatus(updateShelfItemDomainReq.getShelfStatus());

        return updateShelfItemStatusParam;
    }

    //    private DeductItemStockReq buildDeductItemStockReq(DeductItemStockDomainReq deductItemStockDomainReq) {
    //        DeductItemStockReq deductItemStockReq = new DeductItemStockReq();
    //        DeductItemStockParam deductItemStockParam = new DeductItemStockParam();
    //        deductItemStockParam.setProvince(deductItemStockDomainReq.getProvince());
    //        deductItemStockParam.setCity(deductItemStockDomainReq.getCity());
    //        deductItemStockParam.setArea(deductItemStockDomainReq.getArea());
    //        deductItemStockParam.setOrderSkuStockList(deductItemStockDomainReq.getOrderSkuStockList());
    //        deductItemStockParam.setNumber(deductItemStockDomainReq.getNumber());
    //        deductItemStockParam.setOrderId(deductItemStockDomainReq.getOrderId());
    //        deductItemStockParam.setSkuId(deductItemStockDomainReq.getSkuId());
    //        deductItemStockParam.setAddress(deductItemStockDomainReq.getAddress());
    //        deductItemStockParam.setKsSkuId(deductItemStockDomainReq.getKsSkuId());
    //
    //        deductItemStockReq.setParam(deductItemStockParam);
    //        buildBaseReq(deductItemStockReq, DEDUCT_ITEM_STOCK_METHOD_NAME);
    //
    //        return deductItemStockReq;
    //    }
}
