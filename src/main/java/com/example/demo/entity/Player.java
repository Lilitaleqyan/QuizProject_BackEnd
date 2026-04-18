package com.example.demo.entity;

import com.example.demo.entity.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
    @Size(min = 8, message = "Առնվազն 8 նիշ")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&.]).{8,}$",
            message = "Գաղտնաբառը պետք է պարունակի մեծատառ, փոքրատառ, թիվ և սիմվոլ"
    )
    private String password;

    @Pattern(
            regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$",
            message = "Սխալ email ֆորմատ"
    )
    @NotBlank
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
