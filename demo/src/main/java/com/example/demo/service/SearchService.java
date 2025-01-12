package com.example.demo.service;

import com.example.demo.entity.Availability;
import com.example.demo.entity.Tutor;
import com.example.demo.repository.TutorRepository;
import com.example.demo.request.TutorSearchDto;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.common.lucene.search.function.CombineFunction;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class SearchService {
    TutorRepository tutorRepository;
    ElasticsearchRestTemplate elasticsearchTemplate;
    public List<Tutor> findTutor(TutorSearchDto searchDto) {
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        for (Availability availability : searchDto.getAvailabilities()) {
            boolQuery.should(QueryBuilders.nestedQuery(
                    "availability",
                    QueryBuilders.boolQuery()
                            .must(QueryBuilders.termQuery("availability.day_of_week", availability.getDayOfWeek()))
                            .must(QueryBuilders.rangeQuery("availability.start_time").lte(availability.getEndTime().format(formatter)))
                            .must(QueryBuilders.rangeQuery("availability.end_time").gte(availability.getStartTime().format(formatter))),
                    ScoreMode.None
            ));
        }

        // Add subject criteria
        if (searchDto.getLevel() != null && searchDto.getSyllabusId() != null) {
            boolQuery.should(QueryBuilders.nestedQuery(
                    "subjects",
                    QueryBuilders.boolQuery()
                            .must(QueryBuilders.termQuery("subjects.syllabus_id", searchDto.getSyllabusId()))
                            .must(QueryBuilders.termQuery("subjects.level", searchDto.getLevel())),
                    ScoreMode.None
            ));
        }

        // Simplified scoring
        FunctionScoreQueryBuilder functionScoreQuery = QueryBuilders.functionScoreQuery(
                boolQuery,
                new FunctionScoreQueryBuilder.FilterFunctionBuilder[] {
                        new FunctionScoreQueryBuilder.FilterFunctionBuilder(
                                ScoreFunctionBuilders.fieldValueFactorFunction("rating").factor(0.2f)
                        )
                }
        ).boostMode(CombineFunction.SUM);

        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(functionScoreQuery)
                .withMaxResults(50) // Optional: limit the number of results for efficiency
                .build();
        SearchHits<Tutor> searchHits = elasticsearchTemplate.search(searchQuery , Tutor.class);
        log.info(searchHits.getSearchHits().toString());
        log.info(String.valueOf(searchHits.getTotalHits()));
        return searchHits.stream().map(SearchHit::getContent).collect(Collectors.toList());

    }

    public Tutor findTutorById(String id) {
        return tutorRepository.findById(id);
    }

}
