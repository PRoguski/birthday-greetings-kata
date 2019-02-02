package it.xpug.kata.birthday_greetings.domain;

public class BirthdayMessage extends Messagess {

    private BirthdayMessage(String body, String subject, String recipient) {
        super(body, subject, recipient);
    }

    public static BirthdayMessage of(Employee employee1) {
        String recipient = employee1.getEmail();
        String body = "Happy Birthday, dear %NAME%!".replace("%NAME%", employee1.getFirstName());
        String subject = "Happy Birthday!";
        return new BirthdayMessage(body, subject, recipient);
    }
}
