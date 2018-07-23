package com.stu.dmt.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "monitoring")
public class Monitoring implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Double value;
	private Date dtime;
	private Short alarm;
	private Double min;
	private Double max;
	
	public Monitoring() {}
	
	public Monitoring(Integer id, Double value, Date dtime) {
		this.id = id;
		this.value = value;
		this.dtime = dtime;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name = "value", precision = 22, scale = 0)
	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dtime", length = 19, nullable=false)
	public Date getDtime() {
		return dtime;
	}

	public void setDtime(Date dtime) {
		this.dtime = dtime;
	}
	
	@Column(name = "alarm", nullable=false)
	public Short getAlarm() {
		return alarm;
	}

	public void setAlarm(Short alarm) {
		this.alarm = alarm;
	}
	
	@Column(name = "min", precision = 22, scale = 0)
	public Double getMin() {
		return min;
	}

	public void setMin(Double min) {
		this.min = min;
	}
	
	@Column(name = "max", precision = 22, scale = 0)
	public Double getMax() {
		return max;
	}

	public void setMax(Double max) {
		this.max = max;
	}
	
}
