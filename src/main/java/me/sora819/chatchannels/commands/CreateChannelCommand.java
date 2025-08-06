package me.sora819.chatchannels.commands;

import me.sora819.chatchannels.channels.ChannelHandler;
import me.sora819.chatchannels.localization.LocalizationHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CreateChannelCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length != 1) {
            sender.sendMessage(LocalizationHandler.getMessage("error.invalid_argument_count", true));
            return true;
        }

        try {
            ChannelHandler.createChannel(args[0]);
            sender.sendMessage(LocalizationHandler.getMessage("channel.created", true) + args[0]);
        } catch (Exception e) {
            sender.sendMessage(e.getMessage());
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        Set<String> completions = new HashSet<>();

        completions.removeIf(channel -> !channel.toLowerCase().startsWith(args[0].toLowerCase()));
        return completions.stream().toList();
    }
}
