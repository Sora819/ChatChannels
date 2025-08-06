package me.sora819.chatchannels.config;

import me.sora819.chatchannels.localization.LocalizationHandler;

public class ConfigHandler {
    public static ConfigAdapter defaultConfig = new DefaultConfig();
    public static ConfigAdapter defaultMessagesConfig = new LocalizationConfig("en");
    public static ConfigAdapter messagesConfig = new LocalizationConfig(defaultConfig.get("language"));
    public static ConfigAdapter channelsData = new CustomConfig("data/channels.yml");
    public static ConfigAdapter playersData = new CustomConfig("data/players.yml");

    public static void reloadConfigurations() {
        defaultConfig.reload();
        defaultMessagesConfig.reload();
        channelsData.reload();
        playersData.reload();

        messagesConfig = new LocalizationConfig(defaultConfig.get("language"));
        LocalizationHandler.messagesConfig = messagesConfig;
    }
}
