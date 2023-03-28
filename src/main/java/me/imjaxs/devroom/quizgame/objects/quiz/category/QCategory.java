package me.imjaxs.devroom.quizgame.objects.quiz.category;

import com.google.common.collect.Lists;
import me.imjaxs.devroom.quizgame.objects.quiz.question.QQuestion;

import java.util.List;

public class QCategory {
    private final String name;
    private List<QQuestion> questions;

    public QCategory(String name) {
        this.name = name;
        this.questions = Lists.newArrayList();
    }

    public String getName() {
        return name;
    }

    public List<QQuestion> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QQuestion> questions) {
        this.questions = questions;
    }
}
