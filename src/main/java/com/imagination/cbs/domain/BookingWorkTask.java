package com.imagination.cbs.domain;

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

@Entity
@Table(name = "booking_work_tasks")
public class BookingWorkTask {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "booking_work_id")
	private Long bookingWorkId;

	@Column(name = "task_name")
	private String taskName;

	@Column(name = "task_delivery_date")
	private Date taskDeliveryDate;

	@Column(name = "task_date_rate")
	private Double taskDateRate;

	@Column(name = "task_total_days")
	private Long taskTotalDays;

	@Column(name = "task_total_amount")
	private Double taskTotalAmount;

	@Column(name = "changed_date")
	private Timestamp changedDate;

	@Column(name = "changed_by")
	private String changedBy;

	@ManyToOne
	@JoinColumn(name = "booking_revision_id")
	private BookingRevision bookingRevision;

	public Long getBookingWorkId() {
		return bookingWorkId;
	}

	public void setBookingWorkId(Long bookingWorkId) {
		this.bookingWorkId = bookingWorkId;
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

	public BookingRevision getBookingRevision() {
		return bookingRevision;
	}

	public void setBookingRevision(BookingRevision bookingRevision) {
		this.bookingRevision = bookingRevision;
	}
}
