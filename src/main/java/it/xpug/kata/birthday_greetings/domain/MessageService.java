package it.xpug.kata.birthday_greetings.domain;

public interface MessageService {
    void send(String subject, String body, String recipient);
}
