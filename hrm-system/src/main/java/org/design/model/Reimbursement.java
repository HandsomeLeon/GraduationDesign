package org.design.model;


import java.time.LocalDateTime;

public class Reimbursement {
    private Integer id;

    private Double money;

    private String title;

    private String reason;

    private LocalDateTime createTime;

    private Integer state;

    private Long userId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public LocalDateTime getCreateTime() {
		return createTime;
	}

	public void setCreateTime(LocalDateTime createTime) {
		this.createTime = createTime;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "Reimbursement{" +
				"id=" + id +
				", money=" + money +
				", title='" + title + '\'' +
				", reason='" + reason + '\'' +
				", createTime=" + createTime +
				", state=" + state +
				", userId=" + userId +
				'}';
	}
}