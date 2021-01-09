package org.design.model;

public class Job {

	/**
	 * ID 自增
	 */
	private Integer id;
	/**
	 * 职位名称
	 */
	private String name;
	/**
	 * 职位备注
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
		return "Job [id=" + id + ", name=" + name + ", remark=" + remark + "]";
	}
	
}
