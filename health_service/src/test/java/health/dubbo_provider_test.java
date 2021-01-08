package health;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * @Author:ZZZ
 * @Date: 2021/1/5 17:37
 * @Version 1.0
 */
public class dubbo_provider_test {

    @Test
    public void provider() throws IOException {
        ApplicationContext apt=new ClassPathXmlApplicationContext("classpath:applicationContext-service.xml");
        System.in.read();
    }
}
