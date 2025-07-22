package me.sora819.chatchannels.commands;

import me.sora819.chatchannels.config.ConfigHandler;
import me.sora819.chatchannels.localization.LocalizationHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ChatChannelsReloadCommand implements CommandExecutor {

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

}
