package com.kayleh.controller;

import com.kayleh.Service.GoodsService;
import com.kayleh.Service.SpikeUserService;
import com.kayleh.domain.SpikeUser;
import com.kayleh.redis.GoodsKey;
import com.kayleh.redis.RedisService;
import com.kayleh.result.Result;
import com.kayleh.vo.GoodsDetailVo;
import com.kayleh.vo.GoodsVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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
    @ResponseBody
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

    @RequestMapping(value = "/detail/{goodsId}")
    @ResponseBody
    public Result<GoodsDetailVo> detail(HttpServletRequest request, HttpServletResponse response,
                                        Model model, SpikeUser user, @PathVariable("goodsId") long goodsId)
    {
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        long startAt = goods.getStartDate().getTime();
        long endAt = goods.getEndDate().getTime();
        long now = System.currentTimeMillis();
        int spikeStatus = 0;
        int remainSeconds = 0;
        if (now < startAt)
        {
            //秒杀还没开始，倒计时
            spikeStatus = 0;
            remainSeconds = (int) ((startAt - now) / 1000);
        } else if (now > endAt)
        {
            //秒杀已经结束
            spikeStatus = 2;
            remainSeconds = -1;
        } else
        {
            //秒杀进行中
            spikeStatus = 1;
            remainSeconds = 0;
        }
        GoodsDetailVo vo = new GoodsDetailVo();
        vo.setGoods(goods);
        vo.setUser(user);
        vo.setRemainSeconds(remainSeconds);
        vo.setSpikeStatus(spikeStatus);
        return Result.success(vo);
    }

    @RequestMapping(value = "/to_detail/{goodsId}", produces = "text/html")
    @ResponseBody
    public String detail2(HttpServletRequest request, HttpServletResponse response,
                          Model model, SpikeUser user, @PathVariable("goodsId") long goodsId)
    {
        model.addAttribute("user", user);

        //取缓存
        String html = redisService.get(GoodsKey.getGoodsList, "", String.class);
        if (!StringUtils.isEmpty(html))
        {
            return html;
        }
        //手动渲染
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        model.addAttribute("goods", goods);

        long startAt = goods.getStartDate().getTime();
        long endAt = goods.getEndDate().getTime();
        long now = System.currentTimeMillis();
        int spikeStatus = 0;
        int remainSeconds = 0;
        if (now < startAt)
        {
            //秒杀还没开始，倒计时
            spikeStatus = 0;
            remainSeconds = (int) ((startAt - now) / 1000);
        } else if (now > endAt)
        {
            //秒杀已经结束
            spikeStatus = 2;
            remainSeconds = -1;
        } else
        {
            //秒杀进行中
            spikeStatus = 1;
            remainSeconds = 0;
        }
        model.addAttribute("SpikeStatus", spikeStatus);
        model.addAttribute("remainSeconds", remainSeconds);

        SpringWebContext webContext = new SpringWebContext(request, response, request.getServletContext(), request.getLocale(),
                model.asMap(), applicationContext);
        if (!StringUtils.isEmpty(html))
        {
            redisService.set(GoodsKey.getGoodsDetail, "" + goodsId, html);
        }
        return html;

    }
}
