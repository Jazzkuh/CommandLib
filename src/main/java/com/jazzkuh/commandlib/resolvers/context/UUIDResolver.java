package com.jazzkuh.commandlib.resolvers.context;

import com.jazzkuh.commandlib.resolvers.ContextResolver;
import org.bukkit.command.CommandSender;

import java.util.UUID;

public final class UUIDResolver implements ContextResolver<UUID> {
    @Override
    public UUID resolve(CommandSender sender, String args) {
        try {
            return UUID.fromString(args);
        } catch (Exception e) {
            return null;
        }
    }
}