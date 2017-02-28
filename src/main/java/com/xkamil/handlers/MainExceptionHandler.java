package com.xkamil.handlers;

import com.xkamil.handlers.exceptions.AuthenticationException;
import com.xkamil.handlers.exceptions.BadRequestException;
import com.xkamil.handlers.exceptions.ItemAlreadyExistsException;
import com.xkamil.handlers.exceptions.ItemNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MainExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Message handle(Exception ex) {
        ex.printStackTrace();
        return new Message(ex.getMessage().toString());
    }

    @ExceptionHandler(ItemNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Message handle(ItemNotFoundException ex) {

        return new Message(ex.getMessage());
    }

    @ExceptionHandler(ItemAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Message handle(ItemAlreadyExistsException ex) {

        return new Message(ex.getMessage());
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Message handle(AuthenticationException ex) {

        return new Message(ex.getMessage());
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Message handle(BadRequestException ex) {

        return new Message(ex.getMessage());
    }
}
