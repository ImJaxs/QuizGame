package me.imjaxs.devroom.quizgame.managers.database;

import com.glyart.mystral.database.AsyncDatabase;
import com.glyart.mystral.database.Credentials;
import com.glyart.mystral.database.Mystral;
import com.glyart.mystral.sql.BatchSetter;
import com.google.common.collect.Lists;
import me.imjaxs.devroom.quizgame.QuizGamePlugin;
import me.imjaxs.devroom.quizgame.configurations.files.ConfigFile;
import me.imjaxs.devroom.quizgame.managers.user.UserManager;
import me.imjaxs.devroom.quizgame.objects.user.QUser;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class DatabaseManager {
    private final static String TABLE = "quiz_users";

    private final QuizGamePlugin plugin;
    private final AsyncDatabase asyncDatabase;

    public DatabaseManager(QuizGamePlugin plugin) {
        this.plugin = plugin;

        Executor executor = (command -> Bukkit.getScheduler().runTaskAsynchronously(plugin, command));
        this.asyncDatabase = Mystral.newAsyncDatabase(ConfigFile.DATABASE_CREDENTIALS, executor);
    }

    public void createTable() {
        asyncDatabase.update("CREATE TABLE IF NOT EXISTS " + TABLE + "name TEXT, uniqueID TEXT PRIMARY KEY, points INT ", false);
    }

    public CompletableFuture<Integer> insertUser(QUser user) {
        return asyncDatabase.update("INSERT INTO " + TABLE + " VALUES(?, ?, ?);", new Object[]{
                user.getName(), user.getUniqueID().toString(), user.getPoints()
        }, false, Types.VARCHAR, Types.VARCHAR, Types.INTEGER);
    }

    public CompletableFuture<QUser> loadUser(UUID uniqueID) {
        return asyncDatabase.queryForObject("SELECT * FROM " + TABLE + " WHERE uniqueID = ?;", new Object[]{uniqueID.toString()}, (result, row) -> {
            return new QUser(result.getString(1), UUID.fromString(result.getString(2)), result.getInt(3));
        }, Types.VARCHAR);
    }

    public CompletableFuture<List<QUser>> loadUsers() {
        //
        return null;
    }

    public CompletableFuture<Integer> saveUser(QUser user) {
        return asyncDatabase.update("UPDATE " + TABLE + " SET points = ? WHERE uniqueID = ?;", new Object[]{
                user.getPoints(), user.getUniqueID().toString()
        }, false, Types.INTEGER, Types.VARCHAR);
    }

    public CompletableFuture<Void> saveAsyncPlayers() {
        List<QUser> userCache = Lists.newArrayList(plugin.getUserManager().getUserCache().values());

        return asyncDatabase.batchUpdate("UPDATE " + TABLE + " SET points = ? WHERE uniqueID = ?;", new BatchSetter() {
            @Override
            public void setValues(@NotNull PreparedStatement statement, int i) throws SQLException {
                QUser user = userCache.get(i);

                statement.setInt(1, user.getPoints());
                statement.setString(2, user.getUniqueID().toString());
            }

            @Override
            public int getBatchSize() {
                return userCache.size();
            }
        });
    }
}
