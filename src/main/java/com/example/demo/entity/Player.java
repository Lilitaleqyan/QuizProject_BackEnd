package com.example.demo.entity;

import com.example.demo.entity.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
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
    @OneToMany(mappedBy = "player")
    @JsonIgnore
    @JsonManagedReference
    List<QuizScoreResult> quizScoreResultList = new ArrayList<>();

}
