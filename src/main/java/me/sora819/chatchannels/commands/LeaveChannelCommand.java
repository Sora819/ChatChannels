package me.sora819.chatchannels.commands;

import me.sora819.chatchannels.channels.ChannelHandler;
import me.sora819.chatchannels.localization.LocalizationHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Set;

public class LeaveChannelCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(LocalizationHandler.getMessage("error.must_be_player", true));
            return true;
        }

        if (args.length != 1) {
            sender.sendMessage(LocalizationHandler.getMessage("error.invalid_argument_count", true));
            return true;
        }

        try {
            ChannelHandler.removePlayerFromChannel(args[0], (Player)sender);
            sender.sendMessage(LocalizationHandler.getMessage("channel.left", true) + args[0]);
        } catch (Exception e) {
            sender.sendMessage(e.getMessage());
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        Set<String> completions = ChannelHandler.getChannels((Player)sender);

        completions.removeIf(channel -> !channel.toLowerCase().startsWith(args[0].toLowerCase()));
        return completions.stream().toList();
    }
}
