# MeetingCalendarAssistant
API to create a meeting assistant

Steps to run the API:

  1) Download the folder from github and open the folder in eclipse or any other java supporting IDE(Preffered is Eclipse)
  2) Run the application as 'Spring Boot App'
  3) Various URL's for the application:
    
    -> POST-localhost:8080/calendar/register/employee; 
        a) Takes employee name as a parameter
        b) Used to register the employee
        
    -> POST-localhost:8080/calendar/book/meeting; 
        a) In the body tab select raw and type json and enter the data in correct format:
            {
              "employee":"",
              "date":"",
              "time":""
            }
        b) Used to book the meeting with the employee
        
    -> GET-localhost:8080/calendar/freeSlots/{employee1}/{employee2};
        a) Used to find out free slots for employee1 and employee2
        
    -> GET-localhost:8080/calendar/conflicts/{time};
        a) Used to find out employees having meeting on time
        
   4) After entering URL and input click send
   
If you want to run the test case then run as 'JUnit Test'   
