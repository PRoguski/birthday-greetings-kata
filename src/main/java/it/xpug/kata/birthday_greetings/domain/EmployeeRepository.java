package it.xpug.kata.birthday_greetings.domain;

import it.xpug.kata.birthday_greetings.XDate;

import java.util.List;

public interface EmployeeRepository {
    List<Employee> findEmployeeWhereBirthDayIsIn(XDate xDate);
}
