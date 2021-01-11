package com.alibaba.health.job;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * @Author:ZZZ
 * @Date: 2021/1/9 20:34
 * @Version 1.0
 */
public class cleanImgJob {

    @Test
    public void cleanImgJob() throws IOException {
        ApplicationContext apt=new ClassPathXmlApplicationContext("classpath:spring-jobs.xml");
        System.in.read();
    }
}
