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

     Page<CheckGroup> findPage(String queryString) ;

    void add(CheckGroup checkGroup);

//    mybatis多参数且类型都相同需要用注解
    void add_checkItemId(@Param("checkGroupId") Integer checkGroupId,@Param("checkItemId") Integer checkItemId);

    CheckGroup findByid(Integer id);

    List<Integer> findCheckItemIdsByCheckGroupId(int id);

    void update(CheckGroup checkGroup);

    void deleteCheckGroup_CheckItem_Re(Integer id);

    void addCheckGroup_CheckItem_Re(@Param("checkGroupId") Integer id,@Param("checkitemId") int checkitemId);
}
