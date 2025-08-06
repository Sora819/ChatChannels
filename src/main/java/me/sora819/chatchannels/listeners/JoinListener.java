package me.sora819.chatchannels.listeners;

import me.sora819.chatchannels.channels.ChannelHandler;
import me.sora819.chatchannels.config.ConfigAdapter;
import me.sora819.chatchannels.config.ConfigHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        ConfigAdapter channelsData = ConfigHandler.channelsData;
        ConfigAdapter playersData = ConfigHandler.playersData;
        String defaultChannel = ConfigHandler.defaultConfig.get("default_channel");

        if (!channelsData.has(defaultChannel)) {
            try {
                ChannelHandler.createChannel(defaultChannel);
            } catch (Exception ex) {

            }
        }

        if (!playersData.has(e.getPlayer().getUniqueId().toString())) {
            try {
                ChannelHandler.addPlayerToChannel(defaultChannel, e.getPlayer());
                ChannelHandler.setActiveChannel(e.getPlayer(), defaultChannel);
            } catch (Exception ex) {

            }
        }
    }
}
