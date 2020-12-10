package com.kayleh.domain;

import java.util.Date;

/**
 * @Author: Kayleh
 * @Date: 2020/12/9 0:39
 */
public class OrderInfo
{
    private Long id;
    private Long userId;
    private Long goodsId;
    private Long deliverAddrId;
    private String goodsName;
    private Integer goodsCount;
    private Double goodsPrice;
    private Integer orderChannel;//渠道
    private Integer status;
    private Date createDate;
    private Date payDate;

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

    public Long getGoodsId()
    {
        return goodsId;
    }

    public void setGoodsId(Long goodsId)
    {
        this.goodsId = goodsId;
    }

    public Long getDeliverAddrId()
    {
        return deliverAddrId;
    }

    public void setDeliverAddrId(Long deliverAddrId)
    {
        this.deliverAddrId = deliverAddrId;
    }

    public String getGoodsName()
    {
        return goodsName;
    }

    public void setGoodsName(String goodsName)
    {
        this.goodsName = goodsName;
    }

    public Integer getGoodsCount()
    {
        return goodsCount;
    }

    public void setGoodsCount(Integer goodsCount)
    {
        this.goodsCount = goodsCount;
    }

    public Double getGoodsPrice()
    {
        return goodsPrice;
    }

    public void setGoodsPrice(Double goodsPrice)
    {
        this.goodsPrice = goodsPrice;
    }

    public Integer getOrderChannel()
    {
        return orderChannel;
    }

    public void setOrderChannel(Integer orderChannel)
    {
        this.orderChannel = orderChannel;
    }

    public Integer getStatus()
    {
        return status;
    }

    public void setStatus(Integer status)
    {
        this.status = status;
    }

    public Date getCreateDate()
    {
        return createDate;
    }

    public void setCreateDate(Date createDate)
    {
        this.createDate = createDate;
    }

    public Date getPayDate()
    {
        return payDate;
    }

    public void setPayDate(Date payDate)
    {
        this.payDate = payDate;
    }
}
