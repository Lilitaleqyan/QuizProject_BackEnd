package com.example.demo.controller;

import com.example.demo.dto.PlayerDTO;
import com.example.demo.dto.QuizDTO;
import com.example.demo.entity.quizConstructor.Quiz;
import com.example.demo.service.AdminService;
import com.example.demo.service.PlayerService;
import com.example.demo.service.QuestionService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        return playerService.getAllPlayers();
    }
}
