package com.example.demo.controller;

import com.example.demo.dto.PlayerDTO;
import com.example.demo.dto.QuizScoreResultDTO;
import com.example.demo.entity.Player;
import com.example.demo.service.PlayerService;
import com.example.demo.service.QuizScoreResultService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/player")
@CrossOrigin(origins = "http://localhost:5050")
public class PlayerController {
    private final PlayerService playerService;
    private final QuizScoreResultService quizScoreResultService;

    public PlayerController(PlayerService playerService, QuizScoreResultService quizScoreResultService) {
        this.playerService = playerService;
        this.quizScoreResultService = quizScoreResultService;
    }

    @PutMapping("/updateScore/{quizId}")
    public ResponseEntity<PlayerDTO> updateScore(@RequestBody Player player, @PathVariable Long quizId) {
        quizScoreResultService.saveQuizResult(player.getId(), quizId, player.getScore());
        Player player1 = playerService.findByIdWithResults(player.getId()).orElseThrow(() -> new RuntimeException("Player not found"));
        PlayerDTO playerDTO = PlayerDTO.builder()
                .id(player1.getId())
                .userName(player1.getUserName())
                .score(player1.getScore())
                .quizScoreResultList(player1.getQuizScoreResultList().stream()
                        .map(res -> QuizScoreResultDTO.builder()
                                .id(res.getId())
                                .quiz(res.getQuiz().getTitle())
                                .bestScore(res.getBestScore())
                                .build())
                        .collect(Collectors.toList()))
                .build();
        return ResponseEntity.ok(playerDTO);
    }

    @GetMapping("/getPlayerById/{id}")
    public ResponseEntity<PlayerDTO> getPlayerById(@PathVariable Long id) {
        Player player = playerService.findByIdWithResults(id)
                .orElseThrow();
        PlayerDTO playerDto = PlayerDTO.builder()
                .id(player.getId())
                .email(player.getEmail())
                .score(player.getScore())
                .userName(player.getUserName())
                .quizScoreResultList(player.getQuizScoreResultList().stream().
                        map(q -> QuizScoreResultDTO.builder()
                                .id(q.getId())
                                .quiz(q.getQuiz().getTitle())
                                .bestScore(q.getBestScore())
                                .build()

                        ).collect(Collectors.toList())).build();

        return ResponseEntity.ok(playerDto);
    }

}
