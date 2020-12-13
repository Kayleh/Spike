package com.kayleh.redis;

/**
 * @Author: Kayleh
 * @Date: 2020/12/11 19:12
 */
public class OrderKey extends BasePrefix
{
    public OrderKey(String prefix)
    {
        super(prefix);
    }

    public static OrderKey getSpikeOrderByUidGid = new OrderKey("moug");
}
