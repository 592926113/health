package com.itheima.health.jobs;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author:ZZZ
 * @Date: 2021/1/10 16:35
 * @Version 1.0
 */
@Component
public class abcjob {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Scheduled(initialDelay = 2000 ,fixedDelay = 2000)
    public void abc(){
        System.out.println(Thread.currentThread()+ ":" + sdf.format(new Date()));
    }
}
