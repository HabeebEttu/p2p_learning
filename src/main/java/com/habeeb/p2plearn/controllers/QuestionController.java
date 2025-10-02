package com.habeeb.p2plearn.controllers;

import com.habeeb.p2plearn.dto.QuestionDto;
import com.habeeb.p2plearn.services.QuestionServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/question")
public class QuestionController {
    private final QuestionServiceImpl questionService;

    public QuestionController(QuestionServiceImpl questionService) {
        this.questionService = questionService;
    }

    @PostMapping
    public ResponseEntity<String> createQuestion(@RequestBody QuestionDto question){
        questionService.createQuestion(question);
        return ResponseEntity.ok("Question inserted successfully");
    }
    @PostMapping("/bulk")
    public ResponseEntity<String> createQuestions(@RequestBody List<QuestionDto> questions){
        questionService.createQuestions(questions);
        return ResponseEntity.ok("Questions inserted successfully");
    }
    @PutMapping("/{questionId}")
    public ResponseEntity<String> updateQuestion(@PathVariable Long questionId,@RequestBody QuestionDto question){
        questionService.updateQuestion(question,questionId);
        return ResponseEntity.ok("question updated successfully");
    }
    @DeleteMapping("/{questionId}")
    public ResponseEntity<String> deleteQuestion(@PathVariable Long questionId){
        questionService.deleteQuestion(questionId);
        return ResponseEntity.ok("question deleted successfully");
    }
    @GetMapping
    public ResponseEntity<List<QuestionDto>> getAllQuestions(){
        return ResponseEntity.ok(questionService.getAllQuestions());
    }

}
