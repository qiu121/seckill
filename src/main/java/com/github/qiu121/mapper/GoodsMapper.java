package com.github.qiu121.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.qiu121.entity.Goods;
import com.github.qiu121.vo.GoodsVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author to_Geek
* @since 2023/12/17
* @version 1.0
*/
@Mapper
public interface GoodsMapper extends BaseMapper<Goods> {

    List<GoodsVO> findGoodsVo();

    GoodsVO findGoodsVoByGoodsId(Long goodsId);
}
