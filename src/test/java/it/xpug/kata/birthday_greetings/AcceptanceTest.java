package it.xpug.kata.birthday_greetings;

import static org.junit.Assert.*;

import it.xpug.kata.birthday_greetings.domain.BirthdayService;
import it.xpug.kata.birthday_greetings.domain.EmailMessageService;
import it.xpug.kata.birthday_greetings.domain.MessageService;
import org.junit.*;

import com.dumbster.smtp.*;


public class AcceptanceTest {

    private static final int NONSTANDARD_PORT = 9999;
    private BirthdayService birthdayService;
    private SimpleSmtpServer mailServer;
    private MessageService messageService;

    @Before
    public void setUp() {
        mailServer = SimpleSmtpServer.start(NONSTANDARD_PORT);
        messageService = new EmailMessageService("localhost", NONSTANDARD_PORT, "sender@here.com");
        birthdayService = new BirthdayService(messageService);
    }

    @After
    public void tearDown() throws Exception {
        mailServer.stop();
        Thread.sleep(200);
    }

    @Test
    public void willSendGreetings_whenItsSomebodysBirthday() throws Exception {

        birthdayService.sendGreetings("employee_data.txt", new XDate("2008/10/08"));

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
        birthdayService.sendGreetings("employee_data.txt", new XDate("2008/01/01"));

        assertEquals("what? messages?", 0, mailServer.getReceivedEmailSize());
    }
}
