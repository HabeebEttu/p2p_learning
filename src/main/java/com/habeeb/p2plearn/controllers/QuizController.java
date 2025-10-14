package com.habeeb.p2plearn.controllers;

import com.habeeb.p2plearn.dto.QuizDto;
import com.habeeb.p2plearn.dto.QuizRequest;
import com.habeeb.p2plearn.dto.QuizSubmission;
import com.habeeb.p2plearn.services.ProfileService;
import com.habeeb.p2plearn.services.QuizService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/quiz")
public class QuizController {

    private final QuizService quizService;

    public QuizController(QuizService quizService,ProfileService profileService) {
        this.quizService = quizService;
    }

    @PostMapping
    public ResponseEntity<QuizDto> createQuiz(@RequestBody QuizRequest quizRequest) {
        return ResponseEntity.ok(quizService.createQuiz(quizRequest));
    }
    @PostMapping("/submit/{userId}")
    public ResponseEntity<Integer> submitQuiz(@PathVariable Long userId, @RequestBody QuizSubmission quizSubmission){
        return ResponseEntity.ok(quizService.submitQuiz(userId,quizSubmission));
    }


}