package com.meetingcalendarassistant.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meetingcalendarassistant.entity.Employee;
import com.meetingcalendarassistant.entity.Meeting;
import com.meetingcalendarassistant.repository.CalendarRepo;

@Service
public class CalendarService {
	
	@Autowired
	private CalendarRepo calendarRepo;
	
	public Employee registerEmployee(String empName) {
		
		Employee employee=new Employee();
		employee.setEmpName(empName);
		employee.setMeetings(List.of());
		Employee save = calendarRepo.save(employee);
		return save;
		
	}
	
	public int bookMeeting(Meeting meeting) throws ParseException {
		
		try {
			Employee employee=calendarRepo.findById(Integer.parseInt(meeting.getEmployee())).get();
			if(employee!=null) {
				List<String> list = employee.getMeetings();
				if(list.size()>0) {
					String startTime=meeting.getTime();				
					SimpleDateFormat df = new SimpleDateFormat("HH:mm");
					Date d = df.parse(startTime); 
					Calendar cal = Calendar.getInstance();
					cal.setTime(d);
					cal.add(Calendar.MINUTE, 30);
					String endTime = df.format(cal.getTime());
					for(String time:list) {
						String date=time.split("-")[0];							
						if(date.equals(meeting.getDate())) {
							try {						    
							    Date time1 = new SimpleDateFormat("HH:mm").parse(startTime);
							    Calendar calendar1 = Calendar.getInstance();
							    calendar1.setTime(time1);
							    calendar1.add(Calendar.DATE, 1);
							    
							    Date time2 = new SimpleDateFormat("HH:mm").parse(endTime);
							    Calendar calendar2 = Calendar.getInstance();
							    calendar2.setTime(time2);
							    calendar2.add(Calendar.DATE, 1);

							    Date d1 = new SimpleDateFormat("HH:mm").parse(time.split("-")[1]);
							    Calendar calendar3 = Calendar.getInstance();
							    calendar3.setTime(d1);
							    calendar3.add(Calendar.DATE, 1);

							    Date x = calendar3.getTime();
							    if (x.after(calendar1.getTime()) && x.before(calendar2.getTime()) || startTime.equals(time.split("-")[1])) {						        
							        return 2;
							    }
							} catch (Exception e) {
							    e.printStackTrace();
							}
						}					
					}
				}				
				list.add(meeting.getDate()+"-"+meeting.getTime());
				employee.setMeetings(list);
				Employee save = calendarRepo.save(employee);
				if(save!=null)
					return 1;
				return 3;
			}
			else {
				return 0;
			}
		}catch (Exception e) {
			return 0;
		}
		
	}
	
	public List<Employee> getMeeting(){
		
		return calendarRepo.findAll();
		
	}
	
	public List<String> getFreeSlots(String emp1, String emp2){
		
		Employee employee1=null, employee2=null;
		try {
			employee1=calendarRepo.findById(Integer.parseInt(emp1)).get();
			employee2=calendarRepo.findById(Integer.parseInt(emp2)).get();
			if(employee1==null||employee2==null)
				return List.of("Employee With ID="+emp1+" or ID="+emp2+" Not Exist");
		}catch (Exception e) {
			return List.of("Employee With ID="+emp1+" or ID="+emp2+" Not Exist");
		}
		List<String> timeSlots=new ArrayList<>(Arrays.asList("00:00","00:30","01:00","01:30","02:00","02:30","03:00","03:30","04:00","04:30","05:00","05:30","06:00","06:30","07:00","07:30","08:00","08:30","09:00","09:30","10:00","10:30","11:00","11:30","12:00","12:30","13:00","13:30","14:00","14:30","15:00","15:30","16:00","16:30","17:00","17:30","18:00","18:30","19:00","19:30","20:00","20:30","21:00","21:30","22:00","22:30","23:00","23:30"));
		List<String> emp1Meeting = employee1.getMeetings();
		List<String> emp2Meeting = employee2.getMeetings();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");  
		LocalDateTime now = LocalDateTime.now();  
		for(String time:emp1Meeting) {			
			String date=time.split("-")[0];	
			if(date.equals(dtf.format(now))) {
				String t = time.split("-")[1];
				int h=Integer.parseInt(t.split(":")[0]);
				int m=Integer.parseInt(t.split(":")[1]);
				if(m==0) {
					String slot1=t.split(":")[0]+":00";
					timeSlots.remove(slot1);
				}
				else if(m<30) {
					String slot1=t.split(":")[0]+":00";
					String slot2=t.split(":")[0]+":30";
					timeSlots.remove(slot1);
					timeSlots.remove(slot2);
				}
				else if(m==30) {
					timeSlots.remove(t.split(":")[0]+":30");
				}
				else {
					String slot1=t.split(":")[0]+":30";
					String slot2="";
					int h1=0;
					if(Integer.parseInt(t.split(":")[0])==23)
						h1=0;
					else
						h1=Integer.parseInt(t.split(":")[0])+1;
					if(h1<=9)
						slot2="0"+h1+":00";
					else
						slot2=h1+":00";
					timeSlots.remove(slot1);
					timeSlots.remove(slot2);
				}
			}
		}
		for(String time:emp2Meeting) {
			String date=time.split("-")[0];							
			if(date.equals(dtf.format(now))) {
				String t = time.split("-")[1];
				int h=Integer.parseInt(t.split(":")[0]);
				int m=Integer.parseInt(t.split(":")[1]);	
				if(m==0) {
					String slot1=t.split(":")[0]+":00";
					timeSlots.remove(slot1);
				}
				else if(m<30) {
					String slot1=t.split(":")[0]+":00";
					String slot2=t.split(":")[0]+":30";
					timeSlots.remove(slot1);
					timeSlots.remove(slot2);
				}
				else if(m==30) {
					timeSlots.remove(t.split(":")[0]+":30");
				}
				else {
					String slot1=t.split(":")[0]+":30";
					String slot2="";
					int h1=0;
					if(Integer.parseInt(t.split(":")[0])==23)
						h1=0;
					else
						h1=Integer.parseInt(t.split(":")[0])+1;
					if(h1<=9)
						slot2="0"+h1+":00";
					else
						slot2=h1+":00";
					timeSlots.remove(slot1);
					timeSlots.remove(slot2);
				}
			}
		}
		return timeSlots;
		
	}
	
	public List<String> getConflicts(String time){
		
		List<Employee> employeeList = calendarRepo.findAll();
		List<String> employees=new ArrayList<>();
		for(Employee employee:employeeList) {
			
			List<String> times = employee.getMeetings();
			for(String t:times) {
				if(time.equals(t.split("-")[1])) {
					employees.add(employee.getEmpName());
					break;
				}
			}
			
		}
		return employees;
		
	}
	
}
