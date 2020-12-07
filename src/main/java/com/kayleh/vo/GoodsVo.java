package com.kayleh.vo;

import com.kayleh.domain.Goods;

import java.util.Date;

/**
 * @Author: Kayleh
 * @Date: 2020/12/6 20:59
 */
public class GoodsVo extends Goods
{
    private Double SpikePrice;
    private Integer StockCount;
    private Date startDate;
    private Date endDate;

    public Double getSpikePrice()
    {
        return SpikePrice;
    }

    public void setSpikePrice(Double spikePrice)
    {
        SpikePrice = spikePrice;
    }

    public Integer getStockCount()
    {
        return StockCount;
    }

    public void setStockCount(Integer stockCount)
    {
        StockCount = stockCount;
    }

    public Date getStartDate()
    {
        return startDate;
    }

    public void setStartDate(Date startDate)
    {
        this.startDate = startDate;
    }

    public Date getEndDate()
    {
        return endDate;
    }

    public void setEndDate(Date endDate)
    {
        this.endDate = endDate;
    }
}
