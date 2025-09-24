package com.example.demo.services;

import com.example.demo.dto.QuestionRequestDTO;
import com.example.demo.dto.QuestionResponseDTO;
import com.example.demo.models.Question;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IQuestionService {

    public Mono<QuestionResponseDTO> createQuestion(QuestionRequestDTO question);

    public Flux<QuestionResponseDTO> searchQuestions(String searchTerm, int page, int size);

    public Flux<QuestionResponseDTO> getAllQuestions(String cursor, int size);
}
