package it.xpug.kata.birthday_greetings.domain;

import it.xpug.kata.birthday_greetings.XDate;

import java.util.List;

public class BirthdayService {

    private MessageService messageService;
    private EmployeeRepository employeeRepository;

    public BirthdayService(MessageService messageService, EmployeeRepository employeeRepository) {
        this.messageService = messageService;
        this.employeeRepository = employeeRepository;
    }

    public void sendGreetings(XDate xDate) {
        List<Employee> employee = employeeRepository.findEmployeeWhereBirthDayIsIn(xDate);
        employee.forEach(empl -> {
            messageService.send(BirthdayMessage.of(empl));
        });
    }


}
