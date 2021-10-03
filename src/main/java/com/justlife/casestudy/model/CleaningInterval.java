//package com.justlife.casestudy.model;
//
//import com.fasterxml.jackson.annotation.JsonFormat;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import org.joda.time.DateTime;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//import javax.validation.constraints.NotNull;
//import java.sql.Time;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//
//@Entity
//@Getter @Setter
//@AllArgsConstructor @NoArgsConstructor
//public class CleaningInterval extends BaseEntity{
//
//    @ManyToOne
//    @JoinColumn(name = "cleaner_id", nullable = false)
//    @NotNull
//    private Cleaner cleaner;
//
//    @NotNull(message = "Start time cannot be null!")
//    @Column
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
//    private LocalDateTime startTime; // TODO
//
//    @NotNull(message = "End time cannot be null!")
//    @Column
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
//    private LocalDateTime endTime;
//}
