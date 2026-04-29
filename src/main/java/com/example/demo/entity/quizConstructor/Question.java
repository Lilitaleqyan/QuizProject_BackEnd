package com.example.demo.entity.quizConstructor;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    Set<OptionAnswer> optionAnswerSet = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Question)) return false;
        return id != null && id.equals(((Question) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
