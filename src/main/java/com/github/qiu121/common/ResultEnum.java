package com.github.qiu121.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @author to_Geek
 * @version 1.0
 * @date 2023/12/16
 */
@ToString
@Getter
@AllArgsConstructor
public enum ResultEnum {
    SUCCESS(200, "SUCCESS"),
    ERROR(500, "服务端异常"),

    // 登录模块 5002XX
    LOGIN_ERROR(50201, "用户名或密码错误"),
    MOBILE_ERROR(50202, "手机号码格式有误"),
    BIND_ERROR(50203, "数据格式有误"),

    // 秒杀模块 5005XX
    EMPTY_STOCK(50501, "库存不足"),
    REPEATED_BUY(50501, "该商品每人限购一件"),

    // 订单模块 5006XX
    ORDER_NOT_EXIST(50601, "订单不存在")

    ;
    private final Integer code;
    private final String message;

}
