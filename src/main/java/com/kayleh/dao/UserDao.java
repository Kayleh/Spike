package com.kayleh.dao;

import com.kayleh.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

/**
 * @Author: Kayleh
 * @Date: 2020/12/3 15:53
 */
@Mapper
@Component
public interface UserDao
{
    @Select("select * from user where id = #{id}")
    User selectById(@Param("id") int id);

    @Insert("insert into user (id,name) values (#{id},#{name})")
    int insertUser(User user);
}
