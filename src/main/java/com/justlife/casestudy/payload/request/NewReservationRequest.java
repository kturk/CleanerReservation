package com.justlife.casestudy.payload.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
//@AllArgsConstructor
//@NoArgsConstructor
public class NewReservationRequest {

//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @NotNull
    private LocalDateTime startDateTime;

    private int duration;

    private LocalDateTime endDateTime;// = startDateTime.plusHours(duration);

    private int cleanerCount;

    public NewReservationRequest(LocalDateTime startDateTime,
                                 int duration,
                                 int cleanerCount) {
        this.startDateTime = startDateTime;
        this.duration = duration;
        this.endDateTime = startDateTime.plusHours(duration);
        this.cleanerCount = cleanerCount;
    }
}
