package com.kayleh.Service;

import com.kayleh.dao.OrderDao;
import com.kayleh.domain.OrderInfo;
import com.kayleh.domain.SpikeOrder;
import com.kayleh.domain.SpikeUser;
import com.kayleh.redis.OrderKey;
import com.kayleh.redis.RedisService;
import com.kayleh.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Author: Kayleh
 * @Date: 2020/12/11 17:11
 */
@Service
public class OrderService
{
    @Autowired
    OrderDao orderDao;
    @Autowired
    RedisService redisService;

    public SpikeOrder getSpikeOrderByUserIdGoodsId(Long userId, long goodsId)
    {
//        return orderDao.getSpikeOrderBuUserIdGoodsId(userId, goodsId);
        return redisService.get(OrderKey.getSpikeOrderByUidGid, "" + userId + "_" + goodsId, SpikeOrder.class);
    }

    public OrderInfo getOrderById(long orderId)
    {
        return orderDao.getOrderById(orderId);
    }

    public OrderInfo createOrder(SpikeUser user, GoodsVo goods)
    {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setCreateDate(new Date());
        orderInfo.setDeliverAddrId(0L);
        orderInfo.setGoodsCount(1);
        orderInfo.setGoodsId(goods.getId());
        orderInfo.setGoodsName(goods.getGoodsName());
        orderInfo.setGoodsPrice(goods.getSpikePrice());
        orderInfo.setOrderChannel(1);
        orderInfo.setStatus(0);
        orderInfo.setUserId(user.getId());
        long orderId = orderDao.insert(orderInfo);
        SpikeOrder spikeOrder = new SpikeOrder();
        spikeOrder.setGoodsId(goods.getId());
        spikeOrder.setOrderId(orderInfo.getId());
        spikeOrder.setUserId(user.getId());
        orderDao.insertSpikeOrder(spikeOrder);

        redisService.set(OrderKey.getSpikeOrderByUidGid, "" + user.getId() + "_" + goods.getId(), spikeOrder);
        return orderInfo;
    }

    public void deleteOrders()
    {
        orderDao.deleteOrders();
        orderDao.deleteMiaoshaOrders();
    }
}
