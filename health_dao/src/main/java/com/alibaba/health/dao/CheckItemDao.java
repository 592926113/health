package com.alibaba.health.dao;

import com.alibaba.health.entity.PageResult;
import com.alibaba.health.entity.QueryPageBean;
import com.alibaba.health.pojo.CheckItem;
import com.github.pagehelper.Page;

import java.util.List;

/**
 * @Author:ZZZ
 * @Date: 2021/1/5 17:30
 * @Version 1.0
 */
public interface CheckItemDao {
    List<CheckItem>findAll();

    void add(CheckItem checkItem);

    Page<CheckItem> findPage(String queryPage);
}
