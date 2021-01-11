package com.alibaba.health.service;

import com.alibaba.health.exception.Myexception;
import com.alibaba.health.pojo.OrderSetting;

import java.util.List;
import java.util.Map;

/**
 * @Author:ZZZ
 * @Date: 2021/1/9 16:24
 * @Version 1.0
 */

//因为要拦截到异常所以接口要声明
public interface OrderSettingService {
    void upload(List<OrderSetting> orderSetting) throws Myexception;

    List<Map<String, Integer>> getOrderSettingByMonth(String month);
}
