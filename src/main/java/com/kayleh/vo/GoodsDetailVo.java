package com.kayleh.vo;

import com.kayleh.domain.SpikeUser;

/**
 * @Author: Kayleh
 * @Date: 2020/12/8 0:52
 */
public class GoodsDetailVo
{
    private int spikeStatus = 0;
    private int remainSeconds = 0;
    private GoodsVo goods;
    private SpikeUser user;

    public int getSpikeStatus()
    {
        return spikeStatus;
    }

    public void setSpikeStatus(int spikeStatus)
    {
        this.spikeStatus = spikeStatus;
    }

    public int getRemainSeconds()
    {
        return remainSeconds;
    }

    public void setRemainSeconds(int remainSeconds)
    {
        this.remainSeconds = remainSeconds;
    }

    public GoodsVo getGoods()
    {
        return goods;
    }

    public void setGoods(GoodsVo goods)
    {
        this.goods = goods;
    }

    public SpikeUser getUser()
    {
        return user;
    }

    public void setUser(SpikeUser user)
    {
        this.user = user;
    }
}
