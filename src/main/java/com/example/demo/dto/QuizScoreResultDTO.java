package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class QuizScoreResultDTO {
    private Long id;
    private String quiz;
    private Integer bestScore;
}
