package com.kayleh.redis;

/**
 * @Author: Kayleh
 * @Date: 2020/12/6 0:50
 */
public class SpikeUserKey extends BasePrefix
{
    public static final int TOKEN_EXPIRE = 3600 * 24 * 2;

    private SpikeUserKey(int expireSeconds, String prefix)
    {
        super(expireSeconds, prefix);
    }

    public static SpikeUserKey token = new SpikeUserKey(TOKEN_EXPIRE, "tk");
    public static SpikeUserKey getById = new SpikeUserKey(0, "id");
}
