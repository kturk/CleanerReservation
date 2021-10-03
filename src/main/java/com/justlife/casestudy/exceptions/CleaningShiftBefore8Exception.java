package com.justlife.casestudy.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class CleaningShiftBefore8Exception extends RuntimeException {

    public CleaningShiftBefore8Exception() {
        super("Cleaning shift cannot start before 08:00!");
    }
}
