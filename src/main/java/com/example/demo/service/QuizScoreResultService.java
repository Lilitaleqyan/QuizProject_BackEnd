package com.example.demo.service;

import com.example.demo.entity.Player;
import com.example.demo.entity.QuizScoreResult;
import com.example.demo.repository.PlayerRepository;
import com.example.demo.repository.QuizRepository;
import com.example.demo.repository.QuizScoreResultRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class QuizScoreResultService {
    private final QuizScoreResultRepository quizScoreResultRepository;
    private final PlayerService playerService;
    private final PlayerRepository playerRepository;
    private final QuizRepository quizRepository;

    public QuizScoreResultService(QuizScoreResultRepository quizScoreResultRepository, PlayerService playerService, PlayerRepository playerRepository, QuizRepository quizRepository) {
        this.quizScoreResultRepository = quizScoreResultRepository;
        this.playerService = playerService;
        this.playerRepository = playerRepository;
        this.quizRepository = quizRepository;
    }

    @Transactional
    public void saveQuizResult(Long playerId, Long quizId, Integer currentScore) {
        QuizScoreResult quizScoreResult = quizScoreResultRepository.findByPlayerIdAndQuizId(playerId, quizId).orElseGet(
                () -> {
                    QuizScoreResult newQuizScoreResult = new QuizScoreResult();
                    newQuizScoreResult.setPlayer(playerRepository.findById(playerId).get());
                    newQuizScoreResult.setQuiz(quizRepository.findById(quizId).get());
                    newQuizScoreResult.setBestScore(0);
                    return  newQuizScoreResult;
                }
        );
        if (currentScore > quizScoreResult.getBestScore()) {
            Integer difference = currentScore - quizScoreResult.getBestScore();
            quizScoreResult.setBestScore(currentScore);
            quizScoreResultRepository.save(quizScoreResult);

            Player player = playerService.findById(playerId).orElseThrow();
            player.setScore(player.getScore() + difference);
            playerRepository.save(player);
        }
    }
}
