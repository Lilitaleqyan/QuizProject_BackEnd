package com.example.demo.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionDTO {
    Long id;
    String content;
    String correctAnswer;
    String wrongAnswer1;
    String wrongAnswer2;
    String wrongAnswer3;
}
