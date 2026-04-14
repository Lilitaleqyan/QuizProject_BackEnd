package com.example.demo.controller;

import com.example.demo.dto.PlayerDTO;
import com.example.demo.entity.Player;
import com.example.demo.entity.quizConstructor.Quiz;
import com.example.demo.service.AdminService;
import com.example.demo.service.PlayerService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "http://localhost:5050")
public class AdminController {
    private final AdminService adminService;
    private final PlayerService playerService;

    public AdminController(AdminService adminService, PlayerService playerService) {
        this.adminService = adminService;
        this.playerService = playerService;
    }

    @PostMapping("/addQuiz")
    public ResponseEntity<String> addQuiz(@RequestBody Quiz quiz) {

        adminService.addQuiz(quiz);
        return ResponseEntity.ok("quiz is added");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@RequestBody Quiz quiz, @PathVariable Long id) {
        return adminService.update(quiz, id);
    }

    @GetMapping(value = "/getAllQuizzes", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Quiz> getAllQuizzes() {
        return adminService.getAllQuizzes();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteQuiz(@PathVariable Long id) {
        adminService.deleteQuiz(id);
        return ResponseEntity.ok("Quiz is deleted");
    }

    @GetMapping("/getAllPlayers")
    public List<PlayerDTO> getAllPlayers() {
        List<Player> players = playerService.findAll();
        if (players == null || players.isEmpty()) {
            return List.of();
        }

        return players.stream()
                .map(r -> PlayerDTO.builder()
                        .id(r.getId())
                        .userName(r.getUserName() != null ? r.getUserName() : " ")
                        .email(r.getEmail() != null ? r.getEmail() : " ")
                        .score(r.getScore() != 0 ? r.getScore() : 0)
                        .quizScoreResultList(r.getQuizScoreResultList() != null ? r.getQuizScoreResultList() : new ArrayList<>())
                        .build()

                ).toList();
    }


}
