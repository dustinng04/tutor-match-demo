package com.example.demo.request;

import com.example.demo.entity.Availability;
import lombok.Data;

import java.util.List;

@Data
public class TutorSearchDto {
    private List<Availability> availabilities;
    private Integer syllabusId;
    private String level;
}
