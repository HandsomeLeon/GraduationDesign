package org.design.service;

import org.omg.CORBA.OBJ_ADAPTER;

import java.util.List;
import java.util.Map;

/**
 * 增删改查 通用接口
 * @param <T>
 * @param <ID>
 */
public interface BaseService<T, ID> {

    /**
     * 新增数据方法
     * @param model
     */
    void save(T model);

    /**
     * 删除方法
     * @param id
     */
    void delete(ID id);

    /**
     * 更新数据方法
     * @param model
     */
    void update(T model);

    /**
     * 查询单个用户方法
     * @param id
     * @return
     */
    T get(ID id);

    /**
     * 查询全部数据方法
     * @return
     */
    Map<String, Object> findAll(Integer page, Integer limit);

    /**
     * 模糊查询方法
     * @param model
     * @return
     */
    Map<String, Object> findExample(T model, Integer page, Integer limit);
}
