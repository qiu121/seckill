package com.github.qiu121.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.qiu121.entity.Goods;
import com.github.qiu121.vo.GoodsVO;

import java.util.List;

/**
 * @author to_Geek
 * @version 1.0
 * @since 2023/12/17
 */
public interface IGoodsService extends IService<Goods> {
    /**
     * 查询商品列表
     *
     * @return 商品列表
     */
    List<GoodsVO> findGoodsVo();

    /**
     * 根据商品id查询商品详情
     *
     * @param goodsId 商品id
     * @return 商品详情
     */
    GoodsVO findGoodsVoByGoodsId(Long goodsId);
}
