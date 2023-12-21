package com.github.qiu121.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.github.qiu121.entity.User;
import com.github.qiu121.service.IGoodsService;
import com.github.qiu121.service.IUserService;
import com.github.qiu121.vo.GoodsVO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * @author to_Geek
 * @version 1.0
 * @since 2023/12/17
 */
@Controller
@RequestMapping("/goods")
@Slf4j
public class GoodsController {
    @Resource
    private IGoodsService goodsService;
    @Resource
    private IUserService userService;

    /**
     * 跳转到商品列表页面
     *
     * @param model 模型
     * @return goodsList.html
     */
    @GetMapping("/toList")
    public String toList(Model model) {
        model.addAttribute("goodsList", goodsService.findGoodsVo());
        return "goodsList";
    }

    /**
     * 跳转到商品详情页面
     *
     * @param goodsId 商品id
     * @param model  模型
     * @return goodsDetail.html
     */
    @GetMapping("/toDetail/{goodsId}")
    public String toDetail(Model model, @PathVariable Long goodsId) {
        String userId = (String) StpUtil.getLoginId();
        User user = userService.getById(userId);

        model.addAttribute("user", user);
        GoodsVO goodsVO = goodsService.findGoodsVoByGoodsId(goodsId);
        LocalDateTime startDate = goodsVO.getStartDate();
        LocalDateTime endDate = goodsVO.getEndDate();
        LocalDateTime now = LocalDateTime.now();

        // 秒杀状态
        int seckillStatus;
        // 秒杀倒计时
        long remainSeconds;
        if (now.isBefore(startDate)) { // 秒杀时间之前，开始倒计时
            seckillStatus = 0;
            // LocalDateTime时间类型计算差值，使用Duration类
            Duration between = Duration.between(now, startDate);
            remainSeconds = between.getSeconds();
        } else if (now.isAfter(endDate)) {// 秒杀已经结束
            seckillStatus = 2;
            remainSeconds = -1;

        } else {// 秒杀进行中
            seckillStatus = 1;
            remainSeconds = 0;
        }
        log.info("{}", seckillStatus);
        log.info("{}", remainSeconds);
        model.addAttribute("seckillStatus", seckillStatus);
        model.addAttribute("remainSeconds", remainSeconds);
        model.addAttribute("goods", goodsVO);
        return "goodsDetail";
    }
}
