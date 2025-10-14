package com.habeeb.p2plearn.services;

import com.habeeb.p2plearn.dto.QuestionCreationRequestDto;
import com.habeeb.p2plearn.dto.QuizDto;
import com.habeeb.p2plearn.dto.QuizRequest;
import com.habeeb.p2plearn.dto.QuizSubmission;
import com.habeeb.p2plearn.models.Level;
import com.habeeb.p2plearn.models.Profile;
import com.habeeb.p2plearn.models.Question;
import com.habeeb.p2plearn.repositories.ProfileRepository;
import com.habeeb.p2plearn.repositories.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class QuizServiceImpl implements QuizService {

    private final QuestionRepository questionRepository;
    private final ProfileRepository profileRepository;

    public QuizServiceImpl(QuestionRepository questionRepository,ProfileRepository profileRepository) {
        this.questionRepository = questionRepository;
        this.profileRepository = profileRepository;
    }

    private QuestionCreationRequestDto convertTODto(Question q){
        return new QuestionCreationRequestDto(q.getQuestionText(),q.getOptions(),q.getAnswer(),q.getCategory(),q.getDifficulty());
    }
    @Override
    public QuizDto createQuiz(QuizRequest quizRequest) {
        List<Question> questions = questionRepository.findByCategoryAndDifficulty(
                quizRequest.getCategory(), quizRequest.getDifficulty());

        Collections.shuffle(questions);
        questions = questions.stream().limit(quizRequest.getNumQuestions()).toList();
        List<QuestionCreationRequestDto> selectedQuestions = questions.stream()
                .limit(quizRequest.getNumQuestions())
                .map(this::convertTODto)
                .toList();
        QuizDto quiz = new QuizDto(selectedQuestions,quizRequest.getCategory());
        return quiz;

    }

    @Override
    public Integer submitQuiz(Long userId, QuizSubmission quizSubmission) {
        Profile profile = profileRepository.findByUserId(userId).orElseThrow(() -> new RuntimeException("Invalid userId"));
        Map<String, Integer> markedQuiz = markQuiz(quizSubmission);
        int xpGained = markedQuiz.get("xp_gained");
        int score = markedQuiz.get("score");
        profile.setXp(profile.getXp() + xpGained);
        profileRepository.save(profile);
        return new Integer(score);
    }
    private HashMap<String,Integer> markQuiz(QuizSubmission quizSubmission){
        List<QuestionCreationRequestDto> questions = quizSubmission.questions();
        List<Character> answers = quizSubmission.answers();
        int i = 0;
        int score = 0;
        int xp = 0;
        for(QuestionCreationRequestDto q : questions){
            if(q.answer() == answers.get(i)){
                score++;
                switch(q.difficulty()){
                    case Level.BEGINNER:
                        xp+=5;
                        break;
                    case Level.INTERMEDIATE:
                        xp+=10;
                        break;
                    case Level.ADVANCED:
                        xp+=20;
                        break;
                    case Level.EXPERT:
                        xp+=30;
                        break;
                }

            }
            i++;
        }
        int finalXp = xp;
        int finalScore = score;
        return new HashMap<>(){
            {
                this.put("xp_gained", finalXp);
                this.put("score",finalScore);
            }
        };

    }
}
