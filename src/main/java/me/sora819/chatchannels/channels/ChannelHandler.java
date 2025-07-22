package me.sora819.chatchannels.channels;

import me.sora819.chatchannels.config.ConfigAdapter;
import me.sora819.chatchannels.config.ConfigHandler;
import me.sora819.chatchannels.localization.LocalizationHandler;
import org.bukkit.configuration.MemorySection;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ChannelHandler {
    public static void createChannel(String channel) throws Exception {
        ConfigAdapter channelsData = ConfigHandler.channelsData;

        if (channelsData.get(channel) != null) {
            throw new Exception(LocalizationHandler.getMessage("channel.already_created", true) + channel);
        }

        channelsData.set(channel, Collections.emptyList());
    }

    public static void createChannel(String channel, Player player) throws Exception {
        ConfigAdapter channelsData = ConfigHandler.channelsData;

        if (channelsData.get(channel) != null) {
            throw new Exception(LocalizationHandler.getMessage("channel.already_created", true) + channel);
        }

        channelsData.set(channel, Set.of(player.getName()));
    }

    public static void deleteChannel(String channel) throws Exception {
        ConfigAdapter channelsData = ConfigHandler.channelsData;

        if (channelsData.get(channel) == null) {
            throw new Exception(LocalizationHandler.getMessage("channel.not_exist", true) + channel);
        }

        channelsData.set(channel, null);
    }

    public static Set<String> getChannelPlayers(String channel) {
        ConfigAdapter channelsData = ConfigHandler.channelsData;

        return new HashSet<>(channelsData.get(channel));
    }

    public static void addPlayerToChannel(String channel, Player player) throws Exception {
        ConfigAdapter channelsData = ConfigHandler.channelsData;

        if (channelsData.get(channel) == null) {
            throw new Exception(LocalizationHandler.getMessage("channel.already_active", true) + channel);
        }

        Set<String> playerList = new HashSet<>(channelsData.get(channel));

        if (playerList.contains(player.getName())) {
            throw new Exception(LocalizationHandler.getMessage("channel.already_joined", true) + channel);
        }

        playerList.add(player.getName());

        channelsData.set(channel, new ArrayList<>(playerList));
    }

    public static void removePlayerFromChannel(String channel, Player player) throws Exception {
        ConfigAdapter channelsData = ConfigHandler.channelsData;

        if (channelsData.get(channel) == null) {
            throw new Exception(LocalizationHandler.getMessage("channel.not_exist", true) + channel);
        }

        Set<String> playerList = new HashSet<>(channelsData.get(channel));
        playerList.remove(player.getName());

        channelsData.set(channel, new ArrayList<>(playerList));
    }

    public static void setActiveChannel(Player player, String channel) throws Exception {
        ConfigAdapter playersData = ConfigHandler.playerData;
        ConfigAdapter channelsData = ConfigHandler.channelsData;

        if (!channelsData.has(channel)) {
            throw new Exception(LocalizationHandler.getMessage("channel.not_exist", true) + channel);
        }

        if (!(new HashSet<>(channelsData.get(channel))).contains(player.getName())) {
            throw new Exception(LocalizationHandler.getMessage("channel.must_join", true));
        }

        if (playersData.has(player.getUniqueId().toString()) && playersData.get(player.getUniqueId().toString()).equals(channel)) {
            throw new Exception(LocalizationHandler.getMessage("channel.already_active", true) + channel);
        }

        playersData.set(player.getUniqueId().toString(), channel);
    }

    public static String getActiveChannel(Player player) {
        ConfigAdapter playersData = ConfigHandler.playerData;

        return playersData.get(player.getUniqueId().toString());
    }
}
