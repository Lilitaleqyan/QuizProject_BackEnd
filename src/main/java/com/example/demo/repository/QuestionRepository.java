package com.example.demo.repository;

import com.example.demo.entity.quizConstructor.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Quiz, Long> {
}
