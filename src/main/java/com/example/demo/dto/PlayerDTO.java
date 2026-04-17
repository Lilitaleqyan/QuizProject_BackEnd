package com.example.demo.dto;

import com.example.demo.entity.QuizScoreResult;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class PlayerDTO {
    private Long id;
    private String userName;
    private String email;
    private int score;
    @Builder.Default
    private List<QuizScoreResultDTO> quizScoreResultList = new ArrayList<>();
}
