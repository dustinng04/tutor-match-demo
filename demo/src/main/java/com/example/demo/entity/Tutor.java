package com.example.demo.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;

@Document(indexName = "tutors")
@Getter
@Setter
public class Tutor {
    @Id
    private String id;
    @Field(type = FieldType.Integer, name = "tutor_id")
    private Integer tutorId;
    @Field(type = FieldType.Text, name = "name")
    private String name;
    @Field(type = FieldType.Float, name = "rating")
    private Double rating;
    @Field(type = FieldType.Float, name = "price")
    private Double price;
    @Field(type = FieldType.Nested, name = "availability")
    private List<Availability> availability;
    @Field(type = FieldType.Nested, name = "current_classes")
    private List<CurrentClass> currentClasses;
    @Field(type = FieldType.Nested, name = "subjects")
    private List<Subject> subjects;

}
