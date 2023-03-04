package com.meetingcalendarassistant.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.meetingcalendarassistant.entity.Employee;

public interface CalendarRepo extends JpaRepository<Employee, Integer> {

}
