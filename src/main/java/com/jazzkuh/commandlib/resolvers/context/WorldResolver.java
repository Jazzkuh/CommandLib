package com.jazzkuh.commandlib.resolvers.context;

import com.jazzkuh.commandlib.resolvers.ContextResolver;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;

public final class WorldResolver implements ContextResolver<World> {
    @Override
    public World resolve(CommandSender sender, String args) {
        return Bukkit.getWorld(args);
    }
}