package com.alibaba.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.health.dao.OrderSettingDao;
import com.alibaba.health.exception.Myexception;
import com.alibaba.health.pojo.OrderSetting;
import com.alibaba.health.service.OrderSettingService;
import com.alibaba.health.utils.POIUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author:ZZZ
 * @Date: 2021/1/9 16:24
 * @Version 1.0
 */
//因为添加了事务所以要指定接口类

@Service(interfaceClass = OrderSettingService.class)
public class OrderSettingServiceImpl implements OrderSettingService {

    @Autowired
    OrderSettingDao orderSettingDao;

    @Transactional
    @Override
    public void upload(List<OrderSetting> orderSettingList)throws Myexception {

//        遍历excel成每一行
        for (OrderSetting orderSetting : orderSettingList) {
            Date date=orderSetting.getOrderDate();
            // 先查询数据库是否有该日期值
           OrderSetting orderSetting_inDb =  orderSettingDao.findByDate(orderSetting.getOrderDate());
            if (orderSetting_inDb!=null) {
//                数据库有该日期值
//                1.判断数据库已经预约的数量（getReservations）>前端设置的最大数量（getNumber） 则预约人数溢出
                if (orderSetting_inDb.getReservations()>orderSetting.getNumber()) {
                    throw new Myexception(date+"号的设置的预约人数"+"不能小于数据库的已经预约人数");
                }
                else {
//                    执行更新
                    orderSettingDao.update(orderSetting);
                }
            }
            else {
//                数据库没有该日期,直接添加
                orderSettingDao.add(orderSetting);
            }
        }

    }

    @Override
    public List<Map<String, Integer>> getOrderSettingByMonth(String month) {
//month：2021-01
//        开始
        String startDate=month+"-1";
        String endDate=month+"-31";
        Map mapDate = new HashMap();
        mapDate.put("startDate",startDate);
        mapDate.put("endDate",endDate);
      //  System.out.println(startDate);
        //      map:{ date: 1, number: 120, reservations: 1 },
    return orderSettingDao.getOrderSettingByMonth(mapDate);
    }
}
