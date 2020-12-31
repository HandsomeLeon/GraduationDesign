package org.design.service;

import java.util.List;

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
    List<T> findAll();

    /**
     * 模糊查询方法
     * @param model
     * @return
     */
    List<T> findExample(T model);
}
