package com.justlife.casestudy.payload.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class UpdateReservationRequest extends BaseRequest{

    public UpdateReservationRequest(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        super(startDateTime, endDateTime);
    }

}
