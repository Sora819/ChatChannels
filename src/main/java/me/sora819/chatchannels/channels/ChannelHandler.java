package me.sora819.chatchannels.channels;

import me.sora819.chatchannels.ChatChannelsPlugin;
import me.sora819.chatchannels.config.ConfigAdapter;
import me.sora819.chatchannels.config.ConfigHandler;
import me.sora819.chatchannels.localization.LocalizationHandler;
import org.bukkit.entity.Player;

import java.util.*;

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

        channelsData.set(channel, Set.of(player.getUniqueId().toString()));
    }

    public static void deleteChannel(String channel) throws Exception {
        ConfigAdapter channelsData = ConfigHandler.channelsData;

        if (channelsData.get(channel) == null) {
            throw new Exception(LocalizationHandler.getMessage("channel.not_exist", true) + channel);
        }

        for (Player player : getChannelPlayers(channel)) {
            removePlayerFromChannel(channel, player);
        }

        channelsData.set(channel, null);
    }

    public static Set<Player> getChannelPlayers(String channel) {
        ConfigAdapter channelsData = ConfigHandler.channelsData;

        Set<String> playerUUIDs = new HashSet<>(channelsData.get(channel));

        return new HashSet<>(playerUUIDs.stream()
            .map(
                uuid -> ChatChannelsPlugin.getInstance().getServer().getPlayer(UUID.fromString(uuid))
            ).toList()
        );
    }

    public static void addPlayerToChannel(String channel, Player player) throws Exception {
        ConfigAdapter channelsData = ConfigHandler.channelsData;

        if (channelsData.get(channel) == null) {
            throw new Exception(LocalizationHandler.getMessage("channel.not_exist", true) + channel);
        }

        Set<String> playerList = new HashSet<>(channelsData.get(channel));

        if (playerList.contains(player.getUniqueId().toString())) {
            throw new Exception(LocalizationHandler.getMessage("channel.already_joined", true) + channel);
        }

        playerList.add(player.getUniqueId().toString());

        channelsData.set(channel, new ArrayList<>(playerList));
    }

    public static void removePlayerFromChannel(String channel, Player player) throws Exception {
        ConfigAdapter channelsData = ConfigHandler.channelsData;
        ConfigAdapter playersData = ConfigHandler.playersData;

        if (channelsData.get(channel) == null) {
            throw new Exception(LocalizationHandler.getMessage("channel.not_exist", true) + channel);
        }

        Set<String> playerList = new HashSet<>(channelsData.get(channel));
        playerList.remove(player.getUniqueId().toString());

        channelsData.set(channel, new ArrayList<>(playerList));

        if (playersData.get(player.getUniqueId().toString()).equals(channel)) {
            if (getChannels(player).contains("default_channel")) {
                playersData.set(player.getUniqueId().toString(), ConfigHandler.defaultConfig.get("default_channel"));
            } else if (!getChannels(player).isEmpty()) {
                playersData.set(player.getUniqueId().toString(), new ArrayList<>(getChannels(player)).getFirst());
            } else {
                playersData.set(player.getUniqueId().toString(), null);
            }
        }
    }

    public static void setActiveChannel(Player player, String channel) throws Exception {
        ConfigAdapter playersData = ConfigHandler.playersData;
        ConfigAdapter channelsData = ConfigHandler.channelsData;

        if (!channelsData.has(channel)) {
            throw new Exception(LocalizationHandler.getMessage("channel.not_exist", true) + channel);
        }

        if (!(new HashSet<>(channelsData.get(channel))).contains(player.getUniqueId().toString())) {
            throw new Exception(LocalizationHandler.getMessage("channel.must_join", true));
        }

        if (playersData.has(player.getUniqueId().toString()) && playersData.get(player.getUniqueId().toString()).equals(channel)) {
            throw new Exception(LocalizationHandler.getMessage("channel.already_active", true) + channel);
        }

        playersData.set(player.getUniqueId().toString(), channel);
    }

    public static String getActiveChannel(Player player) {
        ConfigAdapter playersData = ConfigHandler.playersData;

        return playersData.get(player.getUniqueId().toString());
    }

    public static Set<String> getChannels() {
        ConfigAdapter channelsData = ConfigHandler.channelsData;

        return channelsData.getKeys();
    }


    public static Set<String> getChannels(Player player) {
        ConfigAdapter channelsData = ConfigHandler.channelsData;
        Set<String> channels = getChannels();

        channels.removeIf(channel -> !(new HashSet<>(channelsData.get(channel))).contains(player.getUniqueId().toString()));

        return channels;
    }
}
