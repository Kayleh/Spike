package com.kayleh.vo;

import com.kayleh.domain.OrderInfo;

/**
 * @Author: Kayleh
 * @Date: 2020/12/13 11:56
 */
public class OrderDetailVo
{
    private GoodsVo goods;
    private OrderInfo order;

    public GoodsVo getGoods()
    {
        return goods;
    }

    public void setGoods(GoodsVo goods)
    {
        this.goods = goods;
    }

    public OrderInfo getOrder()
    {
        return order;
    }

    public void setOrder(OrderInfo order)
    {
        this.order = order;
    }
}
