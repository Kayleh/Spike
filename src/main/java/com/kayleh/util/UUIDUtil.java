package com.kayleh.util;

import java.util.UUID;

/**
 * @Author: Kayleh
 * @Date: 2020/12/6 14:10
 */
public class UUIDUtil
{
    public static String uuid()
    {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
