package com.jazzkuh.commandlib.utils;

import lombok.experimental.UtilityClass;
import org.bukkit.ChatColor;

@UtilityClass
public class ChatUtils {
    public static String color(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
