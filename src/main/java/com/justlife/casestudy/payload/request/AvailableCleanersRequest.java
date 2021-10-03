package com.justlife.casestudy.payload.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
//@AllArgsConstructor
//@NoArgsConstructor
public class AvailableCleanersRequest extends BaseRequest{

    //    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")

    private int duration;

    public AvailableCleanersRequest(LocalDateTime startDateTime,
                                 int duration) {
        super(startDateTime, startDateTime.plusHours(duration));
        this.duration = duration;
    }
}
