package com.justlife.casestudy.payload.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AvailableCleanersRequest extends BaseRequest{

    private int duration;

    public AvailableCleanersRequest(LocalDateTime startDateTime, int duration) {
        super(startDateTime, startDateTime.plusHours(duration));
        this.duration = duration;
    }
}
