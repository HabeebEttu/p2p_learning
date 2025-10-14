package com.habeeb.p2plearn.services;

import com.habeeb.p2plearn.dto.QuestionCreationRequestDto;
import com.habeeb.p2plearn.models.Question;
import com.habeeb.p2plearn.repositories.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class QuestionServiceImpl implements QuestionService {
    private final QuestionRepository questionRepository;

    public QuestionServiceImpl(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }
    private QuestionCreationRequestDto convertTODto(Question q){
        QuestionCreationRequestDto dto = new QuestionCreationRequestDto(q.getQuestionText(),q.getOptions(),q.getAnswer(),q.getCategory(),q.getDifficulty());
        return dto;
    }
    @Override
    public String createQuestion(QuestionCreationRequestDto question) {
        Question q = new Question();
        q.setQuestionText(question.questionText());
        q.setCategory(question.category());
        q.setOptions(question.options());
        q.setDifficulty(question.difficulty());
        q.setAnswer(question.answer());
        if (validateOptions(q.getOptions()) && q.getOptions().containsKey(q.getAnswer())) {
            questionRepository.save(q);
        } else {
            throw new IllegalArgumentException("Each question must have exactly 4 options (A, B, C, D) and the answer must be one of the options.");
        }
        return "";
    }

    @Override
    public String updateQuestion(QuestionCreationRequestDto question, Long questionId) {

        Question q = questionRepository.findById(questionId).orElseThrow(()->new RuntimeException("Question not found"));
        q.setQuestionText(question.questionText());
        q.setCategory(question.category());
        q.setOptions(question.options());
        q.setDifficulty(question.difficulty());
        q.setAnswer(question.answer());
        if(validateOptions(q.getOptions()) && q.getOptions().containsKey(q.getAnswer())){
            questionRepository.save(q);}
        else {
            throw new IllegalArgumentException("Each question must have exactly 4 options (A, B, C, D) and the answer must be one of the options.");
        }
        return "";
    }




    @Override
    public String deleteQuestion(Long questionId) {
        questionRepository.deleteById(questionId);
        return "";
    }
    @Override
    public List<QuestionCreationRequestDto> getAllQuestions() {
        return questionRepository.findAll().stream().map(this::convertTODto).toList();
    }
    private boolean validateOptions(Map<Character, String> options) {
        if (options == null || options.size() != 4) {
            throw new IllegalArgumentException("Each question must have exactly 4 options (A, B, C, D).");
        }

        if (!options.keySet().containsAll(List.of('A', 'B', 'C', 'D'))) {
            throw new IllegalArgumentException("Options must include keys A, B, C, and D.");
        }
        return true;
    }

    @Override
    public void createQuestions(List<QuestionCreationRequestDto> questions) {
        for(QuestionCreationRequestDto question : questions){

            Question q = new Question();
            q.setQuestionText(question.questionText());
            q.setCategory(question.category());
            q.setOptions(question.options());
            q.setDifficulty(question.difficulty());
            q.setAnswer(question.answer());
            if(validateOptions(q.getOptions()) && q.getOptions().containsKey(q.getAnswer())){
            questionRepository.save(q);}
            else {
                throw new IllegalArgumentException("Each question must have exactly 4 options (A, B, C, D) and the answer must be one of the options.");
            }
        }
    }
}
