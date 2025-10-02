package com.habeeb.p2plearn.services;

import com.habeeb.p2plearn.dto.QuestionDto;
import com.habeeb.p2plearn.dto.QuizDto;
import com.habeeb.p2plearn.dto.QuizRequest;
import com.habeeb.p2plearn.models.Question;
import com.habeeb.p2plearn.repositories.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuizServiceImpl implements QuizService {

    private final QuestionRepository questionRepository;

    public QuizServiceImpl(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    private QuestionDto convertTODto(Question q){
        return new QuestionDto(q.getQuestionText(),q.getOptions(),q.getAnswer(),q.getCategory(),q.getDifficulty());
    }
    @Override
    public QuizDto createQuiz(QuizRequest quizRequest) {
        List<Question> questions = questionRepository.findByCategoryAndDifficulty(
                quizRequest.getCategory(), quizRequest.getDifficulty());

        Collections.shuffle(questions);
        questions = questions.stream().limit(quizRequest.getNumQuestions()).toList();
        List<QuestionDto> selectedQuestions = questions.stream()
                .limit(quizRequest.getNumQuestions())
                .map(this::convertTODto)
                .toList();
        QuizDto quiz = new QuizDto(selectedQuestions,quizRequest.getCategory());
        return quiz;

    }
}
