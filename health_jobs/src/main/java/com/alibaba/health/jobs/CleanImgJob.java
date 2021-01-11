package com.alibaba.health.jobs;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.health.service.SetMealService;
import com.alibaba.health.utils.QiniuUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


import java.util.List;

/**
 * @Author:ZZZ
 * @Date: 2021/1/9 20:00
 * @Version 1.0
 */
//图片清理计划
//@Component("cleanImgJob")
public class CleanImgJob {

//    订阅服务，用来查询数据库的图片
    @Reference
    SetMealService setMealService;
//类调用静态方法要在方法里写
@Scheduled(initialDelay=1000,fixedDelay = 1000*60*60*24)
    public void cleanImg(){
    System.out.println("777777777");
        //    七牛的图片
        List<String> imgQiniu = QiniuUtils.listFile();
//        数据库的图片
        List<String> imgInDb=setMealService.findAllImg();
//        七牛-数据库
        imgQiniu.removeAll(imgInDb);
//        剩下的垃圾图片
        String[] strings = imgQiniu.toArray(new String[]{});
//七牛utils的方法
        QiniuUtils.removeFiles(strings);
    }

}
