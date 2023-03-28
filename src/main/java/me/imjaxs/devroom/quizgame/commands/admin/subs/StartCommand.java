package me.imjaxs.devroom.quizgame.commands.admin.subs;

import me.imjaxs.devroom.quizgame.QuizGamePlugin;
import me.imjaxs.devroom.quizgame.commands.SubCommand;
import me.imjaxs.devroom.quizgame.configurations.files.LanguageFile;
import me.imjaxs.devroom.quizgame.managers.quiz.QuizManager;
import me.imjaxs.devroom.quizgame.objects.quiz.category.QCategory;
import me.imjaxs.devroom.quizgame.objects.quiz.QQuiz;
import me.imjaxs.devroom.quizgame.tools.Utils;
import org.bukkit.command.CommandSender;

public class StartCommand implements SubCommand {
    private final QuizManager quizManager;

    public StartCommand(QuizGamePlugin plugin) {
        this.quizManager = plugin.getQuizManager();
    }

    @Override
    public String getName() {
        return "start";
    }

    @Override
    public String getUsage() {
        return "/qadmin start <category>";
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (args.length != 1)
            return false;

        QQuiz quiz = quizManager.getQuiz();
        if (quiz != null) {
            sender.sendMessage(Utils.color(LanguageFile.QUIZ_ADMIN_COMMAND_START_ALREADY_QUIZ));
            return true;
        }

        QCategory category = quizManager.getCategory(args[0]);
        if (category == null) {
            sender.sendMessage(Utils.color(LanguageFile.QUIZ_ADMIN_COMMAND_START_CATEGORY_NOT_FOUND));
            return true;
        }
        quizManager.createQuiz(category);

        sender.sendMessage(Utils.color(LanguageFile.QUIZ_ADMIN_COMMAND_START.replace("%category%", category.getName())));
        return false;
    }
}
