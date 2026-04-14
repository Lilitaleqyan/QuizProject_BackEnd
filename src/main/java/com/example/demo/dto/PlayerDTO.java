package com.example.demo.dto;

import com.example.demo.entity.QuizScoreResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class PlayerDTO {
    private Long id;
    private String userName;
    private String email;
    private int score;
    @Builder.Default
    private List<QuizScoreResult> quizScoreResultList = new ArrayList<>();
}
