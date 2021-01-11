package com.alibaba.health.service;

import com.alibaba.health.entity.PageResult;
import com.alibaba.health.entity.QueryPageBean;
import com.alibaba.health.pojo.Setmeal;

import java.util.List;

/**
 * @Author:ZZZ
 * @Date: 2021/1/8 19:19
 * @Version 1.0
 */
public interface SetMealService {
    void add(Setmeal setmeal, Integer[] checkgroupIds);

    PageResult<Setmeal> findPage(QueryPageBean queryPageBean);

    Setmeal findById(int id);

    List<String> findAllImg();

    List<Setmeal> findAll();

    Setmeal findDetailById(int id);
}
