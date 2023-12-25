package com.github.qiu121.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.qiu121.entity.Order;
import com.github.qiu121.entity.User;
import com.github.qiu121.vo.GoodsVO;
import com.github.qiu121.vo.OrderDetailVO;

/**
 * @author to_Geek
 * @version 1.0
 * @since 2023/12/17
 */
public interface IOrderService extends IService<Order> {

    /**
     * 秒杀
     * @param user 用户
     * @param goodsVO 秒杀商品
     * @return 订单
     */
    Order seckill(User user, GoodsVO goodsVO);

    OrderDetailVO detail(Long orderId);
}
