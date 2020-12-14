package com.kayleh.Service;

import com.kayleh.dao.GoodsDao;
import com.kayleh.domain.SpikeGoods;
import com.kayleh.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Kayleh
 * @Date: 2020/12/6 23:45
 */
@Service
public class GoodsService
{
    @Autowired
    GoodsDao goodsDao;

    public List<GoodsVo> listGoodsVo()
    {
        return goodsDao.listGoodsVo();
    }

    public GoodsVo getGoodsVoByGoodsId(long goodsId)
    {
        return goodsDao.getGoodsVoByGoodsId(goodsId);
    }

    public boolean reduceStock(GoodsVo goods)
    {
        SpikeGoods g = new SpikeGoods();
        g.setGoodsId(goods.getId());
        int ret = goodsDao.reduceStock(g);
        return ret > 0;
    }

    public void resetStock(List<GoodsVo> goodsList)
    {
        for (GoodsVo goods : goodsList)
        {
            SpikeGoods g = new SpikeGoods();
            g.setGoodsId(goods.getId());
            g.setStockCount(goods.getStockCount());
            goodsDao.resetStock(g);
        }
    }

}
