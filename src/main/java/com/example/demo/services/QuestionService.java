package com.example.demo.services;

import com.example.demo.adapters.QuestionAdapter;
import com.example.demo.dto.QuestionRequestDTO;
import com.example.demo.dto.QuestionResponseDTO;
import com.example.demo.models.Question;
import com.example.demo.repositories.QuestionRepository;
import com.example.demo.utils.CursorUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class QuestionService implements IQuestionService {

    private final QuestionRepository questionRepository;
    @Override
    public Mono<QuestionResponseDTO> createQuestion(QuestionRequestDTO questionRequestDTO) {

        Question question = Question.builder()
                .title(questionRequestDTO.getTitle())
                .content(questionRequestDTO.getContent())
                .createdAt(LocalDate.now())
                .updatedAt(LocalDate.now())
                .build();

        return questionRepository.save(question)
                .map(QuestionAdapter::toQuestionResponseDTO)
                .doOnSuccess(response -> System.out.println("Question created successfully: " + response))
                .doOnError(error -> System.err.println("Error creating question: " + error));

    }

    public Flux<QuestionResponseDTO> searchQuestions(String searchTerm, int offset, int page){
        return questionRepository.findByTitleOrContentContainingIgnoreCase(searchTerm, PageRequest.of(offset, page))
                .map(QuestionAdapter::toQuestionResponseDTO)
                .doOnError(error -> System.err.println("Error creating question: " + error))
                .doOnComplete(() -> System.out.println("Question searched successfully"));
    }

    public Flux<QuestionResponseDTO> getAllQuestions(String cursor, int size) {
        Pageable pageable = PageRequest.of(0, size);
        if (!CursorUtils.isValidCursor(cursor)) {
            return questionRepository.findTop10ByOrderByCreatedAtAsc()
                    .take(size)
                    .map(QuestionAdapter::toQuestionResponseDTO)
                    .doOnError(error -> System.err.println("Error creating question: " + error))
                    .doOnComplete(() -> System.out.println("Question fetched successfully"));
        } else {
            LocalDateTime cursorTimeStamp = CursorUtils.parseCursor(cursor);
            return questionRepository.findByCreatedAtGreaterThanOrderByCreatedAtAsc(cursorTimeStamp, pageable)
                    .map(QuestionAdapter::toQuestionResponseDTO)
                    .doOnError(error -> System.err.println("Error creating question: " + error))
                    .doOnComplete(() -> System.out.println("Question fetched successfully"));
        }
    }
}
