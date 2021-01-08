package com.alibaba.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.health.entity.PageResult;
import com.alibaba.health.entity.QueryPageBean;
import com.alibaba.health.entity.Result;
import com.alibaba.health.pojo.CheckItem;
import com.alibaba.health.service.CheckItemService;

import com.github.pagehelper.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author:ZZZ
 * @Date: 2021/1/5 17:19
 * @Version 1.0
 */
@RequestMapping("/checkitem")
@RestController
public class CheckItemController {

    @Reference
    CheckItemService checkItemService;

    @RequestMapping("/findAll")
    public Result findAll() {
        return new Result(true, "查询成功", checkItemService.findAll());
    }

    @RequestMapping("/add")
    public Result add(@RequestBody CheckItem checkItem) {
        checkItemService.add(checkItem);
        return new Result(true, "添加成功");
    }

    @RequestMapping("/findPage")
    public Result queryPage(@RequestBody QueryPageBean queryPageBean){
        PageResult<CheckItem> pageResult = checkItemService.findPage(queryPageBean);
        return new Result(true,"查询成功",pageResult);
    }
}
