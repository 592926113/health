package com.alibaba.health.dao;

import com.alibaba.health.pojo.Setmeal;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author:ZZZ
 * @Date: 2021/1/8 19:23
 * @Version 1.0
 */
public interface SetMealDao {
    void add(Setmeal setmeal);

    void add_checkgroupIds(@Param("setmealId") Integer setmealId, @Param("checkgroupId") Integer checkgroupId);

    Page<Setmeal> findPage(String queryString);

    Setmeal findById(int id);

    List<String> findAllImg();

    List<Setmeal> findAll();

    Setmeal findDetailById(int id);
}
