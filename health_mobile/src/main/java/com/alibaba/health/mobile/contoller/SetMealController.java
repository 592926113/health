package com.alibaba.health.mobile.contoller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.health.entity.Result;
import com.alibaba.health.pojo.Setmeal;
import com.alibaba.health.service.SetMealService;
import com.alibaba.health.utils.QiniuUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author:ZZZ
 * @Date: 2021/1/10 17:59
 * @Version 1.0
 */

@RestController
@RequestMapping("/setmeal")
public class SetMealController {

    @Reference
    SetMealService setMealService;

    @RequestMapping("/getSetmeal")
    public Result getSetmeal(){
        List<Setmeal> list = setMealService.findAll();
        return new Result(true,"查询成功",list);
    }

    @RequestMapping("/findDetailById")
    public Result findDetailById(int id){
        Setmeal setmeal = setMealService.findDetailById(id);
        setmeal.setImg(QiniuUtils.DOMAIN+setmeal.getImg());
        return new Result(true,"按id查询成功",setmeal);
    }
}
