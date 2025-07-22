package me.sora819.chatchannels.listeners;

import me.sora819.chatchannels.ChatChannelsPlugin;
import me.sora819.chatchannels.channels.ChannelHandler;
import me.sora819.chatchannels.config.ConfigHandler;
import me.sora819.chatchannels.localization.LocalizationHandler;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        String channel = ChannelHandler.getActiveChannel(e.getPlayer());

        e.getRecipients().clear();

        e.getRecipients().addAll(
                ChannelHandler.getChannelPlayers(channel).stream()
                        .map(playerName -> ChatChannelsPlugin.getInstance().getServer().getPlayer(playerName))
                        .toList()
        );

        String prefix_format = String.format((String)ConfigHandler.defaultConfig.get("chat_prefix_format"), channel);
        String format = ChatColor.translateAlternateColorCodes('&', prefix_format) + e.getFormat();
        e.setFormat(format);

        ChatChannelsPlugin.getInstance().getLogger().info(e.getFormat());
    }
}
