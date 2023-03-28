package me.imjaxs.devroom.quizgame.configurations.files;

import me.imjaxs.devroom.quizgame.QuizGamePlugin;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class LanguageFile {
    private static final FileConfiguration language = QuizGamePlugin.get().getLanguage();

    public static String NO_CONSOLE = language.getString("no-console");
    public static String NO_PERMISSION = language.getString("no-permission");
    public static String NO_SUBCOMMAND = language.getString("no-subcommand");
    public static String NO_CORRECT_USAGE = language.getString("no-correct-usage");

    public static String QUIZ_START = language.getString("quiz-start");
    public static String QUIZ_END = language.getString("quiz-end");
    public static String QUIZ_QUESTION_DELAY_TITLE = language.getString("quiz-question-delay-title");
    public static String QUIZ_QUESTION_DELAY_SUBTITLE = language.getString("quiz-question-delay-subtitle");
    public static String QUIZ_RIGHT_QUESTION = language.getString("quiz-right-question");

    public static String QUIZ_USER_COMMAND_CHECK = language.getString("quiz-user-command-check");

    public static List<String> QUIZ_ADMIN_COMMAND_HELP = language.getStringList("quiz-admin-command-help");

    public static String QUIZ_ADMIN_COMMAND_NO_CATEGORIES = language.getString("quiz-admin-command-no-categories");
    public static String QUIZ_ADMIN_COMMAND_CATEGORIES = language.getString("quiz-admin-command-categories");

    public static String QUIZ_ADMIN_COMMAND_CHECK_NO_PLAYER = language.getString("quiz-admin-command-check-no-player");
    public static String QUIZ_ADMIN_COMMAND_CHECK_PLAYER = language.getString("quiz-admin-command-check-player");

    public static String QUIZ_ADMIN_COMMAND_START_ALREADY_QUIZ = language.getString("quiz-admin-command-start-already-quiz");
    public static String QUIZ_ADMIN_COMMAND_START_CATEGORY_NOT_FOUND = language.getString("quiz-admin-command-start-category-not-found");
    public static String QUIZ_ADMIN_COMMAND_START = language.getString("quiz-admin-command-start");
}
