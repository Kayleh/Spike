package com.kayleh.redis;

/**
 * @Author: Kayleh
 * @Date: 2020/12/13 1:47
 */
public class AccessKey extends BasePrefix
{
    private AccessKey(int expireSeconds, String prefix)
    {
        super(expireSeconds, prefix);
    }

    public static AccessKey withExpire(int expireSeconds)
    {
        return new AccessKey(expireSeconds, "access");
    }
}
