package com.alibaba.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.health.entity.PageResult;
import com.alibaba.health.entity.QueryPageBean;
import com.alibaba.health.entity.Result;
import com.alibaba.health.pojo.Setmeal;
import com.alibaba.health.service.SetMealService;
import com.alibaba.health.utils.QiniuUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @Author:ZZZ
 * @Date: 2021/1/8 13:41
 * @Version 1.0
 */
@RestController
@RequestMapping("/setmeal")
public class SetMealController {

    @Reference
    SetMealService setMealService;

    /**
     * 上传到七牛
     * @param imgFile
     * @return
     */
    @RequestMapping("/upload")
//    imgFile和前端传的name要一致
    public Result upload(MultipartFile imgFile) throws IOException {

//        文件名+后缀
        String originalFilename = imgFile.getOriginalFilename();
//      后缀
        String hz=originalFilename.substring(originalFilename.lastIndexOf("."));
//唯一标识，uuid是jdk的工具类
        UUID uuid = UUID.randomUUID();
//        唯一字符串+后缀
        String filename=uuid.toString()+hz;//happy.jpg
//        上传filename      文件的字节                 和文件名
        QiniuUtils.uploadViaByte(imgFile.getBytes(),filename);
//        图片名和七牛的url（出去文件地址）
        Map imgMap =new HashMap();
        imgMap.put("domain",QiniuUtils.DOMAIN);//url
        imgMap.put("imgName",filename);//happy.jpg的唯一名 ， 同一个图片七牛上的图片名和数据库的图片名是一致的
        //src=domain+imaName
        return new Result(true,"文件上传成功",imgMap);
    }

    /**
     * 套餐的添加（前端会把图片的唯一名会添加到数据库里）
     * @param setmeal
     * @param checkgroupIds
     * @return
     */
    @RequestMapping("/add")
    public Result add(@RequestBody Setmeal setmeal , Integer [] checkgroupIds){
        setMealService.add(setmeal,checkgroupIds);
        return new Result(true,"添加成功");
    }

    @RequestMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean){
       PageResult<Setmeal> pageResult= setMealService.findPage(queryPageBean);
       return new Result(true,"分页查询套餐成功",pageResult);
    }

    @RequestMapping("/findById")
    public Result findById(int id){
        Setmeal setmeal = setMealService.findById(id);
        Map<String, Object> map = new HashMap();
        map.put("setmeal",setmeal);
        map.put("domain",QiniuUtils.DOMAIN);
        return new Result(true,"查询成功",setmeal);
    }




}
