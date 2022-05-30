package com.kwaishop.digital.access.demo.domain.order.model;

import lombok.Data;

/**
 * @author zhangyiying
 * Created on 2022-05-17
 */
@Data
public class OrderBaseInfo {
    /**
     * 订单id
     */
    private Long oid;

    /**
     * 付款时间
     */
    private Long payTime;

    /**
     * 买家图像
     */
    private String buyerImage;

    /**
     * 买家id
     */
    private String buyerOpenId;

    /**
     * 买家昵称
     */
    private String buyerNick;

    /**
     * 卖家id
     */
    private String sellerOpenId;

    /**
     * 卖家昵称
     */
    private String sellerNick;

    /**
     * 运费
     */
    private Long expressFee;

    /**
     * 促销减价、折扣价格
     */
    private Long discountFee;

    /**
     * 子订单商品总价
     */
    private Long totalFee;

    /**
     * 订单状态：[0, "未知状态"], [10, "待付款"], [30, "已付款"], [40, "已发货"], [50, "已签收"], [70, "订单成功"], [80, "订单失败"];
     */
    private Integer status;

    /**
     * 发货时间
     */
    private Long sendTime;

    /**
     * 退款时间
     */
    private Long refundTime;

    /**
     * 创建时间
     */
    private Long createTime;

    /**
     * 更新时间
     */
    private Long updateTime;

    /**
     * 买家留言
     */
    private String remark;

    /**
     * 发货超时间隔时间，单位：天
     */
    private Long theDayOfDeliverGoodsTime;

    /**
     * 非预售商品承诺发货时间点,即订单支付时间+承诺发货时间间隔
     */
    private Long promiseTimeStampOfDelivery;

    /**
     * 活动类型
     */
    private Integer activityType;

    /**
     * 分销类型 0-全部 1-普通订单 2-分销订单
     */
    private Integer cpsType;

    /**
     * 预售发货时间/承诺发货时间
     */
    private Long validPromiseShipmentTimeStamp;

    /**
     * 预售：preSale 0：非预售 1:预售
     */
    private Integer preSale;

    /**
     * 收货时间
     */
    private Long recvTime;

    /**
     * 2手机充值，3券包，9娱乐会员，13油卡，14电子凭证
     */
    private Integer coType;

    /**
     * 评价状态： 0未评价，1评价
     */
    private Integer commentStatus;

    /**
     * 支付方式，1=微信；2=支付宝
     */
    private Integer payType;

    /**
     * 风险Code码，10001=黑产订单，不建议商家发货
     */
    private Long riskCode;
}
