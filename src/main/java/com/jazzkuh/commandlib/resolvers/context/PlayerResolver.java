package com.jazzkuh.commandlib.resolvers.context;

import com.jazzkuh.commandlib.resolvers.ContextResolver;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public final class PlayerResolver implements ContextResolver<Player> {
    @Override
    public Player resolve(CommandSender sender, String args) {
        return Bukkit.getPlayer(args);
    }
}