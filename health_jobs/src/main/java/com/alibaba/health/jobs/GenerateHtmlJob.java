package com.alibaba.health.jobs;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.health.pojo.Setmeal;
import com.alibaba.health.service.SetMealService;
import com.alibaba.health.utils.QiniuUtils;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Author:ZZZ
 * @Date: 2021/1/11 10:44
 * @Version 1.0
 */

//生成套餐列表的静态页面和套餐详情页面的静态页面
@Component
public class GenerateHtmlJob {

//    日志
    Logger logger = LoggerFactory.getLogger(GenerateHtmlJob.class);
//    spring扫类创建对象执行初始化方法
    @PostConstruct
    public void init() throws IOException {
//        配置文件已经配置了configuration配置类的版本
//        模板目录位置，配置类读取的字符集
        configuration.setDirectoryForTemplateLoading(new File("D:\\JavaEE_Relation\\health\\health_jobs\\src\\main\\resources\\ftl"));
        configuration.setDefaultEncoding("utf-8");
    }

    /** jedis连接池 */
    @Autowired
    JedisPool jedisPool;

    /** 订阅套餐服务 */
    @Reference
    SetMealService setMealService;

    /** 注入freemarker主配置类 */
    @Autowired
    Configuration configuration;

    /** 生成静态页面存放的目录 */
//    配置文件中的spring读取配置文件
    @Value("${out_put_path}")
    String out_put_path;//D:/JavaEE_Relation/health/health_mobile/src/main/webapp/pages/


    /**
     * 任务执行的方法
     */
    @Scheduled(initialDelay = 3000 ,fixedDelay = 1800000)
    public void doGenerateHtml() throws IOException, TemplateException {
// 获取redis连接对象
        Jedis resource = jedisPool.getResource();

        // redis中set集合的key , redis里的集合的名字是static:setmeal:html 集合存储很多的成员
        String key = "static:setmeal:html";

        // 获取集合中所有的套餐id数据
        Set<String> zrangeSet = resource.zrange(key, 0, -1);

        // 有数据(任务)则需要处理   存储的内容是  套餐id|操作类型|时间戳
        if (zrangeSet!=null) {
            for (String zValue : zrangeSet) {
//                split存储的内容 , 按照|分割， 正则表达式|里是或者的意思， 所以加上\\转义
                String[] strings = zValue.split("\\|");
                // 获取套餐的id
                Integer id = Integer.valueOf(strings[0]);
                // 获取操作类型 0删除页面（前端的删除页面触发） 1建立页面（前端的查看页面，编辑页面，增加页面触发）
                Integer operation = Integer.valueOf(strings[1]);
                // 需要生成套餐详情页面的操作
                if (operation==1) {
                // 查询套餐详情
                    Setmeal setmeal = setMealService.findDetailById(id);
                    // 设置图片的全路径
                    setmeal.setImg(QiniuUtils.DOMAIN+setmeal.getImg());
                // 生成套餐详情静态页面
                    generateSetmealDetailHtml(setmeal);
                }
                // 删除套餐静态页面
                if (operation==0) {
                    removeSetmealDetailFile(id);
                }
                // 每处理完一个，删除set集合中对应的数据
                resource.zrem(key,zValue);
                // 套餐列表的数据也发生了变化，要重新生成静态页面
                generateSetmealListHtml();
            }
        }
    }
        /**
         * 删除详情页（被删除的套餐）静态页面
         * @param id 套餐id
         */
        public void removeSetmealDetailFile (Integer id){
//                                  父路径   ， 子路径
          File file = new File( out_put_path,String.format("setmeal_%d.html", id));
            if (file.exists()) {
                file.delete();
            }
        }

        /**
         * 生成套餐详情页面
         * @param setmeal
         */
        public void generateSetmealDetailHtml(Setmeal setmeal) throws IOException, TemplateException {
            // 构建数据模型
            Map map = new HashMap();
//            这里的setmeal需要看模板里的插值表达式的变量的名字${setmeal.id}"
            map.put("setmeal",setmeal);
            // 模板名
            String templateName="mobile_setmeal_detail.ftl";
            // 生成文件的全路径
            String fileName=out_put_path+String.format("setmeal_%d.html",setmeal.getId());
            generateHtml(templateName,map,fileName);
        }

        /**
         * 生成套餐列表页面
         */
        public void generateSetmealListHtml() throws IOException, TemplateException {

            List<Setmeal> setmealList = setMealService.findAll();
            for (Setmeal setmeal : setmealList) {
//                图片地址
                setmeal.setImg(QiniuUtils.DOMAIN+setmeal.getImg());
            }
            // 构建数据模型
            Map map = new HashMap();
//            这里的setmealList需要看模板里的插值表达式的变量的名字//<#list setmealList as setmeal>
            map.put("setmealList",setmealList);
            // 模板名
            String templateName="mobile_setmeal.ftl";
            // 生成文件的全路径
            String fileName=out_put_path+"mobile_setmeal.html";
            generateHtml(templateName,map,fileName);
        }

        /**
         * 生成文件
         * @param templateName 模板名
         * @param dataMap 要填充的数据
         * @param filename 生成的文件名 全路径
         */
        public void generateHtml(String templateName,Map dataMap ,String filename) throws IOException, TemplateException {
//            文件路径
            File file=new File(filename);
            Writer out=new FileWriter(file);
            BufferedWriter bf=new BufferedWriter(out);
//            template模板
            Template template = configuration.getTemplate(templateName);
            // 生成文件
            template.process(dataMap,bf);
//            关流
            bf.close();
            out.close();
        }
}
