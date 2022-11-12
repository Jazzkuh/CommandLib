package com.jazzkuh.commandlib;

import com.jazzkuh.commandlib.resolvers.CompletionResolver;
import com.jazzkuh.commandlib.resolvers.completion.GameModeCompletionResolver;
import com.jazzkuh.commandlib.resolvers.completion.PlayerCompletionResolver;
import com.jazzkuh.commandlib.resolvers.completion.WorldCompletionResolver;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * Easily create commands with annotations.
 *
 * @author Jazzkuh
 */
public class CompletionResolverRegistry {
    private static final Map<Class<?>, CompletionResolver> typeResolvers = new HashMap<>();
    private static final Map<String, CompletionResolver> completions = new HashMap<>();


    static {
        completions.put("players", new PlayerCompletionResolver());

        typeResolvers.put(Player.class, new PlayerCompletionResolver());
        typeResolvers.put(GameMode.class, new GameModeCompletionResolver());
        typeResolvers.put(World.class, new WorldCompletionResolver());
    }

    public static void registerCompletion(String name, CompletionResolver resolver) {
        completions.put(name, resolver);
    }

    public static CompletionResolver getCompletion(String name) {
        name = name.startsWith("@") ? name.substring(1) : name;
        return completions.get(name);
    }

    public static void registerResolver(Class<?> typeClass, CompletionResolver resolver) {
        typeResolvers.put(typeClass, resolver);
    }

    public static CompletionResolver getResolver(Class<?> typeClass) {
        return typeResolvers.get(typeClass);
    }
}