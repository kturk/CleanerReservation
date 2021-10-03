package com.justlife.casestudy.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class CleaningShiftAfter22Exception extends RuntimeException {

    public CleaningShiftAfter22Exception() {

        super("Cleaning shift cannot end after 22:00!");
    }
}
