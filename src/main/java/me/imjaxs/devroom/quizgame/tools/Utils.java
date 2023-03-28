package me.imjaxs.devroom.quizgame.tools;

import org.bukkit.ChatColor;

public class Utils {
    public static String color(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static String formatMillis(long millis) {
        long fraction = Math.abs(millis) % 1000;
        return (millis / 1000) + "." + (fraction < 10 ? "00" + String.valueOf(fraction).charAt(0) : fraction < 100 ? "0" + String.valueOf(fraction).charAt(0) : String.valueOf(fraction).substring(0, 2));
    }
}
