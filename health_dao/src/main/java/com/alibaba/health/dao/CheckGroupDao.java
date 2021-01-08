package com.alibaba.health.dao;

import com.alibaba.health.pojo.CheckGroup;
import com.alibaba.health.pojo.CheckItem;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author:ZZZ
 * @Date: 2021/1/6 18:31
 * @Version 1.0
 */
public interface CheckGroupDao {
//检查组的分页
     Page<CheckGroup> findPage(String queryString) ;
//添加检查组
    void add(CheckGroup checkGroup);

//    mybatis多参数且类型都相同需要用注解
    void add_checkItemId(@Param("checkGroupId") Integer checkGroupId,@Param("checkItemId") Integer checkItemId);
//根据检查组id查询检查组
    CheckGroup findByid(Integer id);
//根据检查组id查询检查组检查项中间关联表
    List<Integer> findCheckItemIdsByCheckGroupId(int id);
//更新检查组
    void update(CheckGroup checkGroup);
//删除检查组检查项中间表关联项
    void deleteCheckGroup_CheckItem_Re(Integer id);
//添加检查组检查项关联项
    void addCheckGroup_CheckItem_Re(@Param("checkGroupId") Integer id,@Param("checkitemId") int checkitemId);
//根据检查组id查询套餐检查组中间关联
   Integer findt_setmeal_checkgroupByGId(int id);
//根据检查组id删除检查组
    void deleteById(int id);
}
