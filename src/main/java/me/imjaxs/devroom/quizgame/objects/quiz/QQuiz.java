package me.imjaxs.devroom.quizgame.objects.quiz;

import com.google.common.collect.Lists;
import me.imjaxs.devroom.quizgame.QuizGamePlugin;
import me.imjaxs.devroom.quizgame.configurations.files.ConfigFile;
import me.imjaxs.devroom.quizgame.configurations.files.LanguageFile;
import me.imjaxs.devroom.quizgame.managers.database.DatabaseManager;
import me.imjaxs.devroom.quizgame.managers.user.UserManager;
import me.imjaxs.devroom.quizgame.objects.quiz.delay.QDelay;
import me.imjaxs.devroom.quizgame.objects.quiz.category.QCategory;
import me.imjaxs.devroom.quizgame.objects.quiz.question.QQuestion;
import me.imjaxs.devroom.quizgame.objects.user.QUser;
import me.imjaxs.devroom.quizgame.tools.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class QQuiz {
    private final QuizGamePlugin plugin;
    private final UserManager userManager;
    private final DatabaseManager databaseManager;

    private final QCategory category;
    private QQuestion question;
    private final List<QQuestion> askedQuestions;
    private long times;

    public QQuiz(QuizGamePlugin plugin, QCategory category) {
        this.plugin = plugin;
        this.userManager = plugin.getUserManager();
        this.databaseManager = plugin.getDatabaseManager();

        this.category = category;
        this.question = null;
        this.askedQuestions = Lists.newArrayList();
        this.times = 0;
    }

    public void init() {
        Bukkit.getOnlinePlayers().forEach(var -> {
            var.sendMessage(Utils.color(LanguageFile.QUIZ_START.replace("%quiz_time%", String.valueOf(ConfigFile.WAITING_QUIZ_TIME))));
        });
        Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, this::newQuestion, 20L * ConfigFile.WAITING_QUIZ_TIME);
    }

    public void newQuestion() {
        this.times = System.currentTimeMillis();

        List<QQuestion> values = Lists.newArrayList();
        category.getQuestions().forEach(var -> {
            if (!askedQuestions.contains(var))
                values.add(var);
        });

        if (values.isEmpty()) {
            this.stop();
            return;
        }

        question = values.get(ThreadLocalRandom.current().nextInt(values.size()));

        new QDelay(ConfigFile.WAITING_QUESTION_TIME).init(() -> {
            Bukkit.getOnlinePlayers().forEach(var -> {
                //
            });
        });
    }

    public boolean checkQuestion(String answer) {
        return question.getAnswers().contains(answer.toLowerCase());
    }

    public void rightQuestion(Player player) {
        askedQuestions.add(question); question = null;

        QUser user = userManager.getUser(player.getUniqueId());
        if (user != null)
            user.addPoints(1);

        long millis = System.currentTimeMillis() - times;
        Bukkit.getOnlinePlayers().forEach(var -> {
            var.sendMessage(Utils.color(LanguageFile.QUIZ_RIGHT_QUESTION.replace("%seconds%", Utils.formatMillis(millis))));
        });

        this.newQuestion();
    }

    public void stop() {
        plugin.getQuizManager().deleteQuiz();

        Bukkit.getOnlinePlayers().forEach(var -> {
            var.sendMessage(Utils.color(LanguageFile.QUIZ_END));
        });

        databaseManager.saveAsyncPlayers().whenComplete((unused, throwable) -> {
            if (throwable != null)
                throwable.printStackTrace();
        });
    }
}
