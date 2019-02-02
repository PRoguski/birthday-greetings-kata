package it.xpug.kata.birthday_greetings;

import it.xpug.kata.birthday_greetings.domain.*;
import org.joda.time.LocalDate;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import java.io.IOException;
import java.text.ParseException;

public class Main {

    public static void main(String[] args) throws AddressException, IOException, ParseException, MessagingException {
        MessageService message = new EmailMessageService("localhost", 25, "sender@here.com");
        EmployeeRepository employeeRepository = new FileEmployeeRepositoryImpl("employee_data.txt");
        BirthdayService service = new BirthdayService(message, employeeRepository);
        service.sendGreetings(LocalDate.now());
    }

}
