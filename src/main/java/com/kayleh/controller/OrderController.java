package com.kayleh.controller;

import com.kayleh.Service.GoodsService;
import com.kayleh.Service.OrderService;
import com.kayleh.Service.SpikeUserService;
import com.kayleh.domain.OrderInfo;
import com.kayleh.domain.SpikeUser;
import com.kayleh.redis.RedisService;
import com.kayleh.result.CodeMsg;
import com.kayleh.result.Result;
import com.kayleh.vo.GoodsVo;
import com.kayleh.vo.OrderDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: Kayleh
 * @Date: 2020/12/13 11:53
 */
@Controller
@RequestMapping("/order")
public class OrderController
{
    @Autowired
    SpikeUserService spikeUserService;
    @Autowired
    RedisService redisService;
    @Autowired
    OrderService orderService;
    @Autowired
    GoodsService goodsService;

    @RequestMapping("/detail")
    @ResponseBody
    public Result<OrderDetailVo> info(Model model, SpikeUser user, @RequestParam("orderId") long orderId)
    {
        if (user == null)
            return Result.error(CodeMsg.SESSION_ERROR);
        OrderInfo order = orderService.getOrderById(orderId);
        if (order == null)
            return Result.error(CodeMsg.ORDER_NOT_EXIST);
        Long goodsId = order.getGoodsId();
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        OrderDetailVo orderDetailVo = new OrderDetailVo();
        orderDetailVo.setGoods(goods);
        orderDetailVo.setOrder(order);
        return Result.success(orderDetailVo);

    }
}
