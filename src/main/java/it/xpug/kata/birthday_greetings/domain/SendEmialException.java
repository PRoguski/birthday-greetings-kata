package it.xpug.kata.birthday_greetings.domain;

class SendEmialException extends RuntimeException {
    SendEmialException(String message) {
        super(message);
    }
}
