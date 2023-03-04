package com.meetingcalendarassistant.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int empID;
	
	private String empName;
	
	private List<String> meetings;
	
	public Employee() {
		super();
	}

	public Employee(int empID, String empName, List<String> meetings) {
		super();
		this.empID = empID;
		this.empName = empName;
		this.meetings = meetings;
	}

	public int getEmpID() {
		return empID;
	}

	public void setEmpID(int empID) {
		this.empID = empID;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public List<String> getMeetings() {
		return meetings;
	}

	public void setMeetings(List<String> meetings) {
		this.meetings = meetings;
	}
	
}
