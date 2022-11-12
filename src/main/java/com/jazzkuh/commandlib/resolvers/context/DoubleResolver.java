package com.jazzkuh.commandlib.resolvers.context;

import com.jazzkuh.commandlib.resolvers.ContextResolver;
import org.bukkit.command.CommandSender;

public final class DoubleResolver implements ContextResolver<Double> {
    @Override
    public Double resolve(CommandSender sender, String args) {
        try {
            return Double.parseDouble(args);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}