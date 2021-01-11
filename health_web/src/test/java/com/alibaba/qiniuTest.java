package com.alibaba;

import com.alibaba.health.utils.QiniuUtils;
import org.junit.Test;

/**
 * @Author:ZZZ
 * @Date: 2021/1/8 20:38
 * @Version 1.0
 */
public class qiniuTest {

    @Test
    public void testQiniu(){
        QiniuUtils.uploadFile(QiniuUtils.DOMAIN,"");
    }

}
