package me.imjaxs.devroom.quizgame.objects.quiz.delay;

import me.imjaxs.devroom.quizgame.QuizGamePlugin;
import me.imjaxs.devroom.quizgame.configurations.files.LanguageFile;
import me.imjaxs.devroom.quizgame.tools.Utils;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

public class QDelay {
    private int second;
    private BukkitTask task;
    private final int seconds;

    public QDelay(int seconds) {
        this.second = 0;
        this.seconds = seconds;
    }

    public void init(Runnable runnable) {
        this.task = Bukkit.getScheduler().runTaskTimerAsynchronously(QuizGamePlugin.get(), () -> {
            second++;

            if (second > seconds) {
                runnable.run();
                task.cancel();
            }

            Bukkit.getOnlinePlayers().forEach(var -> {
                var.sendTitle(Utils.color(LanguageFile.QUIZ_QUESTION_DELAY_TITLE.replace("%seconds%", String.valueOf(second))), Utils.color(LanguageFile.QUIZ_QUESTION_DELAY_SUBTITLE.replace("%seconds%", String.valueOf(second))), 2, 10, 2);
            });
        }, 20L, 20L);
    }
}
