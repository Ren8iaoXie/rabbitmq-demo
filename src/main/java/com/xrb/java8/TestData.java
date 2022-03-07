package com.xrb.java8;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author xieren8iao
 * @date 2021/8/4 10:54 上午
 */
@Data
public class TestData {
    private String valueFour;

    private Integer num;

    private BigDecimal money;

    /**
     * 商品多单位主键
     */
    private Long id;

    /**
     * 企业id
     */
    private String companyId;

    /**
     * 商品id
     */
    private Long itemId;

    /**
     * 商品小包装编码
     */
    private String itemUnit;

    private Date date;
}