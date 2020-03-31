package com.imagination.cbs.domain;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "booking_work_tasks")
public class BookingWorkTask implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "booking_work_id")
	private Long taskId;

	@Column(name = "task_name")
	private String taskName;

	public BookingWorkTask() {
	}

	@Column(name = "task_delivery_date")
	private Date taskDeliveryDate;

	@Column(name = "task_date_rate")
	private Double taskDateRate;

	@Column(name = "task_total_days")
	private Long taskTotalDays;

	@Column(name = "task_total_amount")
	private Double taskTotalAmount;

	@CreationTimestamp
	@Column(name = "changed_date")
	private Timestamp changedDate;

	@Column(name = "changed_by")
	private String changedBy;

	@ManyToOne
	@JoinColumn(name = "booking_revision_id")
	private BookingRevision bookingRevision;

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public BookingRevision getBookingRevision() {
		return bookingRevision;
	}

	public void setBookingRevision(BookingRevision bookingRevision) {
		this.bookingRevision = bookingRevision;
	}

	public Long getBookingWorkId() {
		return taskId;
	}

	public void setBookingWorkId(Long bookingWorkId) {
		this.taskId = bookingWorkId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public Date getTaskDeliveryDate() {
		return taskDeliveryDate;
	}

	public void setTaskDeliveryDate(Date taskDeliveryDate) {
		this.taskDeliveryDate = taskDeliveryDate;
	}

	public Double getTaskDateRate() {
		return taskDateRate;
	}

	public void setTaskDateRate(Double taskDateRate) {
		this.taskDateRate = taskDateRate;
	}

	public Long getTaskTotalDays() {
		return taskTotalDays;
	}

	public void setTaskTotalDays(Long taskTotalDays) {
		this.taskTotalDays = taskTotalDays;
	}

	public Double getTaskTotalAmount() {
		return taskTotalAmount;
	}

	public void setTaskTotalAmount(Double taskTotalAmount) {
		this.taskTotalAmount = taskTotalAmount;
	}

	public Timestamp getChangedDate() {
		return changedDate;
	}

	public void setChangedDate(Timestamp changedDate) {
		this.changedDate = changedDate;
	}

	public String getChangedBy() {
		return changedBy;
	}

	public void setChangedBy(String changedBy) {
		this.changedBy = changedBy;
	}
}
