package me.imjaxs.devroom.quizgame.managers.user;

import com.google.common.collect.Maps;
import me.imjaxs.devroom.quizgame.QuizGamePlugin;
import me.imjaxs.devroom.quizgame.objects.user.QUser;

import java.util.Map;
import java.util.UUID;

public class UserManager {
    private final Map<UUID, QUser> userCache = Maps.newHashMap();

    public UserManager(QuizGamePlugin plugin) {
        plugin.getDatabaseManager().loadUsers().whenComplete((users, throwable) -> {
            if (throwable != null) {
                throwable.printStackTrace();
                return;
            }

            if (users != null && !users.isEmpty())
                users.forEach(user -> userCache.put(user.getUniqueID(), user));
        });
    }

    public QUser getUser(UUID uniqueID) {
        return userCache.get(uniqueID);
    }

    public QUser getUserByName(String name) {
        for (QUser user : userCache.values())
            if (user.getName().equalsIgnoreCase(name))
                return user;
        return null;
    }

    public void addUser(QUser user) {
        userCache.put(user.getUniqueID(), user);
    }

    public void removeUser(QUser user) {
        userCache.remove(user.getUniqueID());
    }

    public Map<UUID, QUser> getUserCache() {
        return userCache;
    }
}
