package com.kayleh.Service;

import com.kayleh.dao.SpikeUserDao;
import com.kayleh.domain.SpikeUser;
import com.kayleh.exception.GlobalException;
import com.kayleh.redis.RedisService;
import com.kayleh.redis.SpikeUserKey;
import com.kayleh.result.CodeMsg;
import com.kayleh.util.MD5Util;
import com.kayleh.util.UUIDUtil;
import com.kayleh.vo.LoginVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: Kayleh
 * @Date: 2020/12/5 19:41
 */
@Service
public class SpikeUserService
{
    public static final String COOKI_NAME_TOKEN = "token";

    @Autowired
    SpikeUserDao spikeUserDao;

    @Autowired
    RedisService redisService;

    public SpikeUser getById(long id)
    {
        //取缓存
        SpikeUser user = redisService.get(SpikeUserKey.getById, "" + id, SpikeUser.class);
        if (user != null)
        {
            return user;
        }
        //取数据库
        user = spikeUserDao.getById(id);
        if (user != null)
        {
            redisService.set(SpikeUserKey.getById, "" + id, user);
        }
        return user;
    }

    //http://blog.csdn.net/tTU1EvLDeLFq5btqiK/article/details/78693323
    public boolean updatePassword(String token, long id, String formPass)
    {
        //取user
        SpikeUser user = getById(id);
        if (user == null)
        {
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        }
        //更新数据库
        SpikeUser toBeUpdate = new SpikeUser();
        toBeUpdate.setId(id);
        toBeUpdate.setPassword(MD5Util.formPassToDbPass(formPass, user.getSalt()));
        spikeUserDao.update(toBeUpdate);
        //处理缓存
        redisService.delete(SpikeUserKey.getById, "" + id);
        user.setPassword(toBeUpdate.getPassword());
        redisService.set(SpikeUserKey.token, token, user);
        return true;
    }


    public SpikeUser getByToken(HttpServletResponse response, String token)
    {
        if (StringUtils.isEmpty(token))
        {
            return null;
        }
        SpikeUser user = redisService.get(SpikeUserKey.token, token, SpikeUser.class);
        //延长有效期
        if (user != null)
        {
            addCookie(response, token, user);
        }
        return user;
    }

    public String login(HttpServletResponse response, LoginVo loginVo)
    {
        if (loginVo == null)
        {
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }
        String moblie = loginVo.getMobile();
        String formPass = loginVo.getPassword();
        //判断手机号是否存在
        SpikeUser user = getById(Long.parseLong(moblie));
        if (user == null)
        {
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        }
        //验证密码
        String dbPass = user.getPassword();
        String saltDB = user.getSalt();
        String calcPass = MD5Util.formPassToDbPass(formPass, saltDB);
        if (!calcPass.equals(dbPass))
        {
            throw new GlobalException(CodeMsg.PASSWORD_ERROR);
        }
        //生成cookie
        String token = UUIDUtil.uuid();
        addCookie(response, token, user);
        return token;
    }

    private void addCookie(HttpServletResponse response, String token, SpikeUser user)
    {
        //首次登陆的时候，需要将Cookie存入Redis
        redisService.set(SpikeUserKey.token, token, user);
        Cookie cookie = new Cookie(COOKI_NAME_TOKEN, token);
        cookie.setMaxAge(SpikeUserKey.token.expireSeconds());
        //设置为根目录，则可以在整个应用范围内使用cookie
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
