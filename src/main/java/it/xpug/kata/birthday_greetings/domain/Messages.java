package it.xpug.kata.birthday_greetings.domain;

public abstract class Messages {
    private String body;
    private  String subject;
    private  String recipient;

    public Messages(String body, String subject, String recipient) {
        this.body = body;
        this.subject = subject;
        this.recipient = recipient;
    }

    public String getBody() {
        return body;
    }

    public String getSubject() {
        return subject;
    }

    public String getRecipient() {
        return recipient;
    }
}
