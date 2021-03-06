package com.kayleh.domain;

/**
 * @Author: Kayleh
 * @Date: 2020/12/9 0:01
 */
public class SpikeOrder
{
    private Long id;
    private Long userId;//用户ID
    private Long orderId;//订单ID
    private Long goodsId;//商品ID

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getUserId()
    {
        return userId;
    }

    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public Long getOrderId()
    {
        return orderId;
    }

    public void setOrderId(Long orderId)
    {
        this.orderId = orderId;
    }

    public Long getGoodsId()
    {
        return goodsId;
    }

    public void setGoodsId(Long goodsId)
    {
        this.goodsId = goodsId;
    }
}
