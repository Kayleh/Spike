package com.kayleh.controller;

import com.kayleh.Service.SpikeUserService;
import com.kayleh.domain.SpikeUser;
import com.kayleh.redis.RedisService;
import com.kayleh.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: Kayleh
 * @Date: 2020/12/13 12:04
 */
@Controller
@RequestMapping("/user")
public class UserController
{
    @Autowired
    SpikeUserService userService;
    @Autowired
    RedisService redisService;

    @RequestMapping("/info")
    @ResponseBody
    public Result<SpikeUser> info(Model model, SpikeUser user)
    {
        return Result.success(user);
    }
}
