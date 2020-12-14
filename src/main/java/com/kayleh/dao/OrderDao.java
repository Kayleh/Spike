package com.kayleh.dao;

import com.kayleh.domain.OrderInfo;
import com.kayleh.domain.SpikeOrder;
import org.apache.ibatis.annotations.*;

/**
 * @Author: Kayleh
 * @Date: 2020/12/8 23:45
 */
@Mapper
public interface OrderDao
{
    @Select("select * from  spike_order where user_id=#{userId} and goods_id=#{goodsId}")
    public SpikeOrder getSpikeOrderBuUserIdGoodsId(@Param("userId") long userId, @Param("goodsId") long goodsId);

    @Insert("insert into order_info(user_id, goods_id, goods_name, goods_count, goods_price, order_channel, status, create_date)values("
            + "#{userId}, #{goodsId}, #{goodsName}, #{goodsCount}, #{goodsPrice}, #{orderChannel},#{status},#{createDate} )")
    @SelectKey(keyColumn = "id", keyProperty = "id", resultType = long.class, before = false, statement = "select last_insert_id()")
    public long insert(OrderInfo orderInfo);

    @Insert("insert into spike_order (user_id, goods_id, order_id)values(#{userId}, #{goodsId}, #{orderId})")
    public int insertSpikeOrder(SpikeOrder spikeOrder);

    @Select("select * from order_info where id = #{orderId}")
    public OrderInfo getOrderById(@Param("orderId") long orderId);


    @Delete("delete from order_info")
    public void deleteOrders();

    @Delete("delete from spike_order")
    public void deleteMiaoshaOrders();
}
