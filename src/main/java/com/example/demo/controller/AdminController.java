package com.example.demo.controller;

import com.example.demo.entity.quizConstructor.Quiz;
import com.example.demo.service.AdminService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private  final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }
    @PostMapping("/addQuiz")
    public ResponseEntity<String> addQuiz(@RequestBody Quiz quiz) {

        adminService.addQuiz(quiz);
        return ResponseEntity.ok("quiz is added");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update( @RequestBody Quiz quiz, @PathVariable Long id) {
       return adminService.update(quiz, id );
    }

    @GetMapping(value = "/getAllQuizzes", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Quiz> getAllQuizzes() {
        return adminService.getAllQuizzes();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteQuiz(@PathVariable Long id) {
         adminService.deleteQuiz(id);
         return ResponseEntity.ok("Quiz is deleted");
    }

}
