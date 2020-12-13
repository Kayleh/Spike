package com.kayleh.access;

import com.kayleh.domain.SpikeUser;

/**
 * @Author: Kayleh
 * @Date: 2020/12/13 1:25
 */
public class UserContext
{
    //用ThreadLocal来装user信息，调用它的set和get方法，向其中存储值
    //ThreadLocal是为当前线程存储值，所以，在多线程下，各个线程的user并不冲突
    private static ThreadLocal<SpikeUser> userHolder = new ThreadLocal<SpikeUser>();

    public static void setUser(SpikeUser user)
    {
        userHolder.set(user);
    }

    public static SpikeUser getUser()
    {
        return userHolder.get();
    }
}
