package me.imjaxs.devroom.quizgame.commands.admin.subs;

import me.imjaxs.devroom.quizgame.QuizGamePlugin;
import me.imjaxs.devroom.quizgame.commands.SubCommand;
import me.imjaxs.devroom.quizgame.configurations.files.LanguageFile;
import me.imjaxs.devroom.quizgame.managers.user.UserManager;
import me.imjaxs.devroom.quizgame.objects.user.QUser;
import me.imjaxs.devroom.quizgame.tools.Utils;
import org.bukkit.command.CommandSender;

public class CheckCommand implements SubCommand {
    private final UserManager userManager;

    public CheckCommand(QuizGamePlugin plugin) {
        this.userManager = plugin.getUserManager();
    }

    @Override
    public String getName() {
        return "check";
    }

    @Override
    public String getUsage() {
        return "/qadmin check <player>";
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (args.length != 1)
            return false;

        QUser user = userManager.getUserByName(args[0]);
        if (user == null) {
            sender.sendMessage(Utils.color(LanguageFile.QUIZ_ADMIN_COMMAND_CHECK_NO_PLAYER));
            return true;
        }

        sender.sendMessage(Utils.color(LanguageFile.QUIZ_ADMIN_COMMAND_CHECK_PLAYER.replace("%player_name%", user.getName()).replace("%points%", String.valueOf(user.getPoints()))));
        return true;
    }
}
