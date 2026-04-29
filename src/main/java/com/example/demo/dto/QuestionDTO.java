package com.example.demo.dto;

import com.example.demo.entity.quizConstructor.OptionAnswer;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionDTO {
    Long id;
    String content;
    Set<OptionAnswerDTO> optionAnswerSet = new HashSet<>();
}
