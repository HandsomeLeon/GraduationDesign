package org.design.model;

import java.time.LocalDateTime;

public class Absence {

    private Integer id;

    /**
     * 请假天数
     */
    private Integer days;

    /**
     * 请假理由
     */
    private String content;

    /**
     * 备注
     */
    private String remark;

    /**
     * 请假时间
     */
    private LocalDateTime leaveTime;

    /**
     * 请假流程状态
     */
    private Integer state;

    /**
     * 请假申请人ID
     */
    private Integer userId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public LocalDateTime getLeaveTime() {
        return leaveTime;
    }

    public void setLeaveTime(LocalDateTime leaveTime) {
        this.leaveTime = leaveTime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "AbsenceForm{" +
                "id=" + id +
                ", days=" + days +
                ", content='" + content + '\'' +
                ", remark='" + remark + '\'' +
                ", leaveTime=" + leaveTime +
                ", state=" + state +
                ", userId=" + userId +
                '}';
    }
}
