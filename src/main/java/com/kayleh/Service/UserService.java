package com.kayleh.Service;

import com.kayleh.dao.UserDao;
import com.kayleh.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: Kayleh
 * @Date: 2020/12/3 16:04
 */
@Service
public class UserService
{
    @Autowired
    UserDao userMapper;

    @Transactional
    public boolean tx()
    {
        User user1 = new User();
        user1.setId(2);
        user1.setName("222");
        userMapper.insertUser(user1);
        User user2 = new User();
        user2.setId(3);
        user2.setName("333");
        userMapper.insertUser(user2);
        return true;
    }
}
