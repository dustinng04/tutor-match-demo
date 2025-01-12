package com.example.demo.entity;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
public class Subject {
    @Field(type = FieldType.Integer, name = "syllabus_id")
    private Integer syllabusId;
    @Field(type = FieldType.Integer, name = "subject_id")
    private Integer subjectId;
    @Field(type = FieldType.Keyword, name = "level")
    private String level; // Beginner, Intermediate, Advanced
}
