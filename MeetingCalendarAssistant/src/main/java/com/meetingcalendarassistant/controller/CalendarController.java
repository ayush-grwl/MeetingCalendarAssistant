package com.meetingcalendarassistant.controller;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.meetingcalendarassistant.entity.Employee;
import com.meetingcalendarassistant.entity.Meeting;
import com.meetingcalendarassistant.service.CalendarService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/calendar")
public class CalendarController {

	@Autowired
	private CalendarService calendarService;
	
	@PostMapping("/register/employee")
	public ResponseEntity<String> registerEmployee(@RequestParam("empName") String empName){
		
		Employee registerEmployee = calendarService.registerEmployee(empName);
		if(registerEmployee!=null)
			return ResponseEntity.ok("Employee Registered With ID="+registerEmployee.getEmpID());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error!! Please Try Again Later");
		
	}
	
	@PostMapping("/book/meeting")
	public ResponseEntity<String> bookMeeting(@Valid @RequestBody Meeting meeting, BindingResult bindingResult) throws ParseException {
		
		String errors="";
		try {
			if(bindingResult.hasErrors()) {
				List<ObjectError> allErrors = bindingResult.getAllErrors();
				for(ObjectError error:allErrors) {
					errors=errors+error.getDefaultMessage()+"\n";
				}
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errors.substring(0,errors.length()-1)); 
			}
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error!! Please Try Again Later");
		}
		int result = calendarService.bookMeeting(meeting);
		if(result==0)
			return ResponseEntity.ok("Employee With ID="+meeting.getEmployee()+" Doesn't Exist");
		else if(result==1)
			return ResponseEntity.ok("Meeting With Employee With ID="+meeting.getEmployee()+" Booked On "+meeting.getDate()+" At "+meeting.getTime());
		else if(result==2)
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Employee With ID="+meeting.getEmployee()+" Is Already Booked At This Date & Time");
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error!! Please Try Again Later");
	}
	
	@GetMapping("/meeting")
	public List<Employee> getMeeting(){
		
		return calendarService.getMeeting();
		
		
	}
	
	@GetMapping("/freeSlots/{emp1}/{emp2}")
	public List<String> getFreeSlots(@PathVariable("emp1") String emp1, @PathVariable("emp2") String emp2){
				
		return calendarService.getFreeSlots(emp1,emp2);
		
	}
	
	@GetMapping("/conflicts/{time}")
	public List<String> getConflicts(@PathVariable("time") String time){
				
		return calendarService.getConflicts(time);
		
	}
	
}
