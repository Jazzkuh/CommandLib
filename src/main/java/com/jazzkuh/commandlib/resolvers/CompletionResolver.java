package com.jazzkuh.commandlib.resolvers;

import org.bukkit.command.CommandSender;

import java.util.List;

public interface CompletionResolver {
    List<String> resolve(CommandSender sender, String arg);
}
