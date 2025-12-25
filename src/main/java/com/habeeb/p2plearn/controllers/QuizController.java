package com.habeeb.p2plearn.controllers;

import com.habeeb.p2plearn.dto.*;
import com.habeeb.p2plearn.models.QuestionCategory;
import com.habeeb.p2plearn.models.QuizAttempt;
import com.habeeb.p2plearn.services.QuizService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quizzes")
public class QuizController {

    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @PostMapping
    public ResponseEntity<QuizResponseDto> createQuiz(@RequestBody QuizCreationDto dto) {
        return ResponseEntity.ok(quizService.createQuiz(dto));
    }

    @GetMapping
    public ResponseEntity<List<QuizResponseDto>> getAllQuizzes() {
        return ResponseEntity.ok(quizService.getAllQuizzes());
    }

    @GetMapping("/{quizId}")
    public ResponseEntity<QuizDetailDto> getQuizById(@PathVariable Long quizId) {
        return ResponseEntity.ok(quizService.getQuizById(quizId));
    }

    @PutMapping("/{quizId}")
    public ResponseEntity<QuizResponseDto> updateQuiz(
            @PathVariable Long quizId,
            @RequestBody QuizCreationDto dto) {
        return ResponseEntity.ok(quizService.updateQuiz(quizId, dto));
    }

    @DeleteMapping("/{quizId}")
    public ResponseEntity<String> deleteQuiz(@PathVariable Long quizId) {
        quizService.deleteQuiz(quizId);
        return ResponseEntity.ok("Quiz deleted successfully");
    }

    @PostMapping("/submit")
    public ResponseEntity<QuizResultDto> submitQuiz(
            @RequestParam Long userId,
            @RequestBody QuizSubmissionDto submission) {
        return ResponseEntity.ok(quizService.submitQuiz(userId, submission));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<QuizResponseDto>> getQuizzesByCategory(
            @PathVariable QuestionCategory category) {
        return ResponseEntity.ok(quizService.getQuizzesByCategory(category));
    }

    @GetMapping("/user/{userId}/attempts")
    public ResponseEntity<List<QuizAttempt>> getUserAttempts(@PathVariable Long userId) {
        return ResponseEntity.ok(quizService.getUserAttempts(userId));
    }
}