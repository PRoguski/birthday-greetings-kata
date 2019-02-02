package it.xpug.kata.birthday_greetings.domain;

import org.joda.time.LocalDate;

import java.util.List;

public class BirthdayService {

    private MessageService messageService;
    private EmployeeRepository employeeRepository;

    public BirthdayService(MessageService messageService, EmployeeRepository employeeRepository) {
        this.messageService = messageService;
        this.employeeRepository = employeeRepository;
    }

    public void sendGreetings(LocalDate date) {
        List<Employee> employee = employeeRepository.findEmployeeWhereBirthDayIsIn(date);
        employee.forEach(empl -> messageService.send(BirthdayMessage.forEmployee(empl)));
    }

}
