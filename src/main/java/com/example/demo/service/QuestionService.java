package com.example.demo.service;

import com.example.demo.repository.QuestionRepository;
import org.springframework.stereotype.Service;

@Service
public class QuestionService {
    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }
     public void deleteQuestion(Long id) {
        questionRepository.deleteById(id);
     }
}
