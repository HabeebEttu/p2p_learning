package com.habeeb.p2plearn.services;

import com.habeeb.p2plearn.dto.*;
import com.habeeb.p2plearn.models.*;
import com.habeeb.p2plearn.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {

    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;
    private final QuizAttemptRepository quizAttemptRepository;
    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;

@Transactional
    @Override
    public QuizResponse createQuiz(QuizPost quizPost, Long userId) {
    User u = userRepository.findById(userId).orElseThrow(()->new RuntimeException("User not found"));
    Quiz quiz = new Quiz();
    quiz.setTitle(quizPost.title());
    quiz.setDescription(quizPost.description());
    quiz.setCategory(quizPost.category());
    quiz.setTimeLimit(quizPost.timeLimit());
    quiz.setPassingScore(quizPost.passingScore());
    quiz.setXpReward(quizPost.xpReward());
    quiz.setCreatedBy(u);

    for (QuestionPost qp : quizPost.questions()) {
        Question question = new Question();
        question.setQuiz(quiz);
        question.setQuestionText(qp.questionText());
        question.setType(qp.type());
        question.setPoints(qp.points());
        question.setCorrectAnswerIndex(qp.correctAnswerIndex());

        // Add options
        for (int i = 0; i < qp.options().size(); i++) {
            QuestionOption option = new QuestionOption();
            option.setQuestion(question);
            option.setOptionText(qp.options().get(i));
            option.setOptionIndex(i);
            question.getOptions().add(option);
        }

        quiz.getQuestions().add(question);
    }

    Quiz saved = quizRepository.save(quiz);
    return convertToResponse(saved, true);
    }
    private QuizResponse convertToResponse(Quiz quiz, boolean includeAnswers) {
        List<QuestionResponse> questions = quiz.getQuestions().stream()
                .map(q -> new QuestionResponse(
                        q.getId(),
                        q.getQuestionText(),
                        q.getType(),
                        q.getPoints(),
                        q.getOptions().stream()
                                .map(o -> new OptionResponse(o.getId(), o.getOptionText(), o.getOptionIndex()))
                                .collect(Collectors.toList()),
                        includeAnswers ? q.getCorrectAnswerIndex() : null
                ))
                .collect(Collectors.toList());

        return new QuizResponse(
                quiz.getId(),
                quiz.getTitle(),
                quiz.getDescription(),
                quiz.getCategory(),
                quiz.getTimeLimit(),
                quiz.getPassingScore(),
                quiz.getXpReward(),
                quiz.getQuestions().size(),
                quiz.getCreatedBy().getId(),
                quiz.getCreatedBy().getUsername(),
                quiz.getCreatedAt(),
                quiz.getUpdatedAt(),
                questions
        );
    }
    @Override
    public Page<QuizResponse> getAllQuizzes(int page, int size) {
        return null;
    }

    @Override
    public QuizResponse getQuizById(Long id, boolean includeAnswers) {
        return null;
    }

    @Override
    public void deleteQuiz(Long id) {

    }
}