package com.example.demo.entity;

import com.example.demo.entity.quizConstructor.Quiz;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class QuizScoreResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonBackReference
    private Player player;

    @ManyToOne
    private Quiz quiz;
    private Integer bestScore;
}
