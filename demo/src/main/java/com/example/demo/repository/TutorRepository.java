package com.example.demo.repository;

import com.example.demo.entity.Tutor;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TutorRepository extends ElasticsearchRepository<Tutor, String> {

    Tutor findById(String id);
}
