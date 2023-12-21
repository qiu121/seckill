package com.github.qiu121.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
     * @param model   模型
     * @param goodsId 商品id
     * @return orderDetail.html
     */
    @PostMapping("/doSeckill")
    public String doSeckill(Model model, Long goodsId) {
        // 用户ID(即手机号、主键)
        String userId = (String) StpUtil.getLoginId();
        User user = userService.getById(userId);
        model.addAttribute("user", user);

        GoodsVO goodsVO = goodsService.findGoodsVoByGoodsId(goodsId);
        // 判断秒杀商品库存
        if (goodsVO.getStockCount() < 1) {
            model.addAttribute("errMsg", ResultEnum.EMPTY_STOCK.getMessage());
            return "seckillFail";
        }
        // 判断是否已经秒杀过
        SeckillOrder seckillOrder = seckillOrderService.getOne(new LambdaQueryWrapper<SeckillOrder>()
                // .eq(SeckillOrder::getUserId, user.getId())
                .eq(SeckillOrder::getUserId, userId)
                .eq(SeckillOrder::getGoodsId, goodsId), false);// 若出现多个结果，不抛出异常，否则需要另外异常处理
        if (seckillOrder != null) {
            model.addAttribute("errMsg", ResultEnum.REPEATED_BUY.getMessage());
            return "seckillFail";
        }

        Order order = orderService.seckill(user, goodsVO);
        model.addAttribute("order", order);
        model.addAttribute("goods", goodsVO);
        return "orderDetail";
    }
}
