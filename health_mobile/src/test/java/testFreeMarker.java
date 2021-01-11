import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author:ZZZ
 * @Date: 2021/1/11 9:54
 * @Version 1.0
 */
public class testFreeMarker {

    public static void main(String[] args) throws IOException, TemplateException {
        //1.创建配置类
        Configuration configuration = new Configuration(Configuration.getVersion());
//        2.设置文件文件的目录
        configuration.setDirectoryForTemplateLoading(new File("D:\\JavaEE_Relation\\health\\health_mobile\\src\\main\\webapp\\template"));
//        3.设置字符集
        configuration.setDefaultEncoding("utf-8");
//        4.加载模板
        Template template = configuration.getTemplate("freemarker入门.ftl");
//        5.创建数据模型
        Map map = new HashMap();
        map.put("name","zzz");
        map.put("message","你好");
//        6.创建writer
        Writer writer = new FileWriter(new File("D:\\JavaEE_Relation\\health\\health_mobile\\src\\main\\webapp\\模板生成的html\\test.html"));
//        7.模板输出
        template.process(map,writer);
//        8.关流
        writer.close();
    }
}
