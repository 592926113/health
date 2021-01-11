package com.alibaba.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.health.dao.SetMealDao;
import com.alibaba.health.entity.PageResult;
import com.alibaba.health.entity.QueryPageBean;
import com.alibaba.health.pojo.Setmeal;
import com.alibaba.health.service.SetMealService;
import com.alibaba.health.utils.QiniuUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Author:ZZZ
 * @Date: 2021/1/8 19:21
 * @Version 1.0
 */
@Service
public class SetMealServiceImpl implements SetMealService {

    @Autowired
    SetMealDao setMealDao;
    @Override
    public void add(Setmeal setmeal, Integer[] checkgroupIds) {
//        添加套餐
        setMealDao.add(setmeal);
        Integer setmealId=setmeal.getId();
//        添加套餐和检查组的关系
        for (Integer checkgroupId : checkgroupIds) {
            setMealDao.add_checkgroupIds(setmealId,checkgroupId);
        }
    }

    @Override
    public PageResult<Setmeal> findPage(QueryPageBean queryPageBean) {
//        mybatis插件让分页查询变得更加简单
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        String queryString = queryPageBean.getQueryString();
//        有查询条件
        if (queryString!=null) {
            queryString="%"+queryString+"%";
        }
        Page<Setmeal> page =setMealDao.findPage(queryString);
        PageResult pageResult= new PageResult(page.getTotal(),page.getResult());
        return pageResult;
    }

    @Override
    public Setmeal findById(int id) {
       return  setMealDao.findById(id);
    }

    @Override
    public List<String> findAllImg() {
        return setMealDao.findAllImg();
    }

    @Override
    public List<Setmeal> findAll() {
        List<Setmeal> setmealList=setMealDao.findAll();
//迭代器
        setmealList.forEach(setmeal -> {
            setmeal.setImg(QiniuUtils.DOMAIN +setmeal.getImg());
        });
        return setmealList;
    }

    @Override
    public Setmeal findDetailById(int id) {
        return setMealDao.findDetailById(id);
    }
}
