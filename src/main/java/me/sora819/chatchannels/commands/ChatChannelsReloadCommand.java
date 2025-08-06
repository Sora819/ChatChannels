package me.sora819.chatchannels.commands;

import me.sora819.chatchannels.config.ConfigHandler;
import me.sora819.chatchannels.localization.LocalizationHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ChatChannelsReloadCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length != 0) {
            sender.sendMessage(LocalizationHandler.getMessage("error.invalid_argument_count", true));
            return true;
        }

        ConfigHandler.reloadConfigurations();
        sender.sendMessage(LocalizationHandler.getMessage("success.reload", true));
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        Set<String> completions = new HashSet<>();

        completions.removeIf(channel -> !channel.toLowerCase().startsWith(args[0].toLowerCase()));
        return completions.stream().toList();
    }
}
