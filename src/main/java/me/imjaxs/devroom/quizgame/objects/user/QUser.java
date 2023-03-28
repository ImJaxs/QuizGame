package me.imjaxs.devroom.quizgame.objects.user;

import java.util.UUID;

public class QUser {
    private String name;
    private UUID uniqueID;
    private int points;

    public QUser(String name, UUID uniqueID) {
        this(name, uniqueID, 0);
    }

    public QUser(String name, UUID uniqueID, int points) {
        this.name = name;
        this.uniqueID = uniqueID;
        this.points = points;
    }

    public String getName() {
        return name;
    }

    public UUID getUniqueID() {
        return uniqueID;
    }

    public int getPoints() {
        return points;
    }

    public void addPoints(int points) {
        this.points += points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
