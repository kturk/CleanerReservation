package com.justlife.casestudy.payload.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
public abstract class BaseRequest {
    private LocalDateTime startDateTime;

    private LocalDateTime endDateTime;

    public BaseRequest(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }
}
