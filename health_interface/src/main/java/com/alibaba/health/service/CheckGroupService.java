package com.alibaba.health.service;

import com.alibaba.health.entity.PageResult;
import com.alibaba.health.entity.QueryPageBean;
import com.alibaba.health.pojo.CheckGroup;
import com.alibaba.health.pojo.CheckItem;

import java.util.List;

/**
 * @Author:ZZZ
 * @Date: 2021/1/6 18:23
 * @Version 1.0
 */
public interface CheckGroupService {

    void add(CheckGroup checkGroup, Integer[] checkItemIds);

    PageResult<CheckItem> findPage(QueryPageBean queryPageBean);

    CheckGroup findByid(Integer id);

    List<Integer> findCheckItemIdsByCheckGroupId(int id);

    void update(CheckGroup checkGroup, int[] checkitemIds);
}
