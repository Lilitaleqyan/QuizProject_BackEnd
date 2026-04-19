package com.example.demo.service;

import com.example.demo.dto.QuestionDTO;
import com.example.demo.dto.QuizDTO;
import com.example.demo.entity.Player;
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
import java.util.Set;
import java.util.stream.Collectors;

@Transactional
@Service
public class AdminService {

    private final QuizRepository quizRepository;
    private final AdminRepository adminRepository;
    private final PlayerRepository playerRepository;

    public AdminService(QuizRepository quizRepository, AdminRepository adminRepository, PlayerRepository playerRepository) {
        this.quizRepository = quizRepository;
        this.adminRepository = adminRepository;
        this.playerRepository = playerRepository;
    }

    public void save(Admin admin) {
        adminRepository.save(admin);
    }

    public void addQuiz(Quiz quiz) {
        if (quiz.getQuestion() != null) {
            for (Question questions : quiz.getQuestion()) {
                questions.setQuiz(quiz);
            }
        }

        quizRepository.save(quiz);
    }

    @Transactional
    public ResponseEntity<QuizDTO> update(QuizDTO quizDto, Long id) {
        return quizRepository.findById(id).map(setQuiz -> {
                    setQuiz.setTitle(quizDto.getTitle());
                    setQuiz.setLevel(quizDto.getLevel());
                    setQuiz.setTopic(quizDto.getTopic());

                    setQuiz.getQuestion().clear();
                    quizRepository.saveAndFlush(setQuiz);

                    if (quizDto.getQuestion() != null) {

                        quizDto.getQuestion().forEach(q -> {
                                    Question question = new Question();
                                    question.setContent(q.getContent());
                                    question.setCorrectAnswer(q.getCorrectAnswer());
                                    question.setWrongAnswer1(q.getWrongAnswer1());
                                    question.setWrongAnswer2(q.getWrongAnswer2());
                                    question.setWrongAnswer3(q.getWrongAnswer3());

                                    question.setQuiz(setQuiz);
                                    setQuiz.getQuestion().add(question);
                                }
                        );
                    }
                    Quiz savedQuiz  = quizRepository.save(setQuiz);
                    return ResponseEntity.ok(convertToDTO(savedQuiz));

                }
        ).orElseThrow();

    }

    public List<QuizDTO> getAllQuizzes() {
        List<Quiz> quizList =  quizRepository.findAllWithQuestion();
        return quizList.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
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

    private QuizDTO convertToDTO(Quiz quiz) {
        Set<QuestionDTO> questionDto = quiz.getQuestion().stream()
                .map(q -> QuestionDTO.builder()
                        .id(q.getId())
                        .content(q.getContent())
                        .correctAnswer(q.getCorrectAnswer())
                        .wrongAnswer1(q.getWrongAnswer1())
                        .wrongAnswer2(q.getWrongAnswer2())
                        .wrongAnswer3(q.getWrongAnswer3())
                        .build())
                .collect(Collectors.toSet());

        return QuizDTO.builder()
                .id(quiz.getId())
                .title(quiz.getTitle())
                .level(quiz.getLevel())
                .topic(quiz.getTopic())
                .question(questionDto)
                .build();
    }
}
