package com.kayleh.redis;

/**
 * @Author: Kayleh
 * @Date: 2020/12/12 0:24
 */
public class SpikeKey extends BasePrefix
{
    public SpikeKey(int expireSeconds, String prefix)
    {
        super(expireSeconds, prefix);
    }

    public static SpikeKey isGoodsOver = new SpikeKey(0, "go");
    public static SpikeKey getSpikePath = new SpikeKey(60, "sp");
    public static SpikeKey getSpikeVerifyCode = new SpikeKey(300, "vc");
}
