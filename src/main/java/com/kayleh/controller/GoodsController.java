package com.kayleh.controller;

import com.kayleh.Service.GoodsService;
import com.kayleh.Service.SpikeUserService;
import com.kayleh.domain.SpikeUser;
import com.kayleh.redis.GoodsKey;
import com.kayleh.redis.RedisService;
import com.kayleh.vo.GoodsVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.spring4.context.SpringWebContext;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author: Kayleh
 * @Date: 2020/12/6 16:57
 */
@Controller
@RequestMapping("/goods")
public class GoodsController
{
    @Autowired
    SpikeUserService userService;

    @Autowired
    RedisService redisService;

    @Autowired
    GoodsService goodsService;

    @Autowired
    ThymeleafViewResolver thymeleafViewResolver;

    @Autowired
    ApplicationContext applicationContext;


    @RequestMapping(value = "/to_list", produces = "text/html")
    public String list(HttpServletRequest request, HttpServletResponse response, Model model, SpikeUser user)
    {
        model.addAttribute("user", user);
        //取缓存
        String html = redisService.get(GoodsKey.getGoodsList, "", String.class);
        if (!StringUtils.isEmpty(html))
        {
            return html;
        }
        List<GoodsVo> goodsList = goodsService.listGoodsVo();
        model.addAttribute("goodsList", goodsList);
        SpringWebContext ctx = new SpringWebContext
                (request, response, request.getServletContext(), request.getLocale(), model.asMap(), applicationContext);
        //手动渲染
        html = thymeleafViewResolver.getTemplateEngine().process("goods_list", ctx);
        if (!StringUtils.isEmpty(html))
        {
            redisService.set(GoodsKey.getGoodsList, "", html);
        }
        return html;
    }
}
