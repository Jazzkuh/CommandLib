package com.jazzkuh.commandlib;

import com.jazzkuh.commandlib.annotations.Main;
import com.jazzkuh.commandlib.annotations.Subcommand;
import com.jazzkuh.commandlib.utils.ChatUtils;
import lombok.Getter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabExecutor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Easily create commands with annotations.
 *
 * @author Jazzkuh
 */
public abstract class AnnotationCommand implements TabExecutor {
    private final @Getter String commandName;
    private final List<Method> subcommandMethods;
    private AnnotationMainCommand mainCommand = null;
    private final List<AnnotationSubCommand> subCommands = new ArrayList<>();

    public AnnotationCommand(String commandName) {
        this.commandName = commandName;

        List<Method> mainCommands = Arrays.stream(this.getClass().getMethods()).filter(method -> method.isAnnotationPresent(Main.class)).toList();
        if (mainCommands.size() > 1) {
            throw new IllegalArgumentException("There can only be one main command per class");
        }
        mainCommands.forEach(method -> this.mainCommand = new AnnotationMainCommand(this, method));

        this.subcommandMethods = Arrays.stream(this.getClass().getMethods()).filter(method -> method.isAnnotationPresent(Subcommand.class)).collect(Collectors.toList());
        this.subcommandMethods.forEach(method -> {
            AnnotationSubCommand subCommand = new AnnotationSubCommand(this, method);
            subCommands.add(subCommand);
        });
    }

    public void register(JavaPlugin plugin) {
        PluginCommand cmd = plugin.getCommand(commandName);
        if (cmd != null) {
            if (mainCommand.getPermission() != null) {
                cmd.setPermission(mainCommand.getPermission());
                cmd.setPermissionMessage(ChatUtils.color("&cYou do not have permission to use this command."));
            }
            cmd.setExecutor(this);
        }
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (args.length < 1) {
            this.execute(sender, args);
            return true;
        }

        for (AnnotationSubCommand subCommand : subCommands) {
            if (!args[0].equalsIgnoreCase(subCommand.getName()) && !subCommand.getAliases().contains(args[0].toLowerCase())) continue;
            if (subCommand.getPermission() != null && !sender.hasPermission(subCommand.getPermission())) {
                sender.sendMessage(ChatUtils.color("&cYou do not have permission to use this command."));
                return true;
            }

            boolean execute = subCommand.execute(sender, args, false);
            if (!execute) {
                sendNotEnoughArguments(sender);
                return true;
            }

            return true;
        }

        this.execute(sender, args);
        return true;
    }

    private void execute(CommandSender sender, String[] args) {
        if (mainCommand.getPermission() != null && !sender.hasPermission(mainCommand.getPermission())) {
            sender.sendMessage(ChatUtils.color("&cYou do not have permission to use this command."));
            return;
        }

        if (mainCommand == null) {
            sendNotEnoughArguments(sender);
            return;
        }

        boolean execute = mainCommand.execute(sender, args, true);
        if (!execute) sendNotEnoughArguments(sender);
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        List<String> options = new ArrayList<>(mainCommand.tabComplete(sender, args, true));

        if (args.length == 1 && this.subcommandMethods.size() >= 1) {
            for (AnnotationSubCommand subCommand : this.subCommands) {
                options.add(subCommand.getName());
            }

            return getApplicableTabCompleters(args[0], options);
        }

        for (AnnotationSubCommand subCommand : this.subCommands) {
            if (!args[0].equalsIgnoreCase(subCommand.getName()) && !subCommand.getAliases().contains(args[0].toLowerCase())) continue;
            options.addAll(subCommand.tabComplete(sender, args, false));
        }
        return options;
    }

    protected void sendNotEnoughArguments(CommandSender sender) {
        if (mainCommand.getUsage() != null && mainCommand.getUsage().length() > 0) {
            sender.sendMessage(ChatUtils.color("&6/" + this.commandName + mainCommand.getCommandArgument().getArguments() + "&8 - &7" + mainCommand.getCommandArgument().getDescription()));
        }

        for (AnnotationSubCommand subCommand : this.subCommands) {
            if (subCommand.getPermission() == null || sender.hasPermission(subCommand.getPermission())) {
                CommandArgument commandArgument = subCommand.getCommandArgument();
                sender.sendMessage(ChatUtils.color("&6/" + this.commandName + " " + commandArgument.getArguments() + "&8 - &7" + commandArgument.getDescription()));
            }
        }
    }

    protected List<String> getApplicableTabCompleters(String arg, Collection<String> completions) {
        return StringUtil.copyPartialMatches(arg, completions, new ArrayList<>(completions.size()));
    }
}
