package com.example.demo.controllers;


import com.example.demo.dto.QuestionRequestDTO;
import com.example.demo.dto.QuestionResponseDTO;
import com.example.demo.services.IQuestionService;
import com.example.demo.services.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/questions")
public class QuestionController {

    private final IQuestionService questionService;

    @PostMapping
    public Mono<QuestionResponseDTO> createQuestion(@RequestBody QuestionRequestDTO questionRequestDTO) {
        return questionService.createQuestion(questionRequestDTO)
                .doOnSuccess(response -> System.out.println("Question created successfully: " + response))
                .doOnError(error -> System.err.println("Error creating question: " + error));
    }
    @GetMapping("/search")

    public Flux<QuestionResponseDTO> searchQuestion(
        @RequestParam String query,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size)
    {
        return questionService.searchQuestions(query,page,size);
    }
    public Flux<QuestionResponseDTO>  getAllQuestions(
            @RequestParam(required = false) String cursor,
            @RequestParam(defaultValue = "10") int size
    ) {
        return questionService.getAllQuestions(cursor,size)
                .doOnComplete(() -> System.out.println("Question fetched successfully "))
                .doOnError(error -> System.err.println("Error creating question: " + error));

    }
}
