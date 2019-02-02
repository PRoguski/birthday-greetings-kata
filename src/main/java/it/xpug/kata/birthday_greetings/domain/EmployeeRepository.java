package it.xpug.kata.birthday_greetings.domain;

import org.joda.time.LocalDate;

import java.util.List;

public interface EmployeeRepository {
    List<Employee> findEmployeeWhereBirthDayIsIn(LocalDate date);
}
