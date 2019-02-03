package it.xpug.kata.birthday_greetings.domain;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class FileEmployeeRepositoryImpl implements EmployeeRepository {

    private String fileName;
    private List<Employee> employees = new LinkedList<>();

    public FileEmployeeRepositoryImpl(String fileName) {
        this.fileName = fileName;
        loadData();
    }

    private void loadData() {
        try (BufferedReader in = new BufferedReader(new FileReader(this.fileName))) {
            String str = "";
            str = in.readLine(); // skip header
            while ((str = in.readLine()) != null) {
                Employee employee = parseFromCSVLine(str);
                this.employees.add(employee);
            }
        } catch (IOException e) {
            throw new FileRepositoryException("Error during parse file: " + e);
        }

    }

    private Employee parseFromCSVLine(String line) {
        String[] employeePart = line.split(", ");

        if (employeePart.length != 4)
            throw new FileRepositoryException("Error during parsing line: " + line);

        try {
            LocalDate biDate = LocalDate.parse(employeePart[2], DateTimeFormat.forPattern("yyyy/MM/dd"));
            return new Employee(employeePart[1], employeePart[0], biDate, employeePart[3]);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new FileRepositoryException("Error during parse employee: " + e + " line:" + line);
        }
    }

    @Override
    public List<Employee> findEmployeeWhereBirthDayIsIn(LocalDate date) {
        return employees.stream()
                .filter(employee -> employee.isBirthday(date))
                .collect(Collectors.toList());
    }
}
