package it.xpug.kata.birthday_greetings.domain;

import it.xpug.kata.birthday_greetings.XDate;

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

    private Employee parseFromCSVLine(String str) {
        String[] employeeData = str.split(", ");
        try {
            return new Employee(employeeData[1], employeeData[0], employeeData[2], employeeData[3]);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new IllegalStateException("TODO");
        }
    }

    @Override
    public List<Employee> findEmployeeWhereBirthDayIsIn(XDate xDate) {
        return employees.stream()
                .filter(employee -> employee.isBirthday(xDate))
                .collect(Collectors.toList());
    }
}
