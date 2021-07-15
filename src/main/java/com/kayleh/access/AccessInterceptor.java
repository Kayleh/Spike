package com.kayleh.access;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.kayleh.Service.SpikeUserService;
import com.kayleh.domain.SpikeUser;
import com.kayleh.redis.AccessKey;
import com.kayleh.redis.RedisService;
import com.kayleh.result.CodeMsg;
import com.kayleh.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

/**
 * 实现拦截器 在方法之前进行拦截
 *
 * @Author: Kayleh
 * @Date: 2020/12/13 1:12
 */
@Service
public class AccessInterceptor extends HandlerInterceptorAdapter
{
    @Autowired
    SpikeUserService userService;

    @Autowired
    RedisService redisService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
    {
        if (handler instanceof HandlerMethod)
        {
            SpikeUser user = getUser(request, response);
            UserContext.setUser(user);
            HandlerMethod hm = (HandlerMethod) handler;
            AccessLimit accessLimit = hm.getMethodAnnotation(AccessLimit.class);
            if (accessLimit == null)
            {
                return true;
            }
            int seconds = accessLimit.seconds();
            int maxCount = accessLimit.maxCount();
            boolean needLogin = accessLimit.needLogin();
            String key = request.getRequestURI();//获取请求的地址
            if (needLogin)
            {
                if (user == null)
                {
                    //user为空，递交错误信息
                    render(response, CodeMsg.SESSION_ERROR);
                    return false;
                }
                key += "_" + user.getId();
            } else
            {
                //do nothing
            }
            AccessKey ak = AccessKey.withExpire(seconds);
            Integer count = redisService.get(ak, key, Integer.class);
            if (count == null)
            {
                redisService.set(ak, key, 1);
            } else if (count < maxCount)
            {
                redisService.incr(ak, key);
            } else
            {
                render(response, CodeMsg.ACCESS_LIMIT_REACHED);
                return false;
            }
        }
        return true;
    }

    private void render(HttpServletResponse response, CodeMsg cm) throws Exception
    {
        response.setContentType("application/json;charset=UTF-8");
        OutputStream out = response.getOutputStream();
        String str = JSON.toJSONString(Result.error(cm));
        out.write(str.getBytes("UTF-8"));
        out.flush();
        out.close();
    }

    private SpikeUser getUser(HttpServletRequest request, HttpServletResponse response)
    {
        String paramToken = request.getParameter(SpikeUserService.COOKI_NAME_TOKEN);
        String cookieToken = getCookieValue(request, SpikeUserService.COOKI_NAME_TOKEN);
        if (StringUtils.isEmpty(cookieToken) && StringUtils.isEmpty(paramToken))
        {
            return null;
        }
        String token = StringUtils.isEmpty(paramToken) ? cookieToken : paramToken;
        return userService.getByToken(response, token);
    }

    private String getCookieValue(HttpServletRequest request, String cookiName)
    {
        Cookie[] cookies = request.getCookies();
        if (cookies == null || cookies.length <= 0)
        {
            return null;
        }
        for (Cookie cookie : cookies)
        {
            if (cookie.getName().equals(cookiName))
            {
                return cookie.getValue();
            }
        }
        return null;
    }

}
