package com.jazzkuh.commandlib.resolvers.completion;

import com.jazzkuh.commandlib.resolvers.CompletionResolver;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;

import java.util.List;

public final class WorldCompletionResolver implements CompletionResolver {
    @Override
    public List<String> resolve(CommandSender sender, String arg) {
        return Bukkit.getWorlds().stream().map(World::getName).toList();
    }
}