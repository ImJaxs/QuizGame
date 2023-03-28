package me.imjaxs.devroom.quizgame.objects.quiz.question;

import java.util.List;

public class QQuestion {
    private final String question;
    private final List<String> answers;

    public QQuestion(String question, List<String> answers) {
        this.question = question;
        this.answers = answers;
    }

    public String getQuestion() {
        return question;
    }

    public List<String> getAnswers() {
        return answers;
    }
}
