package com.example.demo.entity;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalTime;

@Data
public class Availability {
    @Field(type = FieldType.Keyword, name = "day_of_week")
    private String dayOfWeek;
    @Field(type = FieldType.Date, format = {}, pattern = "HH:mm", name = "start_time")
    private LocalTime startTime;
    @Field(type = FieldType.Date, format = {}, pattern = "HH:mm", name = "end_time")
    private LocalTime endTime;
    @Field(type = FieldType.Boolean, name = "recurring")
    private Boolean recurring;
}
