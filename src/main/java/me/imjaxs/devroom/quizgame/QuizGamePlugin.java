package me.imjaxs.devroom.quizgame;

import me.imjaxs.devroom.quizgame.commands.admin.AdminCommandManager;
import me.imjaxs.devroom.quizgame.commands.user.UserCommandManager;
import me.imjaxs.devroom.quizgame.listeners.PlayerListener;
import me.imjaxs.devroom.quizgame.managers.database.DatabaseManager;
import me.imjaxs.devroom.quizgame.managers.quiz.QuizManager;
import me.imjaxs.devroom.quizgame.managers.user.UserManager;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

public final class QuizGamePlugin extends JavaPlugin {
    private static QuizGamePlugin instance;
    private YamlConfiguration language;

    private UserManager userManager;
    private QuizManager quizManager;
    private DatabaseManager databaseManager;

    @Override
    public void onEnable() {
        instance = this;

        // FILES
        this.saveDefaultConfig();


        // MANAGERS
        this.databaseManager = new DatabaseManager(this);
        this.databaseManager.createTable();
        this.userManager = new UserManager(this);
        this.quizManager = new QuizManager(this);

        // COMMANDS
        this.getCommand("quiz").setExecutor(new UserCommandManager(this));
        this.getCommand("qadmin").setExecutor(new AdminCommandManager(this));

        // LISTENERS
        this.getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
    }

    @Override
    public void onDisable() {

    }

    public static QuizGamePlugin get() {
        return instance;
    }

    private void createLanguage() {
        File file = new File(this.getDataFolder(), "language.yml");

        if (!file.exists())
            this.saveResource("language.yml", false);
        this.language = new YamlConfiguration();

        try {
            language.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public YamlConfiguration getLanguage() {
        return language;
    }

    public UserManager getUserManager() {
        return userManager;
    }

    public QuizManager getQuizManager() {
        return quizManager;
    }

    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }
}
