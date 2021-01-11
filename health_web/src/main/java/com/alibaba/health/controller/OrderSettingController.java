package com.alibaba.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.health.entity.Result;
import com.alibaba.health.pojo.OrderSetting;
import com.alibaba.health.service.OrderSettingService;
import com.alibaba.health.utils.POIUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author:ZZZ
 * @Date: 2021/1/9 15:57
 * @Version 1.0
 */
@RestController
@RequestMapping("/ordersetting")
public class OrderSettingController {

    @Reference
    OrderSettingService orderSettingService;
    @RequestMapping("/upload")
    public Result upload(MultipartFile excelFile) throws IOException, ParseException {
//        poUtils读取excel,list是整个excel ， String[]是每一行
        List<String[]> excelList = POIUtils.readExcel(excelFile);
//        新建List<OrderSetting>处理excel
        List<OrderSetting> orderSettingList = new ArrayList<>();
//        遍历excel  ,string数组是每一行的值
//        日期格式
        SimpleDateFormat sdf = new SimpleDateFormat(POIUtils.DATE_FORMAT);
        Date date = null;
        Integer number = null;
        OrderSetting orderSetting=null;
        for (String[] string : excelList) {
            date = sdf.parse(string[0]);//excel里的日期是字符串，pojo类里的日期是date，数据库的也是date类型，所以要日期格式转换转日期
            number = Integer.parseInt(string[1]);//可预约数量
            orderSetting = new OrderSetting();
            orderSetting.setOrderDate(date);
            orderSetting.setNumber(number);
            orderSettingList.add(orderSetting);//添加到orderSettingList
        }
//        调用业务方法
        orderSettingService.upload(orderSettingList);
        return new Result(true, "批量添加成功");
    }

    @RequestMapping("/getOrderSettingByMonth")
    public Result getOrderSettingByMonth(String month){
//          map:{ date: 1, number: 120, reservations: 1 },
        List<Map<String,Integer>> mapList=orderSettingService.getOrderSettingByMonth(month);
        return new Result(true,"按月查询成功",mapList);
    }


}
