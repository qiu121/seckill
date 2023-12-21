package com.github.qiu121.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.qiu121.entity.Goods;
import com.github.qiu121.mapper.GoodsMapper;
import com.github.qiu121.service.IGoodsService;
import com.github.qiu121.vo.GoodsVO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author to_Geek
* @since 2023/12/17
* @version 1.0
*/
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements IGoodsService {

    @Resource
    private GoodsMapper goodsMapper;

    /**
     * 查询商品列表
     * @return 商品列表
     */
    @Override
    public List<GoodsVO> findGoodsVo() {
        return goodsMapper.findGoodsVo();
    }

    /**
     * 根据商品id查询商品详情
     * @param goodsId 商品id
     * @return 商品详情
     */
    @Override
    public GoodsVO findGoodsVoByGoodsId(Long goodsId) {
        return goodsMapper.findGoodsVoByGoodsId(goodsId);
    }
}
