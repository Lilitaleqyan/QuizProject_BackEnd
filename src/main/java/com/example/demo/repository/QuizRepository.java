package com.example.demo.repository;

import com.example.demo.entity.quizConstructor.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {

@Query("SELECT  q FROM Quiz  q LEFT JOIN FETCH q.question")
List<Quiz> findAllWithQuestion();
}
