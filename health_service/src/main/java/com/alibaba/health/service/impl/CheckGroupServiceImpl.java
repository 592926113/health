package com.alibaba.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.health.dao.CheckGroupDao;
import com.alibaba.health.entity.PageResult;
import com.alibaba.health.entity.QueryPageBean;
import com.alibaba.health.entity.Result;
import com.alibaba.health.exception.Myexception;
import com.alibaba.health.pojo.CheckGroup;
import com.alibaba.health.pojo.CheckItem;
import com.alibaba.health.service.CheckGroupService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author:ZZZ
 * @Date: 2021/1/6 18:24
 * @Version 1.0
 */
@Service(interfaceClass = CheckGroupService.class)
public class CheckGroupServiceImpl implements CheckGroupService {

    @Autowired
    CheckGroupDao checkGroupDao;

//    开启事务
    @Transactional
    @Override
    public void add(CheckGroup checkGroup, Integer[] checkItemIds) {
//        添加检查组
        checkGroupDao.add(checkGroup);
//        添加检查组后得到新增的id
        Integer checkGroupId=checkGroup.getId();
//        中间关系表的添加记录
        for (Integer checkItemId : checkItemIds) {
//           检查组id——检查项id
            checkGroupDao.add_checkItemId(checkGroupId,checkItemId);
        }


    }


    @Override
    public PageResult<CheckItem> findPage(QueryPageBean queryPageBean) {
// mybatis-plus插件
//        页号，页面大小
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        if(null!=queryPageBean.getQueryString()){
            queryPageBean.setQueryString("%"+queryPageBean.getQueryString()+"%");
        }
      Page<CheckGroup> page =  checkGroupDao.findPage(queryPageBean.getQueryString());
        PageResult pageResult = new PageResult(page.getTotal(),page.getResult());
        return pageResult;
    }

    @Override
    public CheckGroup findByid(Integer id) {
        return checkGroupDao.findByid(id);
    }

    @Override
    public List<Integer> findCheckItemIdsByCheckGroupId(int id) {
        return checkGroupDao.findCheckItemIdsByCheckGroupId(id);
    }

    @Transactional
    @Override
    public void update(CheckGroup checkGroup, int[] checkitemIds) {
//        更新检查组
        checkGroupDao.update(checkGroup);
//        删除该检查组的id下的和检查项的关联
        checkGroupDao.deleteCheckGroup_CheckItem_Re(checkGroup.getId());
//        添加关联
        for (int checkitemId : checkitemIds) {
            checkGroupDao.addCheckGroup_CheckItem_Re(checkGroup.getId(),checkitemId);
        }
    }

    @Transactional
    @Override
    public void deleteById(int id) throws Myexception{
       Integer count= checkGroupDao.findt_setmeal_checkgroupByGId(id);
        if(count>0){
//            关联表有值
            throw new Myexception("套餐和检查组有关联");
        }
        //        直接删除检查项和检查组中间表关联值
        checkGroupDao.deleteCheckGroup_CheckItem_Re(id);
//        没有值就能删除检查组
        checkGroupDao.deleteById(id);
    }
}
