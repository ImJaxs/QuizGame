package me.imjaxs.devroom.quizgame.commands.user;

import me.imjaxs.devroom.quizgame.QuizGamePlugin;
import me.imjaxs.devroom.quizgame.commands.SubCommand;
import me.imjaxs.devroom.quizgame.configurations.files.LanguageFile;
import me.imjaxs.devroom.quizgame.managers.user.UserManager;
import me.imjaxs.devroom.quizgame.objects.user.QUser;
import me.imjaxs.devroom.quizgame.tools.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class UserCommandManager implements CommandExecutor {
    private final UserManager userManager;

    public UserCommandManager(QuizGamePlugin plugin) {
        this.userManager = plugin.getUserManager();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof ConsoleCommandSender) {
            //
            return true;
        }
        Player player = (Player) sender;

        QUser user = userManager.getUser(player.getUniqueId());
        if (user == null)
            user = new QUser(player.getName(), player.getUniqueId(), 0);

        sender.sendMessage(Utils.color(LanguageFile.QUIZ_USER_COMMAND_CHECK.replace("%points", String.valueOf(user.getPoints()))));
        return true;
    }
}
