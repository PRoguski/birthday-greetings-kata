package it.xpug.kata.birthday_greetings;

import it.xpug.kata.birthday_greetings.domain.BirthdayService;
import it.xpug.kata.birthday_greetings.domain.EmailMessageService;
import it.xpug.kata.birthday_greetings.domain.MessageService;

import java.io.*;
import java.text.ParseException;

import javax.mail.*;
import javax.mail.internet.*;

public class Main {

    public static void main(String[] args) throws AddressException, IOException, ParseException, MessagingException {
        MessageService message = new EmailMessageService("localhost", 25, "sender@here.com");
        BirthdayService service = new BirthdayService(message);
        service.sendGreetings("employee_data.txt", new XDate());
    }

}
