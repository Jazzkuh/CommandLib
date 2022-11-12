package com.jazzkuh.commandlib.resolvers.context;

import com.jazzkuh.commandlib.resolvers.ContextResolver;
import org.bukkit.command.CommandSender;

public final class StringResolver implements ContextResolver<String> {
    @Override
    public String resolve(CommandSender sender, String arg) {
        return arg;
    }
}