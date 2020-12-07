package com.kayleh.redis;

/**
 * @Author: Kayleh
 * @Date: 2020/12/6 21:16
 */
public class GoodsKey extends BasePrefix
{
    private GoodsKey(int expiredSeconds, String prefix)
    {
        super(expiredSeconds, prefix);
    }

    public static GoodsKey getGoodsList = new GoodsKey(60, "gl");
    public static GoodsKey getGoodsDetail = new GoodsKey(60, "gd");
    public static GoodsKey getSpikeGoodsStock = new GoodsKey(0, "gs");
}
