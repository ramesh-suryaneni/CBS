package com.imagination.cbs.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.imagination.cbs.domain.BookingRevision;
/**
 * The persistent class for the booking_work_tasks database table.
 * 
 */
@Entity
@Table(name="booking_work_tasks")
@NamedQuery(name="BookingWorkTasks.findAll", query="SELECT b from BookingWorkTasks b")
public class BookingWorkTasks implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="booking_work_id")
	private long bookingWorkId;
	
	@Column(name="task_name")
	private String taskName;
	
	@Column(name="task_delivery_date")
	private Timestamp taskDeliveryDate;
	
	@Column(name = "task_date_rate")
	private BigDecimal taskDateRate;
	
	@Column(name="task_total_days")
	private long taskTotalDays;
	
	@Column(name = "task_total_amount")
	private BigDecimal taskTotalAmmount;
	
	@Column(name="changed_date")
	private Timestamp changedDate;
	
	@Column(name="changed_by")
	private String changedBy;
	
	@OneToOne
	@JoinColumn(name="booking_revision_id")
	private BookingRevision bookingRevision;

	public long getBookingWorkId() {
		return bookingWorkId;
	}

	public void setBookingWorkId(long bookingWorkId) {
		this.bookingWorkId = bookingWorkId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public Timestamp getTaskDeliveryDate() {
		return taskDeliveryDate;
	}

	public void setTaskDeliveryDate(Timestamp taskDeliveryDate) {
		this.taskDeliveryDate = taskDeliveryDate;
	}

	public BigDecimal getTaskDateRate() {
		return taskDateRate;
	}

	public void setTaskDateRate(BigDecimal taskDateRate) {
		this.taskDateRate = taskDateRate;
	}

	public long getTaskTotalDays() {
		return taskTotalDays;
	}

	public void setTaskTotalDays(long taskTotalDays) {
		this.taskTotalDays = taskTotalDays;
	}

	public BigDecimal getTaskTotalAmmount() {
		return taskTotalAmmount;
	}

	public void setTaskTotalAmmount(BigDecimal taskTotalAmmount) {
		this.taskTotalAmmount = taskTotalAmmount;
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
