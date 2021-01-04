package org.design.model;

import java.time.LocalDateTime;

public class Employee {

    /**
     * id
     * 自增 插入数据时不需要传参数
     */
    private Integer id;

    /**
     * 姓名（登录用户名） （必需）
     */
    private String username;

    /**
     * 登录密码
     */
    private String password;

    /**
     * 员工所属部门 （必需）
     */
    private Department department;

    /**
     * 员工所在职位 （必需）
     */
    private Job job;

    /**
     * 员工所属角色
     */
    private Role role;

    /**
     * 员工上级ID
     */
    private Integer managerId;

    /**
     * 住址 （必需）
     */
    private String address;

    /**
     * 电话 （必需）
     */
    private String phone;

    /**
     * 性别 （必需）
     */
    private String gender;

    /**
     * 出生日期 （必需）
     */
    private LocalDateTime birthday;

    /**
     * 账号创建时间
     */
    private LocalDateTime createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Integer getManagerId() {
        return managerId;
    }

    public void setManagerId(Integer managerId) {
        this.managerId = managerId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public LocalDateTime getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDateTime birthday) {
        this.birthday = birthday;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", department=" + department +
                ", job=" + job +
                ", role=" + role +
                ", managerId=" + managerId +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", gender='" + gender + '\'' +
                ", birthday=" + birthday +
                ", createTime=" + createTime +
                '}';
    }
}
