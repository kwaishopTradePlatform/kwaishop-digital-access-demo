package com.kwaishop.digital.access.starter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.kwaishop.digital.access.demo.domain.item.model.AddSkuDTO;
import com.kwaishop.digital.access.demo.domain.item.model.AddSkuPropDTO;
import com.kwaishop.digital.access.demo.domain.item.request.AddItemDomainReq;
import com.kwaishop.digital.access.demo.domain.item.request.AddItemPropValue;
import com.kwaishop.digital.access.demo.domain.item.request.CategoryPropValueParam;
import com.kwaishop.digital.access.demo.domain.item.request.OpenApiServicePromise;
import com.kwaishop.digital.access.demo.domain.item.request.ServiceRule;
import com.kwaishop.digital.access.demo.domain.item.response.ItemAddDomainResponse;
import com.kwaishop.digital.access.demo.domain.item.service.ItemDomainService;

/**
 * @author zhangyiying
 * Created on 2022-05-20
 */
@SpringBootTest(classes=Application.class)
@RunWith(SpringRunner.class)
public class ItemTest {

    @Autowired
    private ItemDomainService itemDomainService;

    // 创建商品，下单，发货，核销，销毁

    @Test
    public void testCreateItem() {
        AddItemDomainReq addItemDomainReq = new AddItemDomainReq();

        addItemDomainReq.setTitle("电子凭证测试商品");
        Random random = new Random();
        addItemDomainReq.setRelItemId(Math.abs(random.nextLong()));
        addItemDomainReq.setCategoryId(3689);
        addItemDomainReq.setCategoryName("小吃快餐");
        String[] imageUrls = new String[1];
        imageUrls[0] =
                "https://kcdn.staging.kuaishou.com/bs2/image-kwaishop-product/item-2173536129"
                        + "-7f8a392fc8554a61b935794d7bba4ea5.jpg?bp=10180";
        addItemDomainReq.setImageUrls(imageUrls);
        List<AddSkuDTO> addSkuDTOs = new ArrayList<>();
        AddSkuDTO addSkuDTO = new AddSkuDTO();
        addSkuDTO.setRelSkuId(Math.abs(random.nextLong()));
        addSkuDTO.setSkuStock(99999L);
        addSkuDTO.setSkuSalePrice(1L);
        addSkuDTO.setSkuNick("TS9988");
        List<AddSkuPropDTO> addSkuPropDTOS = new ArrayList<>();
        AddSkuPropDTO addSkuPropDTO = new AddSkuPropDTO();
        addSkuPropDTO.setPropName("套餐名");
        addSkuPropDTO.setPropValueName("双人餐");
        addSkuPropDTO.setIsMainProp(0);
        addSkuPropDTO.setPropValueGroupId(0L);
        addSkuPropDTO.setPropVersion(1);
        addSkuPropDTOS.add(addSkuPropDTO);
        addSkuDTO.setSkuProps(addSkuPropDTOS);
        addSkuDTO.setSkuMarketPrice(2L);
        addSkuDTOs.add(addSkuDTO);
        addItemDomainReq.setSkuList(addSkuDTOs);
        addItemDomainReq.setPurchaseLimit(false);
        AddItemPropValue[] addItemPropValues = new AddItemPropValue[2];

        AddItemPropValue addItemPropValue = new AddItemPropValue();
        addItemPropValue.setPropId(30622L);
        CategoryPropValueParam categoryPropValueParam = new CategoryPropValueParam();
        categoryPropValueParam.setPropValueId(1101141722L);
        categoryPropValueParam.setPropValue("团购套餐");
        addItemPropValue.setRadioPropValue(categoryPropValueParam);
        addItemPropValues[0] = addItemPropValue;

        AddItemPropValue brandProp = new AddItemPropValue();
        brandProp.setPropId(102L);
        brandProp.setPropName("品牌");
        brandProp.setPropType(2);
        CategoryPropValueParam brandPropValueParam = new CategoryPropValueParam();
        brandPropValueParam.setPropValueId(272256L);
        brandPropValueParam.setPropValue("茶客记");
        brandProp.setRadioPropValue(brandPropValueParam);
        addItemPropValues[1] = brandProp;
        addItemDomainReq.setItemPropValues(addItemPropValues);

        addItemDomainReq.setDetails("商品描述");
        addItemDomainReq.setStockPartner(false);
        addItemDomainReq.setItemRemark("商品备注");
        ServiceRule serviceRule = new ServiceRule();
        serviceRule.setRefundRule("11"); //随时退过期退
        serviceRule.setPromiseDeliveryTime(-1L);
        serviceRule.setImmediatelyOnOfflineFlag(0);
        serviceRule.setDeliveryMethod("certificate");
        serviceRule.setCertMerchantCode("merchant"); // ks快手发码，merchant商家发码，couponLibrary券码库发码
        serviceRule.setCertExpireType(3);
        serviceRule.setCertExpDays(30L);
        OpenApiServicePromise openApiServicePromise = new OpenApiServicePromise();
        serviceRule.setServicePromise(openApiServicePromise);
        addItemDomainReq.setServiceRule(serviceRule);
        addItemDomainReq.setSaleTimeFlag(false);
        addItemDomainReq.setPayWay(2);
        addItemDomainReq.setMultipleStock(false);
        addItemDomainReq.setExpressTemplateId(1L);
        addItemDomainReq.setPoiIds(Arrays.asList(3002703451617993287L));
        ItemAddDomainResponse response = itemDomainService.addItem(addItemDomainReq);
        System.out.println(response);
    }
}
