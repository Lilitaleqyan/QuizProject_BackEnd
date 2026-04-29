package com.example.demo.service;

import com.example.demo.dto.PlayerDTO;
import com.example.demo.dto.QuizScoreResultDTO;
import com.example.demo.entity.Player;
import com.example.demo.repository.PlayerRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlayerService {
    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }


    public boolean existsByUserName(String userName) {
        return playerRepository.existsPlayerByUserName(userName);
    }

    public boolean existByEmail(String email) {
        return playerRepository.existsPlayerByEmail(email);
    }

    public Player save(Player player) {
        return playerRepository.save(player);
    }

    public List<PlayerDTO> getAllPlayers() {
        return playerRepository.findAllWithQuizScoreResultList().stream()
                .map(this::convertDTO)
                .collect(Collectors.toList());

    }

    private PlayerDTO convertDTO(Player player) {
        List<QuizScoreResultDTO> scores = Optional.ofNullable(player.getQuizScoreResultList())
                .orElse(Collections.emptyList())
                .stream()
                .map(result -> QuizScoreResultDTO.builder()
                        .id(result.getId())
                        .quiz(result.getQuiz().getTitle())
                        .bestScore(result.getBestScore())
                        .build())
                .toList();
        return PlayerDTO.builder()
                .id(player.getId())
                .userName(player.getUserName())
                .email(player.getEmail())
                .score(player.getScore())
                .quizScoreResultList(scores)
                .build();


    }

    public Optional<Player> findByUserName(String userName) {
        return playerRepository.findByUserName(userName);
    }

    public Optional<Player> findById(Long id) {
        return playerRepository.findById(id);
    }

    public Optional<Player> findByIdWithResults(Long id) {
        return playerRepository.findByIdWithResults(id);
    }


}
