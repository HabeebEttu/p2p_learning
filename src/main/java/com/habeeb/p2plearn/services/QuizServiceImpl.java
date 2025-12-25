package com.habeeb.p2plearn.services;

import com.habeeb.p2plearn.dto.*;
import com.habeeb.p2plearn.models.*;
import com.habeeb.p2plearn.repositories.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class QuizServiceImpl implements QuizService {

    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;
    private final QuizAttemptRepository quizAttemptRepository;
    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;

    public QuizServiceImpl(
            QuizRepository quizRepository,
            QuestionRepository questionRepository,
            QuizAttemptRepository quizAttemptRepository,
            ProfileRepository profileRepository,
            UserRepository userRepository) {
        this.quizRepository = quizRepository;
        this.questionRepository = questionRepository;
        this.quizAttemptRepository = quizAttemptRepository;
        this.profileRepository = profileRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public QuizResponseDto createQuiz(QuizCreationDto dto) {
        List<Question> questions = questionRepository.findAllById(dto.questionIds());

        if (questions.size() != dto.questionIds().size()) {
            throw new RuntimeException("Some questions were not found");
        }

        Quiz quiz = Quiz.builder()
                .title(dto.title())
                .description(dto.description())
                .category(dto.category())
                .difficulty(dto.difficulty())
                .xpReward(dto.xpReward())
                .timeLimit(dto.timeLimit())
                .questions(questions)
                .build();

        quiz = quizRepository.save(quiz);

        return convertToResponseDto(quiz);
    }

    @Override
    public List<QuizResponseDto> getAllQuizzes() {
        return quizRepository.findAll().stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public QuizDetailDto getQuizById(Long quizId) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new RuntimeException("Quiz not found"));

        return convertToDetailDto(quiz);
    }

    @Override
    @Transactional
    public QuizResponseDto updateQuiz(Long quizId, QuizCreationDto dto) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new RuntimeException("Quiz not found"));

        List<Question> questions = questionRepository.findAllById(dto.questionIds());

        quiz.setTitle(dto.title());
        quiz.setDescription(dto.description());
        quiz.setCategory(dto.category());
        quiz.setDifficulty(dto.difficulty());
        quiz.setXpReward(dto.xpReward());
        quiz.setTimeLimit(dto.timeLimit());
        quiz.setQuestions(questions);

        quiz = quizRepository.save(quiz);

        return convertToResponseDto(quiz);
    }

    @Override
    @Transactional
    public void deleteQuiz(Long quizId) {
        if (!quizRepository.existsById(quizId)) {
            throw new RuntimeException("Quiz not found");
        }
        quizRepository.deleteById(quizId);
    }

    @Override
    @Transactional
    public QuizResultDto submitQuiz(Long userId, QuizSubmissionDto submission) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Profile profile = profileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Profile not found"));

        Quiz quiz = quizRepository.findById(submission.quizId())
                .orElseThrow(() -> new RuntimeException("Quiz not found"));

        // Calculate score
        int score = 0;
        int xpEarned = 0;

        for (Question question : quiz.getQuestions()) {
            Character userAnswer = submission.answers().get(question.getId());
            if (userAnswer != null && userAnswer.equals(question.getAnswer())) {
                score++;
                xpEarned += calculateXpForQuestion(question.getDifficulty());
            }
        }

        int totalQuestions = quiz.getQuestions().size();
        double percentage = (score * 100.0) / totalQuestions;
        boolean passed = percentage >= 70; // 70% pass rate

        // If passed, award bonus XP
        if (passed) {
            xpEarned += quiz.getXpReward();
        }

        // Save attempt
        QuizAttempt attempt = QuizAttempt.builder()
                .user(user)
                .quiz(quiz)
                .userAnswers(submission.answers())
                .score(score)
                .totalQuestions(totalQuestions)
                .xpEarned(xpEarned)
                .passed(passed)
                .build();

        quizAttemptRepository.save(attempt);

        // Update user XP
        profile.setXp(profile.getXp() + xpEarned);
        profileRepository.save(profile);

        return new QuizResultDto(score, totalQuestions, xpEarned, passed, percentage);
    }

    @Override
    public List<QuizResponseDto> getQuizzesByCategory(QuestionCategory category) {
        return quizRepository.findByCategory(category).stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<QuizAttempt> getUserAttempts(Long userId) {
        return quizAttemptRepository.findByUserId(userId);
    }

    private int calculateXpForQuestion(Level difficulty) {
        return switch (difficulty) {
            case BEGINNER -> 5;
            case INTERMEDIATE -> 10;
            case ADVANCED -> 20;
            case EXPERT -> 30;
        };
    }

    private QuizResponseDto convertToResponseDto(Quiz quiz) {
        return new QuizResponseDto(
                quiz.getId(),
                quiz.getTitle(),
                quiz.getDescription(),
                quiz.getCategory(),
                quiz.getDifficulty(),
                quiz.getXpReward(),
                quiz.getTimeLimit(),
                quiz.getQuestions().size(),
                quiz.getCreatedAt()
        );
    }

    private QuizDetailDto convertToDetailDto(Quiz quiz) {
        List<QuestionCreationRequestDto> questions = quiz.getQuestions().stream()
                .map(q -> new QuestionCreationRequestDto(
                        q.getQuestionText(),
                        q.getOptions(),
                        q.getAnswer(),
                        q.getCategory(),
                        q.getDifficulty()
                ))
                .collect(Collectors.toList());

        return new QuizDetailDto(
                quiz.getId(),
                quiz.getTitle(),
                quiz.getDescription(),
                quiz.getXpReward(),
                quiz.getTimeLimit(),
                questions
        );
    }
}