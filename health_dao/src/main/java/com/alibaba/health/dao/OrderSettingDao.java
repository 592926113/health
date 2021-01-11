package com.alibaba.health.dao;

import com.alibaba.health.pojo.OrderSetting;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author:ZZZ
 * @Date: 2021/1/9 16:25
 * @Version 1.0
 */
public interface OrderSettingDao {
    OrderSetting findByDate(Date date);

    void update(OrderSetting orderSetting);

    void add(OrderSetting orderSetting);

    List<Map<String, Integer>> getOrderSettingByMonth(Map month);
}
