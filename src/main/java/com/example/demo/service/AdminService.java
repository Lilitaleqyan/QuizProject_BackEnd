package com.example.demo.service;

import com.example.demo.entity.Admin;
import com.example.demo.entity.quizConstructor.Question;
import com.example.demo.entity.quizConstructor.Quiz;
import com.example.demo.repository.AdminRepository;
import com.example.demo.repository.PlayerRepository;
import com.example.demo.repository.QuizRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class AdminService {

    private final QuizRepository quizRepository;
    private final AdminRepository adminRepository;

    public AdminService(QuizRepository quizRepository, AdminRepository adminRepository) {
        this.quizRepository = quizRepository;
        this.adminRepository = adminRepository;
    }

    public void save(Admin admin) {
        adminRepository.save(admin);
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
        return quizRepository.findAllWithQuestion();
    }

    public void deleteQuiz(Long id) {
        quizRepository.deleteById(id);
    }

    public boolean existsByUserName(String userName) {
        return adminRepository.existsByUserName(userName);
    }

    public Optional<Admin> findByUserName(String userName) {
       return adminRepository.findByUserName(userName);
    }


}
