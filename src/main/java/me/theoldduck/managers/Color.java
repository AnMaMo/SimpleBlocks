package me.theoldduck.managers;

import org.bukkit.ChatColor;

public class Color {

    public static String color(String a) {
        return ChatColor.translateAlternateColorCodes('&', a);
    }
}