package it.xpug.kata.birthday_greetings;

import com.dumbster.smtp.SimpleSmtpServer;
import com.dumbster.smtp.SmtpMessage;
import it.xpug.kata.birthday_greetings.domain.*;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class AcceptanceTest {

    private static final int NONSTANDARD_PORT = 9999;
    private BirthdayService birthdayService;
    private SimpleSmtpServer mailServer;

    @Before
    public void setUp() {
        mailServer = SimpleSmtpServer.start(NONSTANDARD_PORT);
        MessageService messageService = new EmailMessageService("localhost", NONSTANDARD_PORT, "sender@here.com");
        EmployeeRepository repository = new FileEmployeeRepositoryImpl("employee_data.txt");
        birthdayService = new BirthdayService(messageService, repository);
    }

    @After
    public void tearDown() throws Exception {
        mailServer.stop();
        Thread.sleep(200);
    }

    @Test
    public void willSendGreetings_whenItsSomebodysBirthday() throws Exception {

        birthdayService.sendGreetings(toLocalDate("1982/10/08"));

        assertEquals("message not sent?", 1, mailServer.getReceivedEmailSize());
        SmtpMessage message = (SmtpMessage) mailServer.getReceivedEmail().next();
        assertEquals("Happy Birthday, dear John!", message.getBody());
        assertEquals("Happy Birthday!", message.getHeaderValue("Subject"));
        String[] recipients = message.getHeaderValues("To");
        assertEquals(1, recipients.length);

        assertEquals("john.doe@foobar.com", recipients[0].toString());
    }

    @Test
    public void willNotSendEmailsWhenNobodysBirthday() throws Exception {
        birthdayService.sendGreetings(toLocalDate("1981/10/08"));

        assertEquals("should return 0 messages", 0, mailServer.getReceivedEmailSize());
    }

    private LocalDate toLocalDate(String yyyyMMdd) {
        return LocalDate.parse(yyyyMMdd, DateTimeFormat.forPattern("yyyy/MM/dd"));
    }
}
