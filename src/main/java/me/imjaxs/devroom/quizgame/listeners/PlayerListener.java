package me.imjaxs.devroom.quizgame.listeners;

import me.imjaxs.devroom.quizgame.QuizGamePlugin;
import me.imjaxs.devroom.quizgame.configurations.files.ConfigFile;
import me.imjaxs.devroom.quizgame.managers.database.DatabaseManager;
import me.imjaxs.devroom.quizgame.managers.quiz.QuizManager;
import me.imjaxs.devroom.quizgame.managers.user.UserManager;
import me.imjaxs.devroom.quizgame.objects.quiz.QQuiz;
import me.imjaxs.devroom.quizgame.objects.user.QUser;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerListener implements Listener {
    private final UserManager userManager;
    private final QuizManager quizManager;
    private final DatabaseManager databaseManager;

    public PlayerListener(QuizGamePlugin plugin) {
        this.userManager = plugin.getUserManager();
        this.quizManager = plugin.getQuizManager();
        this.databaseManager = plugin.getDatabaseManager();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        QUser checkUser = userManager.getUser(player.getUniqueId());
        if (checkUser != null)
            return;

        QUser user = new QUser(player.getName(), player.getUniqueId(), 0);
        databaseManager.insertUser(user).whenComplete((integer, throwableInsert) -> {
            if (throwableInsert != null)
                throwableInsert.printStackTrace();
        });
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        QQuiz quiz = quizManager.getQuiz();
        if (quiz == null)
            return;

        String answer = event.getMessage();
        if (!quiz.checkQuestion(answer) && ConfigFile.REMOVE_OTHER_MESSAGES)
            event.setCancelled(true);
        quiz.rightQuestion(player);
    }
}
