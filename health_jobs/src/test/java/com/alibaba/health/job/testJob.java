package com.alibaba.health.job;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * @Author:ZZZ
 * @Date: 2021/1/10 16:34
 * @Version 1.0
 */
public class testJob {

    @Test
    public void testJob() throws IOException {
        new ClassPathXmlApplicationContext("classpath:spring-jobs.xml");
        System.in.read();
    }

    @Test
    public void testString(){
        String s = String.format("hello_%d_world", 1);
        System.out.println(s);
    }
}
