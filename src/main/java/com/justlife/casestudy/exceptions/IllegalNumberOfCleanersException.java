package com.justlife.casestudy.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class IllegalNumberOfCleanersException extends RuntimeException {

    public IllegalNumberOfCleanersException() {

        super("Number of cleaners has to be 1, 2 or 3!");
    }
}
