package com.habeeb.p2plearn.controllers;

import com.habeeb.p2plearn.dto.QuizResponse;
import com.habeeb.p2plearn.dto.QuizResultResponse;
import com.habeeb.p2plearn.dto.QuizSubmission;
import com.habeeb.p2plearn.models.QuestionCategory;
import com.habeeb.p2plearn.models.User;
import com.habeeb.p2plearn.services.AuthServiceImpl;
import com.habeeb.p2plearn.services.QuizServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/quizzes")
public class QuizController{
    private final AuthServiceImpl authService;
    private final QuizServiceImpl quizService;

    @GetMapping()
    public ResponseEntity<?> getAllQuizzes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false)QuestionCategory category
            ){
        Page<QuizResponse> quizPage;
        if(category != null){
            quizPage = quizService.getQuizzesByCategory(category, page, size);
        }else{
            quizPage = quizService.getAllQuizzes(page, size);
        }
        Map<String , Object> response = new HashMap<>();
        response.put("quizzes",quizPage.getContent());
        response.put("totalPages",quizPage.getTotalPages());
        response.put("totalItems",quizPage.getTotalElements());
        response.put("currentPage",quizPage.getNumber());
        return ResponseEntity.ok(response);

    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getQuiz(@PathVariable Long id) {
        try {
            QuizResponse response = quizService.getQuizById(id, false);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }
    @PostMapping("/{id}/submit")
    public ResponseEntity<?> submitQuiz(
            @PathVariable Long id,
            @RequestBody QuizSubmission submission
    ) {
        User user = authService.getCurrentUser();

        try {
            QuizResultResponse result = quizService.submitQuiz(id, user.getId(), submission);
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }
    @GetMapping("/categories/get")
    public ResponseEntity<?> getCategories(){
        return ResponseEntity.ok(QuestionCategory.values());
    }
}