package com.meetingcalendarassistant.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.meetingcalendarassistant.entity.Employee;
import com.meetingcalendarassistant.entity.Meeting;
import com.meetingcalendarassistant.repository.CalendarRepo;

@SpringBootTest
class CalendarServiceTest {

	@Autowired
	private CalendarService calendarService;
	
	@Autowired
	private CalendarRepo calendarRepo;
	
	int id1=0,id2=0;
	
	Employee createEmployee(String name) {
		
		Employee employee1 = calendarService.registerEmployee(name);
		return employee1;
		
	}
	
	int book(int id,String time) throws ParseException {
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  
	    Date date = new Date(); 
		Meeting meeting=new Meeting(id+"",formatter.format(date),time);
		int bookMeeting = calendarService.bookMeeting(meeting);
		return bookMeeting;
		
	}
	
	@Test
	void bookMeeting() throws ParseException {
		
		Employee employee1 = createEmployee("ABC");
		id1=employee1.getEmpID();
		int bookMeeting = book(id1, "13:00");
		calendarRepo.delete(employee1);
		assertThat(bookMeeting).isEqualTo(1);		
		
	}
	
	@Test
	void bookMeeting1() throws ParseException {
		
		Employee employee1 = createEmployee("ABC");
		id1=employee1.getEmpID();
		book(id1, "13:00");
		int bookMeeting = book(id1, "13:00");
		calendarRepo.delete(employee1);
		assertThat(bookMeeting).isEqualTo(2);		
		
	}

	@Test
	void bookMeeting2() throws ParseException {
		
		Employee employee1 = createEmployee("ABC");
		id1=employee1.getEmpID();
		int bookMeeting = book(id1, "15:00");
		calendarRepo.delete(employee1);
		assertThat(bookMeeting).isEqualTo(1);		
		
	}
	
	@Test
	void bookMeeting4() throws ParseException {
		
		Employee employee2 = createEmployee("XYZ");
		id2=employee2.getEmpID();
		int bookMeeting = book(id2, "14:00");
		calendarRepo.delete(employee2);
		assertThat(bookMeeting).isEqualTo(1);		
		
	}
	
	@Test
	void freeSlots() throws ParseException {
		
		Employee employee1 = createEmployee("ABC");
		id1=employee1.getEmpID();
		Employee employee2 = createEmployee("XYZ");
		id2=employee2.getEmpID();
		book(id1, "13:00");
		book(id1, "15:00");
		book(id2, "14:00");
		List<String> slots=List.of("00:00","00:30","01:00","01:30","02:00","02:30","03:00","03:30","04:00","04:30","05:00","05:30","06:00","06:30","07:00","07:30","08:00","08:30","09:00","09:30","10:00","10:30","11:00","11:30","12:00","12:30","13:30","14:30","15:30","16:00","16:30","17:00","17:30","18:00","18:30","19:00","19:30","20:00","20:30","21:00","21:30","22:00","22:30","23:00","23:30");
		List<String> freeSlots = calendarService.getFreeSlots(id1+"", id2+"");
		calendarRepo.delete(employee1);
		calendarRepo.delete(employee2);
		assertThat(freeSlots).isEqualTo(slots);		
		
	}
	
}
