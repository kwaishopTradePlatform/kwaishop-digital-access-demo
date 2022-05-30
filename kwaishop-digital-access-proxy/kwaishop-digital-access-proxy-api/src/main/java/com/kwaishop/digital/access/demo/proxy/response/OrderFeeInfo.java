package com.kwaishop.digital.access.demo.proxy.response;

import lombok.Data;

/**
 * @author zhangkwei
 * Created on 2022-05-22
 */
@Data
public class OrderFeeInfo {
    private Long oid;
    private Long sellerId;
    // 货款, 商家维度，不包括运费 计算公式为：商品总金额(price * num)- 商家承担(merchantBearAmount)
    private Long totalFee;
    // 订单优惠金额，累加marketingInfoOuters所有类型的优惠
    private Long discountFee;
    // 运费
    private Long expressFee;
    // 优惠补贴，platformBearAmount + merchantBearAmount
    private Long platformAllowance;
    // 商家实收，货款 + 运费
    private Long sellerRecv;
    // 买家实付，货款 + 运费 - 平台承担补贴（platformBearAmount）
    private Long buyerPay;
    // 平台补贴中平台承担的费用 新模型计算公式（marketingInfoOuters.bearInfoList不为null）：累加marketingInfoOuters.bearInfoList 中平台承担类型的bearAmount 旧模型计算公式（marketingInfoOuters.bearInfoList为null）：取marketingInfoOuters子类型1002的discountFee
    private Long platformBearAmount;
    // 平台补贴中商家承担的费用 计算公式：累加marketingInfoOuters.bearInfoList 中商家承担类型的bearAmount
    private Long merchantBearAmount;

}
