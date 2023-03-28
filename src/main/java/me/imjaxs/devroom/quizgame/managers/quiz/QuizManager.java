package me.imjaxs.devroom.quizgame.managers.quiz;

import com.google.common.collect.Maps;
import me.imjaxs.devroom.quizgame.QuizGamePlugin;
import me.imjaxs.devroom.quizgame.configurations.files.ConfigFile;
import me.imjaxs.devroom.quizgame.objects.quiz.category.QCategory;
import me.imjaxs.devroom.quizgame.objects.quiz.QQuiz;

import java.util.Map;

public class QuizManager {
    private final QuizGamePlugin plugin;
    private final Map<String, QCategory> categories = Maps.newHashMap();
    private QQuiz quiz;

    public QuizManager(QuizGamePlugin plugin) {
        this.plugin = plugin;

        this.quiz = null;
        ConfigFile.QUIZ_CATEGORIES.forEach(category -> categories.put(category.getName().toLowerCase(), category));
    }

    public QQuiz getQuiz() {
        return quiz;
    }

    public void createQuiz(QCategory category) {
        this.quiz = new QQuiz(plugin, category);
    }

    public void deleteQuiz() {
        this.quiz = null;
    }

    public QCategory getCategory(String name) {
        return categories.get(name);
    }

    public Map<String, QCategory> getCategories() {
        return categories;
    }
}
