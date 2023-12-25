package com.github.qiu121.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.qiu121.entity.Order;
import com.github.qiu121.entity.SeckillGoods;
import com.github.qiu121.entity.SeckillOrder;
import com.github.qiu121.entity.User;
import com.github.qiu121.mapper.GoodsMapper;
import com.github.qiu121.mapper.OrderMapper;
import com.github.qiu121.mapper.SeckillGoodsMapper;
import com.github.qiu121.mapper.SeckillOrderMapper;
import com.github.qiu121.service.IOrderService;
import com.github.qiu121.vo.GoodsVO;
import com.github.qiu121.vo.OrderDetailVO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


/**
 * @author to_Geek
 * @version 1.0
 * @since 2023/12/17
 */
@Service
@Slf4j
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

    @Resource
    private SeckillGoodsMapper seckillGoodsMapper;
    @Resource
    private OrderMapper orderMapper;
    @Resource
    private SeckillOrderMapper seckillOrderMapper;
    @Resource
    private GoodsMapper goodsMapper;

    /**
     * 秒杀
     *
     * @param user    用户
     * @param goodsVO 秒杀商品
     * @return 订单
     */
    @Override
    public Order seckill(User user, GoodsVO goodsVO) {
        // 查询秒杀商品库存
        SeckillGoods seckillGoods = seckillGoodsMapper.selectOne(new LambdaQueryWrapper<SeckillGoods>()
                .eq(SeckillGoods::getGoodsId, goodsVO.getId())
                .gt(SeckillGoods::getStockCount, 0)
        );

        // 减库存
        seckillGoods.setStockCount(seckillGoods.getStockCount() - 1);
        seckillGoodsMapper.update(seckillGoods, new LambdaQueryWrapper<SeckillGoods>()
                .eq(SeckillGoods::getGoodsId, goodsVO.getId())
                .gt(SeckillGoods::getStockCount, 0)
        );

        // 生成订单
        Order order = new Order()
                .setUserId(user.getId())
                .setGoodsId(goodsVO.getId())
                .setDeliveryAddrId(0L)
                .setGoodsName(goodsVO.getGoodsName())
                .setGoodsCount(1)
                .setGoodsPrice(seckillGoods.getSeckillPrice())
                .setOrderChannel((byte) 1)  // 渠道，1pc
                .setStatus((byte) 0)        // 0新建未支付
                .setCreateDate(LocalDateTime.now());
        orderMapper.insert(order);

        // 生成秒杀订单
        SeckillOrder seckillOrder = new SeckillOrder()
                // .setUserId(user.getId())
                .setUserId(user.getId())
                .setOrderId(order.getId())
                .setGoodsId(goodsVO.getId());
        seckillOrderMapper.insert(seckillOrder);

        return order;
    }

    /**
     * 订单详情
     *
     * @param orderId 订单id
     * @return 订单详情
     */
    @Override
    public OrderDetailVO detail(Long orderId) {
        Order order = orderMapper.selectById(orderId);

        GoodsVO goodsVO = goodsMapper.findGoodsVoByGoodsId(order.getGoodsId());
        OrderDetailVO orderDetailVO = new OrderDetailVO();
        orderDetailVO.setGoodsVO(goodsVO);
        orderDetailVO.setOrder(order);

        return orderDetailVO;
    }
}
