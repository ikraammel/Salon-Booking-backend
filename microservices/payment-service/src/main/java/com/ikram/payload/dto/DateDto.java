package com.ikram.payload.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DateDto {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
