package com.example.demo.service;

import com.example.demo.dto.OptionAnswerDTO;
import com.example.demo.dto.QuestionDTO;
import com.example.demo.dto.QuizDTO;
import com.example.demo.entity.Admin;
import com.example.demo.entity.quizConstructor.OptionAnswer;
import com.example.demo.entity.quizConstructor.Question;
import com.example.demo.entity.quizConstructor.Quiz;
import com.example.demo.repository.AdminRepository;
import com.example.demo.repository.PlayerRepository;
import com.example.demo.repository.QuizRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
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
                if (questions.getOptionAnswerSet() != null){
                    for(OptionAnswer optionAnswer : questions.getOptionAnswerSet()) {
                        optionAnswer.setQuestion(questions);
                    }
                }
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
                                    Set<OptionAnswer> answers = q.getOptionAnswerSet() == null ? Collections.emptySet() :
                                            q.getOptionAnswerSet().stream()
                                            .map(a -> {
                                                OptionAnswer optionAnswer = new OptionAnswer();
                                                optionAnswer.setText(a.getText());
                                                optionAnswer.setCorrect(Boolean.TRUE.equals(a.getCorrect()));                                                optionAnswer.setQuestion(question);
                                                return optionAnswer;
                                            }).collect(Collectors.toSet());
                                    question.setOptionAnswerSet(answers);

                                    question.setQuiz(setQuiz);
                                    setQuiz.getQuestion().add(question);
                                }
                        );
                    }
                    Quiz savedQuiz = quizRepository.save(setQuiz);
                    return ResponseEntity.ok(convertToDTO(savedQuiz));

                }
        ).orElseThrow();

    }

    public List<QuizDTO> getAllQuizzes() {
        List<Quiz> quizList = quizRepository.findAllWithQuestion();
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
                .map(q -> {

                    Set<OptionAnswerDTO> answerDTO = q.getOptionAnswerSet() == null ? Collections.emptySet() :
                    q.getOptionAnswerSet().stream()
                            .map(o -> new OptionAnswerDTO(
                                    o.getId(),
                                    o.getText(),
                                    o.getCorrect()
                            ))
                            .collect(Collectors.toSet());

          QuestionDTO questionDTOS =  QuestionDTO.builder()
                            .id(q.getId())
                            .content(q.getContent())
                            .optionAnswerSet(answerDTO)
                            .build();
                    return questionDTOS;

                })
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
