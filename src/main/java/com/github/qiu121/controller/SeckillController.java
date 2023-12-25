package com.github.qiu121.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.qiu121.common.Result;
import com.github.qiu121.common.ResultEnum;
import com.github.qiu121.entity.Order;
import com.github.qiu121.entity.SeckillOrder;
import com.github.qiu121.entity.User;
import com.github.qiu121.service.IGoodsService;
import com.github.qiu121.service.IOrderService;
import com.github.qiu121.service.ISeckillOrderService;
import com.github.qiu121.service.IUserService;
import com.github.qiu121.vo.GoodsVO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author to_Geek
 * @version 1.0
 * @date 2023/12/20
 */
@Controller
@Slf4j
@RequestMapping("/seckill")
public class SeckillController {
    @Resource
    private IGoodsService goodsService;
    @Resource
    private IOrderService orderService;
    @Resource
    private ISeckillOrderService seckillOrderService;
    @Resource
    private IUserService userService;

    /**
     * 秒杀
     *
     * @param goodsId 商品id
     * @return orderDetail.html
     */
    @PostMapping("/doSeckill")
    @ResponseBody
    public Result doSeckill(Long goodsId) {
        // 用户ID(即手机号、主键)
        String userId = (String) StpUtil.getLoginId();
        User user = userService.getById(userId);

        GoodsVO goodsVO = goodsService.findGoodsVoByGoodsId(goodsId);
        // 判断秒杀商品库存
        if (goodsVO.getStockCount() < 1) {
            return Result.error(ResultEnum.EMPTY_STOCK);
        }
        // 判断是否已经秒杀过
        SeckillOrder seckillOrder = seckillOrderService.getOne(new LambdaQueryWrapper<SeckillOrder>()
                // .eq(SeckillOrder::getUserId, user.getId())
                .eq(SeckillOrder::getUserId, userId)
                .eq(SeckillOrder::getGoodsId, goodsId), false);// 若出现多个结果，不抛出异常，否则需要另外异常处理
        if (seckillOrder != null) {
            return Result.error(ResultEnum.REPEATED_BUY);
        }

        Order order = orderService.seckill(user, goodsVO);
        return Result.success(order);
    }
}
