package com.github.qiu121.mapper;

import com.github.qiu121.vo.GoodsVO;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author to_Geek
 * @version 1.0
 * @date 2023/12/18
 */
@SpringBootTest
class GoodsMapperTest {
    @Resource
    private GoodsMapper goodsMapper;

    @Test
    void findGoodsVo() {
        goodsMapper.findGoodsVo().forEach(System.out::println);
    }

    @Test
    void findGoodsVoByGoodsId() {
        GoodsVO goodsVO = goodsMapper.findGoodsVoByGoodsId(1L);
        System.out.println(goodsVO);
    }
}