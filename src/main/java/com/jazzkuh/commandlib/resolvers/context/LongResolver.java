package com.jazzkuh.commandlib.resolvers.context;

import com.jazzkuh.commandlib.resolvers.ContextResolver;
import org.bukkit.command.CommandSender;

public final class LongResolver implements ContextResolver<Long> {
    @Override
    public Long resolve(CommandSender sender, String args) {
        try {
            return Long.parseLong(args);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}