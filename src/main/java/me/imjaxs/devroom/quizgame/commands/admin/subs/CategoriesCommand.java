package me.imjaxs.devroom.quizgame.commands.admin.subs;

import com.google.common.collect.Lists;
import me.imjaxs.devroom.quizgame.QuizGamePlugin;
import me.imjaxs.devroom.quizgame.commands.SubCommand;
import me.imjaxs.devroom.quizgame.configurations.files.LanguageFile;
import me.imjaxs.devroom.quizgame.managers.quiz.QuizManager;
import me.imjaxs.devroom.quizgame.tools.Utils;
import org.bukkit.command.CommandSender;

import java.util.List;

public class CategoriesCommand implements SubCommand {
    private final QuizManager quizManager;

    public CategoriesCommand(QuizGamePlugin plugin) {
        this.quizManager = plugin.getQuizManager();
    }

    @Override
    public String getName() {
        return "categories";
    }

    @Override
    public String getUsage() {
        return "/qadmin categories";
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (args.length != 0)
            return false;

        if (quizManager.getCategories().isEmpty())
            sender.sendMessage(Utils.color(LanguageFile.QUIZ_ADMIN_COMMAND_NO_CATEGORIES));

        String categories = ""; List<String> values = Lists.newArrayList(quizManager.getCategories().keySet());
        for (int i = 0; i < values.size(); i++)
            categories = categories + (i == 0 ? values.get(i) : ", " + values.get(i));

        sender.sendMessage(Utils.color(LanguageFile.QUIZ_ADMIN_COMMAND_CATEGORIES.replace("%categories%", categories)));
        return true;
    }
}
