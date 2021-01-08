package com.alibaba.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.health.entity.PageResult;
import com.alibaba.health.entity.QueryPageBean;
import com.alibaba.health.entity.Result;
import com.alibaba.health.pojo.CheckGroup;
import com.alibaba.health.pojo.CheckItem;
import com.alibaba.health.service.CheckGroupService;
;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.ResultSet;
import java.util.List;

/**
 * @Author:ZZZ
 * @Date: 2021/1/6 18:12
 * @Version 1.0
 */
@RestController
@RequestMapping("checkgroup")
public class CheckGroupController {

    @Reference
    CheckGroupService checkGroupService;

    @RequestMapping("/add")
    public Result add(@RequestBody CheckGroup checkGroup, @RequestParam("checkitemIds") Integer[] checkItemIds) {
//        提交检查组的表格和检查项的id数组
        checkGroupService.add(checkGroup, checkItemIds);
        return new Result(true, "添加检查组成功");
    }

    /**
     * 检查组分页查询
     *
     * @param queryPageBean
     * @return
     */
    @RequestMapping("/findPage")
    public Result queryPage(@RequestBody QueryPageBean queryPageBean) {
        PageResult<CheckItem> pageResult = checkGroupService.findPage(queryPageBean);
        return new Result(true, "查询成功", pageResult);
    }

    /**
     * 根据id查询检查组
     *
     * @param id
     * @return
     */
    @RequestMapping("/findById")
    public Result findById(Integer id) {
        CheckGroup checkGroup = checkGroupService.findByid(id);
        return new Result(true, "查询成功", checkGroup);
    }

    /**
     * 根据检查组id查该检查组下的所有的检查项id
     *
     * @param id
     * @return
     */
    @RequestMapping("/findCheckItemIdsByCheckGroupId")
    public Result findCheckItemIdsByCheckGroupId(int id) {
        List<Integer> ids = checkGroupService.findCheckItemIdsByCheckGroupId(id);
        return new Result(true,"查询成功",ids);
    }

    @RequestMapping("/update")
    public Result update(@RequestBody CheckGroup checkGroup  ,@RequestParam("checkitemIds") int[] checkitemIds){
        checkGroupService.update(checkGroup,checkitemIds);
        return new Result(true,"查询成功");
    }

}
