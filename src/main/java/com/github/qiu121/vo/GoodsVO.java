package com.github.qiu121.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author to_Geek
 * @version 1.0
 * @date 2023/12/18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodsVO {

    private Long id;

    private String goodsName;

    private String goodsTitle;

    private String goodsImg;

    private String goodsDetail;

    private BigDecimal goodsPrice;

    private Integer goodsStock;

    private BigDecimal seckillPrice;

    private Integer stockCount;
    // @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startDate;
    // @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endDate;

}
