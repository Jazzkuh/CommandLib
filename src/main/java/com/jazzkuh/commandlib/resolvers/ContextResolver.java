package com.jazzkuh.commandlib.resolvers;

import org.bukkit.command.CommandSender;

public interface ContextResolver<T> {
    T resolve(CommandSender sender, String arg);
}
