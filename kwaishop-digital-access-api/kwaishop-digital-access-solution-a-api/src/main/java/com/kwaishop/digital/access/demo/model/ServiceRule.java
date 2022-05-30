package com.kwaishop.digital.access.demo.model;

import lombok.Data;

@Data
public class ServiceRule {
    /**
     * 退款规则，请求内容为枚举数值，非后面文案： 1:支持7天无理由退货 4:不支持7天无理由退货 5:支持7天无理由退货(拆封后不支持) 6:支持7天无理由退货(激活后不支持) 7:支持7天无理由退货(安装后不支持)
     * 8:支持7天无理由退货(定制类不支持) 9:支持7天无理由退货(使用后不支持) ，由open.item.category.config获得（退款规则会根据类目变化而变化） 11:随时退过期退
     */
    private String refundRule;

    /**
     * 发货间隔时间，单位：秒，范围在[4,90天] 需要转换为秒 345600
     * 为4天，deliverGoodsInteralTime和promiseDeliveryTime至少填一个且不能都填。填deliverGoodsInteralTime代码预售商品，填promiseDeliveryTime
     * 代表非预售商品。（如果需要取消请传 0 或 -1）
     */
    private Long deliverGoodsInteralTime;

    /**
     * 非预售商品承诺发货时间，单位：秒，取值86400,172800,259200,
     * 分别代表24、48、72小时，deliverGoodsInteralTime和promiseDeliveryTime至少填一个且不能都填。填deliverGoodsInteralTime
     * 代码预售商品，填promiseDeliveryTime代表非预售商品。（如果需要取消请传 0 或 -1）
     */
    private Long promiseDeliveryTime;

    /**
     * 是否立即上架 ,0立即上架，1不立即上架
     */
    private Integer immediatelyOnOfflineFlag;

    /**
     * 发货方式 logistics：物流配送（默认） certificate：电子凭证
     */
    private String deliveryMethod;

    /**
     * 服务承诺：freshRotRefund：坏了包退 brokenRefund：破损包退 allergyRefund：过敏包退
     */
    private OpenApiServicePromise servicePromise;

    /**
     * 电子凭证不可用时间
     */
    private OpenApiUnavailableTimeRule unavailableTimeRule;

    /**
     * 码商，仅有deliveryMethod 为certificate：电子凭证时，此字段才有值，且需校验 ks：快手平台发码 merchant：商家发码 couponLibrary:券码库发码
     */
    private String certMerchantCode;

    /**
     * 电子凭证有效期类型，仅有deliveryMethod 为certificate：电子凭证时，此字段才有值 1：日期范围；2：仅有截止日期；3：有效天数
     */
    private Integer certExpireType;

    /**
     * 开始时间，仅有deliveryMethod 为certificate：电子凭证时，certExpireType为1或2时此字段才有值
     */
    private Long certStartTime;

    /**
     * 截止时间，仅有deliveryMethod 为certificate：电子凭证时，certExpireType为1或2时此字段才有值
     */
    private Long certEndTime;

    /**
     * 有效天数，仅有deliveryMethod 为certificate：电子凭证时，certExpireType为3时此字段才有值
     */
    private Long certExpDays;
}