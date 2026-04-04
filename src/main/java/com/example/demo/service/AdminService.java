package com.example.demo.service;

import com.example.demo.entity.quizConstructor.Question;
import com.example.demo.entity.quizConstructor.Quiz;
import com.example.demo.repository.QuizRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Transactional
@Service
public class AdminService {

    private final QuizRepository quizRepository;

    public AdminService(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    public void addQuiz(Quiz quiz) {
        if (quiz.getQuestion() != null) {
            for (Question questions: quiz.getQuestion()) {
                questions.setQuiz(quiz);
            }
        }

        quizRepository.save(quiz);
    }

    public ResponseEntity<Quiz> update(Quiz quiz, Long id) {
       return quizRepository.findById(id).map(setQuiz -> {
                    setQuiz.setTitle(quiz.getTitle());
                    setQuiz.setLevel(quiz.getLevel());
                    setQuiz.setTopic(quiz.getTopic());
                    setQuiz.getQuestion().clear();
                    if (quiz.getQuestion() != null) {

                        quiz.getQuestion().forEach(q -> q.setQuiz(setQuiz));
                         setQuiz.getQuestion().addAll(quiz.getQuestion());
                    }
                    return ResponseEntity.ok(quizRepository.save(setQuiz));

                }
                ).orElseThrow();

    }

    public List<Quiz> getAllQuizzes() {
        return quizRepository.findAll();
    }

    public void deleteQuiz(Long id) {
        quizRepository.deleteById(id);
    }


}
