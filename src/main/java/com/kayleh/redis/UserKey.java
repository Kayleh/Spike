package com.kayleh.redis;

/**
 * 展现出抽象类的实现类
 * <p>
 * 它的构造函数就是用的抽象父类中的构造函数，而且定义了两个静态字段，
 * 一种是根据Id来生成前缀，前缀格式会根据getPrefix()方法，表示为类名+：+id
 *
 * @Author: Kayleh
 * @Date: 2020/12/3 21:08
 */
public class UserKey extends BasePrefix
{
    public UserKey(String prefix)
    {
        super(prefix);
    }

    //UserKey的两种前缀形式,一种是根据id另一种根据name
    public static UserKey getById = new UserKey("id");
    public static UserKey getByName = new UserKey("name");
}
