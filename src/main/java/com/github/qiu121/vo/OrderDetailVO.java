package com.github.qiu121.vo;

import com.github.qiu121.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author to_Geek
 * @version 1.0
 * @date 2023/12/25
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailVO {
    private GoodsVO goodsVO;
    private Order order;
}
