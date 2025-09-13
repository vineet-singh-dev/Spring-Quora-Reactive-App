package com.example.demo.adapters;

import com.example.demo.dto.QuestionResponseDTO;
import com.example.demo.models.Question;

import java.time.LocalDate;

public class QuestionAdapter {

    public static QuestionResponseDTO toQuestionResponseDTO(Question question) {
        return QuestionResponseDTO.builder()
                .id(question.getId())
                .title(question.getTitle())
                .content(question.getContent())
                .createdAt(question.getCreatedAt())
                .build();
    }
}
