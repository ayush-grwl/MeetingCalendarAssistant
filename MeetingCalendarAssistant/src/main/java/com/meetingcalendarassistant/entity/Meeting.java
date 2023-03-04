package com.meetingcalendarassistant.entity;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class Meeting {

	@NotNull(message = "Enter Employee ID")
	public String employee;
	
	@NotNull(message = "Enter The Meeting Date")
	@Pattern(regexp = "^(3[01]|[12][0-9]|0[1-9])/(1[0-2]|0[1-9])/[0-9]{4}$", message = "Enter Date In Correct Format(dd/mm/yyyy)")
	public String date;
	
	@NotNull(message = "Enter The Meeting Time")
	@Pattern(regexp = "([01]?[0-9]|2[0-3]):[0-5][0-9]", message = "Enter Time In 24 Hour Format")
	public String time;
	
	public Meeting() {
		super();
	}

	public Meeting(@NotNull(message = "Enter Employee ID") String employee,
			@NotNull(message = "Enter The Meeting Date") @Pattern(regexp = "^(3[01]|[12][0-9]|0[1-9])/(1[0-2]|0[1-9])/[0-9]{4}$", message = "Enter Date In Correct Format(dd/mm/yyyy)") String date,
			@NotNull(message = "Enter The Meeting Time") @Pattern(regexp = "([01]?[0-9]|2[0-3]):[0-5][0-9]", message = "Enter Time In 24 Hour Format") String time) {
		super();
		this.employee = employee;
		this.date = date;
		this.time = time;
	}

	public String getEmployee() {
		return employee;
	}

	public void setEmployee(String employee) {
		this.employee = employee;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	
}
