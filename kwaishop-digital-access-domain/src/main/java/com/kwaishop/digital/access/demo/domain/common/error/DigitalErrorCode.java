package com.kwaishop.digital.access.demo.domain.common.error;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangyiying
 * Created on 2022-05-18
 */

@Slf4j
public enum DigitalErrorCode {
    UNKNOWN(-1L, "未知"),

    /**
     * =============== 通用基础错误码 ===============
     */
    SUCCESS(1L, "成功"),

    OPERATE_TOO_FAST(2L, "被限流", true),
    TO_MANY_REQ(8L, "请求太多被限流", true),
    OPERATE_TOO_FAST_RETRY(9L, "Throttling限流", true),

    SERVER_ERROR(11L, "服务异常，请稍后重试！", true),
    PARAM_INVALID(21L, "参数非法"),

    /**
     * =============== 自定义业务错误码 ===============
     */
    PARSE_EVENT_ERROR(1001L, "解析消息错误"),
    LIST_ORDER_ERROR(1002L, "查询订单列表异常"),
    QUERY_ORDER_DETAIL_ERROR(1003L, "查询订单详情异常"),
    ADD_ITEM_ERROR(1003L, "新增商品异常"),
    MOCK_ORDER_ERROR(1004L, "mock订单异常"),
    CHECK_AVAILABLE_ETICKET_ERROR(1005L, "检查可用电子凭证异常"),
    ;

    DigitalErrorCode(Long code, String message) {
        this.code = code;
        this.message = message;
        this.systemError = false;
    }

    DigitalErrorCode(Long code, String message, boolean systemError) {
        this.code = code;
        this.message = message;
        this.systemError = systemError;
    }

    private Long code;

    private String message;

    private boolean systemError;

    public Long getErrorCode() {
        return code;
    }

    public String getErrorInfo() {
        return message;
    }
}
