package com.habeeb.p2plearn.controllers;

import com.habeeb.p2plearn.dto.QuizDto;
import com.habeeb.p2plearn.dto.QuizRequest;
import com.habeeb.p2plearn.services.QuizService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/quiz")
public class QuizController {

    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @PostMapping
    public ResponseEntity<QuizDto> createQuiz(@RequestBody QuizRequest quizRequest) {
        return ResponseEntity.ok(quizService.createQuiz(quizRequest));
    }


}