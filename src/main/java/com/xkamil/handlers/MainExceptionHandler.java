package com.xkamil.handlers;

import com.xkamil.handlers.exceptions.AuthenticationException;
import com.xkamil.handlers.exceptions.BadRequestException;
import com.xkamil.handlers.exceptions.ItemAlreadyExistsException;
import com.xkamil.handlers.exceptions.ItemNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MainExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Message handle(Exception ex) {
        ex.printStackTrace();
        return new Message(ex.getMessage().toString());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(ItemNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Message handle(ItemNotFoundException ex) {

        return new Message(ex.getMessage());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(ItemAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Message handle(ItemAlreadyExistsException ex) {

        return new Message(ex.getMessage());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Message handle(AuthenticationException ex) {

        return new Message(ex.getMessage());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Message handle(BadRequestException ex) {

        return new Message(ex.getMessage());
    }
}
