package com.justlife.casestudy.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class FridayWorkNotAllowedException extends RuntimeException {

    public FridayWorkNotAllowedException() {

        super("Cleaners don't work in Fridays!");
    }
}
