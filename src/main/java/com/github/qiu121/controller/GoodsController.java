package com.github.qiu121.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.github.qiu121.common.Result;
import com.github.qiu121.service.IGoodsService;
import com.github.qiu121.vo.GoodsDetailVO;
import com.github.qiu121.vo.GoodsVO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
    // @Resource
    // private StringRedisTemplate stringRedisTemplate;
    // @Resource
    // private ThymeleafViewResolver thymeleafViewResolver;

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
     * 页面缓存
     */
    // @GetMapping(value = "/toList", produces = "text/html;encoding=utf-8")
    // @ResponseBody
    // public String toList(Model model) {
    //
    //     String html = stringRedisTemplate.opsForValue().get("goodsList");
    //     if (StringUtils.hasLength(html)) {
    //         return html;
    //     }
    //     model.addAttribute("goodsList", goodsService.findGoodsVo());
    //     // WebContext context = new WebContext(request, response, request.getServletContext(), request.getLocale(), model.asMap());
    //     // html = thymeleafViewResolver.getTemplateEngine().process("goodsList", context);
    //
    //     Context context = new Context();
    //     context.setVariables(model.asMap());
    //     html = thymeleafViewResolver.getTemplateEngine().process("goodsList", context);
    //     if (StringUtils.hasLength(html)) {
    //         stringRedisTemplate.opsForValue().set("goodsList", html, Duration.ofSeconds(60));
    //     }
    //     return html;
    // }

    /**
     * 跳转到商品详情页面
     *
     * @param goodsId 商品id
     * @return goodsDetail.html.html
     */
    @GetMapping("/toDetail/{goodsId}")
    @ResponseBody
    public Result toDetail(@PathVariable Long goodsId) {
        String userId = (String) StpUtil.getLoginId();

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
        log.info("秒杀状态：{}", seckillStatus);
        log.info("秒杀倒计时：{}", remainSeconds);

        GoodsDetailVO goodsDetailVO = new GoodsDetailVO();
        goodsDetailVO.setUserId(userId)
                .setGoodsVO(goodsVO)
                .setSeckillStatus(seckillStatus)
                .setRemainSeconds(remainSeconds);
        return Result.success(goodsDetailVO);
    }
}
