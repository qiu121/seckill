package com.github.qiu121.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author to_Geek
 * @version 1.0
 * @date 2023/12/25
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class GoodsDetailVO {
    private String userId;
    private GoodsVO goodsVO;
    private int seckillStatus;
    private long remainSeconds;
}
