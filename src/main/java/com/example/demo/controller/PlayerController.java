package com.example.demo.controller;

import com.example.demo.entity.Player;
import com.example.demo.entity.QuizScoreResult;
import com.example.demo.service.PlayerService;
import com.example.demo.service.QuizScoreResultService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Player> updateScore(@RequestBody  Player player, @PathVariable Long quizId ) {
        quizScoreResultService.saveQuizResult(player.getId(), quizId,player.getScore());
        Player player1 = playerService.findById(player.getId()).orElseThrow(() -> new RuntimeException("Player not found"));
        return ResponseEntity.ok(player1);
    }


}
