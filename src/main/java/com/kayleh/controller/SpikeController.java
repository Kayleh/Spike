package com.kayleh.controller;

import com.kayleh.Service.GoodsService;
import com.kayleh.Service.OrderService;
import com.kayleh.Service.SpikeSerivce;
import com.kayleh.access.AccessLimit;
import com.kayleh.domain.SpikeOrder;
import com.kayleh.domain.SpikeUser;
import com.kayleh.rabbitmq.MQSender;
import com.kayleh.rabbitmq.SpikeMessage;
import com.kayleh.redis.GoodsKey;
import com.kayleh.redis.RedisService;
import com.kayleh.result.CodeMsg;
import com.kayleh.result.Result;
import com.kayleh.vo.GoodsVo;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;

/**
 * @Author: Kayleh
 * @Date: 2020/12/12 20:19
 */
@Controller
@RequestMapping("/spike")
public class SpikeController implements InitializingBean
{

    @Autowired
    GoodsService goodsService;
    @Autowired
    RedisService redisService;
    @Autowired
    OrderService orderService;
    @Autowired
    SpikeSerivce spikeSerivce;
    @Autowired
    MQSender mqSender;

    private HashMap<Long, Boolean> localOverMap = new HashMap<Long, Boolean>();

    //系统初始化 预加载数量
    @Override
    public void afterPropertiesSet() throws Exception
    {
        List<GoodsVo> goodsVoList = goodsService.listGoodsVo();
        if (goodsVoList == null) return;
        for (GoodsVo goods : goodsVoList)
        {
            redisService.set(GoodsKey.getSpikeGoodsStock, "" + goods.getId(), goods.getStockCount());
            localOverMap.put(goods.getId(), false);
        }
    }

    @RequestMapping(value = "/do_spike", method = RequestMethod.POST)
    @ResponseBody
    public Result<Integer> spike(Model model, SpikeUser user, @RequestParam("goodsId") long goodsId)
    {
        model.addAttribute("user", user);
        if (user == null) return Result.error(CodeMsg.SESSION_ERROR);
        //内存标记,减少redis访问
        boolean over = localOverMap.get(goodsId);
        if (over)
        {
            return Result.error(CodeMsg.SPIKE_OVER);
        }
        //预减库存
        Long stock = redisService.decr(GoodsKey.getSpikeGoodsStock, "" + goodsId);
        if (stock < 0)
        {
            localOverMap.put(goodsId, true);
            return Result.error(CodeMsg.SPIKE_OVER);
        }
        //判断是否已经秒杀到了
        SpikeOrder order = orderService.getSpikeOrderByUserIdGoodsId(user.getId(), goodsId);
        if (order != null)
        {
            return Result.error(CodeMsg.REPEATE_SPIKE);
        }
        //入队
        SpikeMessage sm = new SpikeMessage();
        sm.setUser(user);
        sm.setGoodsId(goodsId);
        mqSender.sendSpikeMessage(sm);
        return Result.success(0);//排队中
    }

    /**
     * orderId：成功
     * -1：秒杀失败
     * 0： 排队中
     */
    @RequestMapping(value = "/result", method = RequestMethod.GET)
    public Result<Long> SpikeResult(Model model, SpikeUser user, @RequestParam("goodsId") long goodsId)
    {
        model.addAttribute("user", user);
        if (user == null)
        {
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        long result = spikeSerivce.getSpikeResult(user.getId(), goodsId);
        return Result.success(result);
    }

    /**
     * 获取path 隐藏秒杀接口
     * 自定义注解 限制在规定时间内 请求次数
     * seconds 代表秒数
     * maxCount 代表最大请求数
     * needLogin 是否需要登录
     */
    @AccessLimit(seconds = 5, maxCount = 5, needLogin = true)
    @RequestMapping(value = "/path", method = RequestMethod.GET)
    @ResponseBody
    public Result<String> getSpikePath(HttpServletRequest request, SpikeUser user, @RequestParam("goodsId") long goodsId,
                                       @RequestParam(value = "verifyCode", defaultValue = "0") int verifyCode)
    {
        if (user == null)
        {
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        //查看验证码是否正确
        boolean check = spikeSerivce.checkVerifyCode(user, goodsId, verifyCode);
        if (!check)
            return Result.error(CodeMsg.REQUEST_ILLEGAL);
        //获取path 一个user对应一个path  用于请求秒杀接口时验证
        String path = spikeSerivce.createSpikePath(user, goodsId);
        return Result.success(path);
    }

    /**
     * 获取验证码
     */
    @RequestMapping(value = "/verifyCode", method = RequestMethod.GET)
    @ResponseBody
    public Result<String> getSpikeVerifyCode(HttpServletRequest request, SpikeUser user, @RequestParam("goodsId") long goodsId, HttpServletResponse response)
    {
        if (user == null)
        {
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        try
        {
            BufferedImage image = spikeSerivce.createVerifyCode(user, goodsId);
            OutputStream out = response.getOutputStream();
            ImageIO.write(image, "JPEG", out);
            out.flush();
            out.close();
            return null;
        } catch (IOException e)
        {
            e.printStackTrace();
            return Result.error(CodeMsg.SPIKE_FAIL);
        }
    }
}
