package com.github.qiu121.controller;

import com.github.qiu121.common.Result;
import com.github.qiu121.common.ResultEnum;
import com.github.qiu121.service.IOrderService;
import com.github.qiu121.vo.OrderDetailVO;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author to_Geek
 * @version 1.0
 * @date 2023/12/25
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    @Resource
    private IOrderService orderService;

    /**
     * 获取订单详情
     *
     * @param orderId 订单id
     * @return Result
     */
    @GetMapping("/detail")
    public Result getOrderDetail(Long orderId) {
        if (orderId == null) {
            throw new RuntimeException(ResultEnum.ORDER_NOT_EXIST.getMessage());
        }
        OrderDetailVO orderDetailVO = orderService.detail(orderId);
        return Result.success(orderDetailVO);
    }
}
