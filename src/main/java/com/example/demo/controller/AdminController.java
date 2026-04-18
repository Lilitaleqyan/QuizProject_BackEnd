package com.example.demo.controller;

import com.example.demo.dto.PlayerDTO;
import com.example.demo.dto.QuizDTO;
import com.example.demo.dto.QuizScoreResultDTO;
import com.example.demo.entity.Player;
import com.example.demo.entity.quizConstructor.Quiz;
import com.example.demo.service.AdminService;
import com.example.demo.service.PlayerService;
import com.example.demo.service.QuestionService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;
    private final PlayerService playerService;
    private final QuestionService questionService;

    public AdminController(AdminService adminService, PlayerService playerService, QuestionService questionService) {
        this.adminService = adminService;
        this.playerService = playerService;
        this.questionService = questionService;

    }

    @PostMapping("/addQuiz")
    public ResponseEntity<String> addQuiz(@RequestBody Quiz quiz) {
        adminService.addQuiz(quiz);
        return ResponseEntity.ok("quiz is added");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@RequestBody QuizDTO quiz, @PathVariable Long id) {
        return adminService.update(quiz, id);
    }

    @GetMapping(value = "/getAllQuizzes", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<QuizDTO> getAllQuizzes() {
        return adminService.getAllQuizzes();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteQuiz(@PathVariable Long id) {
        adminService.deleteQuiz(id);
        return ResponseEntity.ok("Quiz is deleted");
    }

    @DeleteMapping("deleteQuestion/{id}")
    public ResponseEntity<String> deleteQuestion(@PathVariable Long id) {
        questionService.deleteQuestion(id);
        return ResponseEntity.ok("Question is removed");
    }

    @GetMapping("/getAllPlayers")
    public List<PlayerDTO> getAllPlayers() {
        List<Player> players = playerService.findAll();
        if (players == null || players.isEmpty()) {
            return List.of();
        }

        return players.stream()
                .map(r ->
                {
                    List<QuizScoreResultDTO> scores = (r.getQuizScoreResultList() == null)
                            ? new ArrayList<>()
                            : r.getQuizScoreResultList().stream()
                              .map(result -> QuizScoreResultDTO.builder()
                                             .id(result.getId())
                                             .quiz(result.getQuiz().getTitle())
                                             .bestScore(result.getBestScore())
                                             .build())
                              .collect(Collectors.toList());


                    return PlayerDTO.builder()
                            .id(r.getId())
                            .userName(r.getUserName() != null ? r.getUserName() : " ")
                            .email(r.getEmail() != null ? r.getEmail() : " ")
                            .score(r.getScore() != 0 ? r.getScore() : 0)
                            .quizScoreResultList(scores)
                            .build();
                }).collect(Collectors.toList());
    }


}
