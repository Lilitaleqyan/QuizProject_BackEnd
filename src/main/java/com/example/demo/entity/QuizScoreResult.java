package com.example.demo.entity;

import com.example.demo.entity.quizConstructor.Quiz;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class QuizScoreResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonBackReference("player_result")
    private Player player;

    @ManyToOne
    @JsonBackReference
    private Quiz quiz;
    private Integer bestScore;
}
