package me.imjaxs.devroom.quizgame.commands.admin;

import com.google.common.collect.Lists;
import me.imjaxs.devroom.quizgame.QuizGamePlugin;
import me.imjaxs.devroom.quizgame.commands.SubCommand;
import me.imjaxs.devroom.quizgame.commands.admin.subs.CategoriesCommand;
import me.imjaxs.devroom.quizgame.commands.admin.subs.CheckCommand;
import me.imjaxs.devroom.quizgame.commands.admin.subs.StartCommand;
import me.imjaxs.devroom.quizgame.configurations.files.LanguageFile;
import me.imjaxs.devroom.quizgame.tools.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.List;

public class AdminCommandManager implements CommandExecutor {
    private final String PERMISSION = "quiz.admin";

    private final List<SubCommand> subCommands;

    public AdminCommandManager(QuizGamePlugin plugin) {
        this.subCommands = Lists.newArrayList();

        subCommands.add(new StartCommand(plugin));
        subCommands.add(new CheckCommand(plugin));
        subCommands.add(new CategoriesCommand(plugin));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission(PERMISSION)) {
            sender.sendMessage(Utils.color(LanguageFile.NO_PERMISSION));
            return true;
        }

        if (args.length == 0) {
            LanguageFile.QUIZ_ADMIN_COMMAND_HELP.forEach(line -> {
                sender.sendMessage(Utils.color(line));
            });
            return true;
        }
        String name = args[0];

        SubCommand subCommand = getSubCommand(name);
        if (subCommand == null) {
            sender.sendMessage(Utils.color(LanguageFile.NO_SUBCOMMAND));
            return true;
        }

        if (!subCommand.execute(sender, getSubCommandArgs(args))) {
            sender.sendMessage(Utils.color(LanguageFile.NO_CORRECT_USAGE.replace("%usage%", subCommand.getUsage())));
            return true;
        }
        return true;
    }

    private SubCommand getSubCommand(String name) {
        for (SubCommand subCommand : subCommands)
            if (subCommand.getName().equalsIgnoreCase(name))
                return subCommand;
        return null;
    }

    private String[] getSubCommandArgs(String[] args) {
        String[] arguments = new String[args.length - 1];
        System.arraycopy(args,1, arguments, 0, arguments.length);
        return arguments;
    }
}
