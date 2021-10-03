package com.justlife.casestudy.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class IllegalCleaningDurationException extends RuntimeException {

    public IllegalCleaningDurationException() {
        super("Cleaning duration has to be 2 or 4 hours!");
    }
}
