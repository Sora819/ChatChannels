package me.sora819.chatchannels.commands;

import me.sora819.chatchannels.channels.ChannelHandler;
import me.sora819.chatchannels.config.ConfigHandler;
import me.sora819.chatchannels.localization.LocalizationHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DeleteChannelCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length != 1) {
            sender.sendMessage(LocalizationHandler.getMessage("error.invalid_argument_count", true));
            return true;
        }

        try {
            ChannelHandler.deleteChannel(args[0]);
            sender.sendMessage(LocalizationHandler.getMessage("channel.deleted", true) + args[0]);
        } catch (Exception e) {
            sender.sendMessage(e.getMessage());
        }

        return true;
    }

}
