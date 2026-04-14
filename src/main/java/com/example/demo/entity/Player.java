package com.example.demo.entity;

import com.example.demo.entity.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userName;
    private String password;
    private String email;
    private Integer score;
    @Enumerated(value = EnumType.STRING)
    private Role role;

    private Boolean musicEnabled = true;
    private Boolean soundEnabled = true;
    @OneToMany(mappedBy = "player", fetch = FetchType.EAGER)
    @JsonIgnore
    @JsonManagedReference
    List<QuizScoreResult> quizScoreResultList = new ArrayList<>();

}
