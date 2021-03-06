package org.design.model;

import javax.persistence.Id;

public class Department {

    /**
     * id
     * 自增 插入数据时不需要传参数
     */
    @Id
    private Integer id;
    /**
     * 部门名称 （必需）
     */
    private String name;
    /**
     * 部门备注 （非必需）
     */
    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "Department [id=" + id + ", name=" + name + ", remark=" + remark + "]";
    }

}
