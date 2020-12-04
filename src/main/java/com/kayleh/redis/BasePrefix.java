package com.kayleh.redis;

/**
 * 抽象类中构造方法的理解：其中的构造方法与普通类中的构造方法长得一样，
 * 不过它不能用来构造自己，因为它是抽象的，不能实例化，
 * 但是一旦子类实现了该抽象类，那么子类便可以调用其抽象类的构造函数进行实例化
 *
 * @Author: Kayleh
 * @Date: 2020/12/3 19:58
 */
public abstract class BasePrefix implements KeyPrefix
{
    private int expireSeconds;

    private String prefix;

    public BasePrefix(String prefix)
    {
        this(0, prefix);
    }

    public BasePrefix(int expireSeconds, String prefix)
    {
        this.expireSeconds = expireSeconds;
        this.prefix = prefix;
    }

    @Override
    public int expireSeconds()
    {
        //默认0表示永不过期
        return expireSeconds;
    }

    @Override
    public String getPrefix()
    {
        //获取前缀，前面添加类名
        String className = getClass().getSimpleName();
        return className + ":" + prefix;
    }
}
