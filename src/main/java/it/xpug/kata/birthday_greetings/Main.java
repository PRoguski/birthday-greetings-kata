package it.xpug.kata.birthday_greetings;

import it.xpug.kata.birthday_greetings.domain.*;

import java.io.*;
import java.text.ParseException;

import javax.mail.*;
import javax.mail.internet.*;

public class Main {

    public static void main(String[] args) throws AddressException, IOException, ParseException, MessagingException {
        MessageService message = new EmailMessageService("localhost", 25, "sender@here.com");
        EmployeeRepository employeeRepository = new FileEmployeeRepositoryImpl("employee_data.txt");
        BirthdayService service = new BirthdayService(message, employeeRepository);
        service.sendGreetings(new XDate());
    }

}
