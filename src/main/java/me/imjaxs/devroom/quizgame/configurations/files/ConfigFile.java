package me.imjaxs.devroom.quizgame.configurations.files;

import com.glyart.mystral.database.Credentials;
import me.imjaxs.devroom.quizgame.QuizGamePlugin;
import me.imjaxs.devroom.quizgame.objects.quiz.category.QCategory;
import me.imjaxs.devroom.quizgame.objects.quiz.question.QQuestion;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("DataFlowIssue")
public class ConfigFile {
    private static final FileConfiguration config = QuizGamePlugin.get().getConfig();

    // GENERALS
    public static final boolean REMOVE_OTHER_MESSAGES = config.getBoolean("games-settings.generals.remove-other-messages");
    public static final int WAITING_QUIZ_TIME = config.getInt("games-settings.generals.waiting-quiz-time");
    public static final int WAITING_QUESTION_TIME = config.getInt("games-settings.generals.waiting-question-time");

    // CATEGORIES
    public static final List<QCategory> QUIZ_CATEGORIES = config.getConfigurationSection("games-settings.categories").getKeys(false).stream().map(categoryVar -> {
        ConfigurationSection categorySection = config.getConfigurationSection("games-settings.categories").getConfigurationSection(categoryVar);
        QCategory category = new QCategory(categorySection.getString("name"));

        category.setQuestions(categorySection.getConfigurationSection("questions").getKeys(false).stream().map(questionVar -> {
            ConfigurationSection questionSection = categorySection.getConfigurationSection("questions").getConfigurationSection(questionVar);
            return new QQuestion(questionSection.getString("question"), questionSection.getStringList("answers").stream().map(String::toLowerCase).collect(Collectors.toList()));
        }).collect(Collectors.toList()));

        return category;
    }).collect(Collectors.toList());

    // DATABASE
    public static final Credentials DATABASE_CREDENTIALS = Credentials.builder()
            .host(config.getString("database-credentials.hostname"))
            .user(config.getString("database-credentials.username"))
            .password(config.getString("database-credentials.password"))
            .schema(config.getString("database-credentials.database"))
            .port(config.getInt("database-credentials.port"))
            .pool("quiz-pool")
            .build();
}
