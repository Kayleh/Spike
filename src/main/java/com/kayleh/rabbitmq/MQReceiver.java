package com.kayleh.rabbitmq;

import com.kayleh.Service.GoodsService;
import com.kayleh.Service.OrderService;
import com.kayleh.Service.SpikeSerivce;
import com.kayleh.domain.SpikeOrder;
import com.kayleh.domain.SpikeUser;
import com.kayleh.redis.RedisService;
import com.kayleh.vo.GoodsVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: Kayleh
 * @Date: 2020/12/11 16:55
 */
@Service
public class MQReceiver
{
    @Autowired
    GoodsService goodsService;
    @Autowired
    OrderService orderService;
    @Autowired
    SpikeSerivce spikeSerivce;

    private static Logger log = LoggerFactory.getLogger(MQReceiver.class);

    @RabbitListener(queues = MQConfig.SPIKE_QUEUE)
    public void receiver(String message)
    {
        log.info("receive message:" + message);
        SpikeMessage sm = RedisService.stringToBean(message, SpikeMessage.class);
        SpikeUser user = sm.getUser();
        long goodsid = sm.getGoodsId();

        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsid);
        int stock = goods.getStockCount();
        if (stock <= 0) return;
        //判断是否已经秒杀到了
        SpikeOrder order = orderService.getSpikeOrderByUserIdGoodsId(user.getId(), goodsid);
        if (order != null) return;
        //减库存，下订单，写入秒杀订单
        spikeSerivce.spike(user, goods);
    }
}
