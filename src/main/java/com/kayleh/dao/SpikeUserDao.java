package com.kayleh.dao;

import com.kayleh.domain.SpikeUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

/**
 * @Author: Kayleh
 * @Date: 2020/12/6 0:41
 */
@Mapper
@Component
public interface SpikeUserDao
{
    @Select("select * from spike_user where id = #{id}")
    public SpikeUser getById(@Param("id") long id);

    @Update("update spike_user set password = #{password} where id = #{id}")
    public void update(SpikeUser toBeUpdate);
}
