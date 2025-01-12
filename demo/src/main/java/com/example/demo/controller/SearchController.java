package com.example.demo.controller;

import com.example.demo.entity.Availability;
import com.example.demo.entity.Tutor;
import com.example.demo.request.TutorSearchDto;
import com.example.demo.service.SearchService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class SearchController {
    SearchService searchService;

//    @GetMapping("/health")
//    public String checkElasticsearch() {
//        return elasticsearchTemplate.indexOps(IndexCoordinates.of("test-index")).exists() ?
//                "Elasticsearch is running!" :
//                "Elasticsearch is down!";
//    }

    @PostMapping("/search")
    public List<Tutor> findTutor(@RequestBody TutorSearchDto searchDto) {
        return searchService.findTutor(searchDto);
    }

    @GetMapping("/search-id")
    public Tutor findById(@RequestBody String id) {
        return searchService.findTutorById(id);
    }
}
