package com.alibaba.health.service.impl;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.health.dao.CheckItemDao;
import com.alibaba.health.entity.PageResult;
import com.alibaba.health.entity.QueryPageBean;
import com.alibaba.health.pojo.CheckItem;
import com.alibaba.health.service.CheckItemService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Author:ZZZ
 * @Date: 2021/1/5 17:28
 * @Version 1.0
 */
@Service(interfaceClass = CheckItemService.class)
public class CheckItemServiceImpl implements CheckItemService {

    @Autowired
    CheckItemDao checkItemDao;

    @Override
    public List<CheckItem> findAll() {
        return checkItemDao.findAll();
    }

    @Override
    public void add(CheckItem checkItem) {
        checkItemDao.add(checkItem);
    }

    @Override
    public PageResult<CheckItem> findPage(QueryPageBean queryPageBean) {
//        mybatis-plus插件
//        查询第几页
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());

//        查询条件不为空，给条件加上%%
        if (null != queryPageBean.getQueryString()) {
            queryPageBean.setQueryString("%" + queryPageBean.getQueryString() + "%");
        }
//        if(!StringUtils.isEmpty(queryPageBean.getQueryString())) {
//            queryPageBean.setQueryString("%" + queryPageBean.getQueryString() + "%");
//        }

//        插件拦截了sql语句查询出了total,和Result
        Page<CheckItem> page = checkItemDao.findPage(queryPageBean.getQueryString());
//
        PageResult<CheckItem> pageResult = new PageResult<>(page.getTotal(),page.getResult());

        return pageResult;
    }
}
