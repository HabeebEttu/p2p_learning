package com.habeeb.p2plearn.services;

import com.habeeb.p2plearn.dto.*;
import com.habeeb.p2plearn.models.*;
import com.habeeb.p2plearn.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
        Pageable pageable = PageRequest.of(page,size, Sort.by("createdAt").descending());

        return quizRepository.findAll(pageable).map(q -> convertToResponse(q, false));
    }

    @Override
    public QuizResponse getQuizById(Long id, boolean includeAnswers) {
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Quiz not found"));
        return convertToResponse(quiz, includeAnswers);
    }

    @Transactional
    @Override
    public void deleteQuiz(Long id) {
        quizRepository.deleteById(id);
    }

    @Override
    @Transactional
    public QuizResponse updateQuiz(Long id, QuizPost quizPost) {
        return null;
    }

    @Override
    public Map<String, Object> getQuizStatistics(Long id) {
        return Map.of();
    }

    @Override
    public Page<QuizResponse> getQuizzesByCategory(QuestionCategory category, int page, int size) {
        return null;
    }

    @Override
    public Page<QuizResponse> getAllQuizzesForUsers(int page, int size) {
        return null;
    }

    @Override
    public QuizResultResponse submitQuiz(Long quizId, Long userId, QuizSubmission submission) {
    Quiz quiz = quizRepository.findById(quizId).orElseThrow(()-> new RuntimeException("quiz not found"));
    User user = userRepository.findById(userId).orElseThrow(()->new RuntimeException("User not found with the id of "+userId));
        if (!submission.quizId().equals(quizId)) {
            throw new RuntimeException("Quiz ID mismatch");
        }
        int totalScore = 0;
        int maxScore = 0;
        int correctAnswers = 0;
        List<QuestionResultResponse> questionResults = new java.util.ArrayList<>();
        for (Question question : quiz.getQuestions()) {
            Integer selectedAnswer = submission.answers().get(question.getId());
            Integer correctAnswer = question.getCorrectAnswerIndex();

            boolean isCorrect = selectedAnswer != null &&
                    selectedAnswer.equals(correctAnswer);
            int pointsAwarded = isCorrect ? question.getPoints() : 0;

            totalScore += pointsAwarded;
            maxScore += question.getPoints();
            if (isCorrect) correctAnswers++;

            questionResults.add(new QuestionResultResponse(
                    question.getId(),
                    question.getQuestionText(),
                    selectedAnswer,
                    correctAnswer,
                    isCorrect,
                    pointsAwarded,
                    question.getPoints()
            ));
        }
        int percentageScore = maxScore>0?(totalScore*100)/maxScore:0;
        boolean passed = percentageScore>=quiz.getPassingScore();
        int xpAwarded = 0;
        if (passed) {
            xpAwarded = quiz.getXpReward();
            Profile profile = profileRepository.findByUserId(userId)
                    .orElseThrow(() -> new RuntimeException("Profile not found"));
            profile.setXp(profile.getXp() + xpAwarded);
            profileRepository.save(profile);
        }
        QuizAttempt attempt = new QuizAttempt();
        attempt.setQuiz(quiz);
        attempt.setUser(user);
        attempt.setScore(totalScore);
        attempt.setMaxScore(maxScore);
        attempt.setPercentageScore(percentageScore);
        attempt.setPassed(passed);
        attempt.setXpAwarded(xpAwarded);
        attempt.setTimeTaken(0);

        QuizAttempt savedAttempt = quizAttemptRepository.save(attempt);

        return new QuizResultResponse(
                savedAttempt.getId(),
                quiz.getId(),
                quiz.getTitle(),
                totalScore,
                maxScore,
                percentageScore,
                passed,
                xpAwarded,
                correctAnswers,
                quiz.getQuestions().size(),
                0,
                questionResults
        );
    }
}