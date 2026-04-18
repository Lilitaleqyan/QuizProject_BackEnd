package com.example.demo.dto;

import com.example.demo.entity.enums.Level;
import com.example.demo.entity.enums.Topic;
import lombok.*;

import java.util.Set;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuizDTO {
    Long id;
    String title;
    Level level;
    Topic topic;
    Set<QuestionDTO> question;
}