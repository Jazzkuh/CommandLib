package com.jazzkuh.commandlib.resolvers.completion;

import com.jazzkuh.commandlib.resolvers.CompletionResolver;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public final class PlayerCompletionResolver implements CompletionResolver {
    @Override
    public List<String> resolve(CommandSender sender, String arg) {
        return Bukkit.getOnlinePlayers().stream().map(Player::getName).toList();
    }
}