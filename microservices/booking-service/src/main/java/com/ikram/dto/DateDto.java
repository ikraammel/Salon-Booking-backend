package com.ikram.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class DateDto {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
