package com.kayleh.rabbitmq;

import com.kayleh.domain.SpikeUser;

/**
 * @Author: Kayleh
 * @Date: 2020/12/11 17:10
 */
public class SpikeMessage
{
    private SpikeUser user;
    private long goodsId;

    public SpikeUser getUser()
    {
        return user;
    }

    public void setUser(SpikeUser user)
    {
        this.user = user;
    }

    public long getGoodsId()
    {
        return goodsId;
    }

    public void     setGoodsId(long goodsId)
    {
        this.goodsId = goodsId;
    }

}
