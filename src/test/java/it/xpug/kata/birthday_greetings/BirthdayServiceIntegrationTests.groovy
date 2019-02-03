package it.xpug.kata.birthday_greetings

import com.dumbster.smtp.SimpleSmtpServer
import com.dumbster.smtp.SmtpMessage
import it.xpug.kata.birthday_greetings.domain.*
import org.joda.time.LocalDate
import spock.lang.Specification

class BirthdayServiceIntegrationTests extends Specification {


    private static final int NONSTANDARD_PORT = 9999
    private BirthdayService birthdayService
    private SimpleSmtpServer mailServer

    void setup() {
        mailServer = SimpleSmtpServer.start(NONSTANDARD_PORT)
        MessageService messageService = new EmailMessageService("localhost", NONSTANDARD_PORT, "sender@here.com")
        EmployeeRepository repository = new FileEmployeeRepositoryImpl("employee_data.txt")
        birthdayService = new BirthdayService(messageService, repository)
    }

    void cleanup() {
        mailServer.stop()
        Thread.sleep(200)
    }

    def "service send greetings email when its some body birthday"() {
        when:
        birthdayService.sendGreetings(new LocalDate(1982, 10, 8))
        SmtpMessage message = mailServer.getReceivedEmail().next()
        then:
        message.getBody() == "Happy Birthday, dear John!"
        message.getHeaderValue("Subject") == "Happy Birthday!"
        message.getHeaderValues("To").length == 1
        message.getHeaderValues("To")[0] == "john.doe@foobar.com"

    }

    def "service should't send any email"() {
        when:
        birthdayService.sendGreetings(new LocalDate(1983, 10, 8))
        def email = mailServer.getReceivedEmail()
        then:
        !email.hasNext()
    }


}
