package com.alibaba.health.service;

import com.alibaba.health.entity.PageResult;
import com.alibaba.health.entity.QueryPageBean;
import com.alibaba.health.pojo.CheckItem;

import java.util.List;

/**
 * @Author:ZZZ
 * @Date: 2021/1/5 17:22
 * @Version 1.0
 */
public interface CheckItemService {
    List<CheckItem> findAll();

    void add(CheckItem checkItem);

    PageResult<CheckItem> findPage(QueryPageBean queryPageBean);
}
