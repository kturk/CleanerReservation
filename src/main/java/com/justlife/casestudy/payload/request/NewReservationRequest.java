package com.justlife.casestudy.payload.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class NewReservationRequest extends BaseRequest{

    private int duration;

    private int cleanerCount;

    public NewReservationRequest(LocalDateTime startDateTime, int duration, int cleanerCount) {
        super(startDateTime, startDateTime.plusHours(duration));
        this.duration = duration;
        this.cleanerCount = cleanerCount;
    }
}
