package me.sora819.chatchannels.config;

import me.sora819.chatchannels.ChatChannelsPlugin;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Set;

public class DefaultConfig implements ConfigAdapter{
    public DefaultConfig() {
        ChatChannelsPlugin.getInstance().saveDefaultConfig();

        addDefaults();
    }

    private void addDefaults() {
        InputStream defaultFile = ChatChannelsPlugin.getInstance().getResource("config.yml");

        if (defaultFile != null) {
            FileConfiguration defaults = YamlConfiguration.loadConfiguration(
                    new InputStreamReader(defaultFile, StandardCharsets.UTF_8));

            ChatChannelsPlugin.getInstance().getConfig().addDefaults(defaults);
            ChatChannelsPlugin.getInstance().getConfig().options().copyDefaults(true);
            save();
        }
    }

    @Override
    public void reload() {
        ChatChannelsPlugin.getInstance().reloadConfig();

        addDefaults();
    }

    @Override
    public void save() {
        ChatChannelsPlugin.getInstance().saveConfig();
    }

    @Override
    public <T> T get(String path) {
        return (T) ChatChannelsPlugin.getInstance().getConfig().get(path);
    }

    @Override
    public <T> T get(String path, T default_value) {
        return get(path) != null ? get(path) : default_value;
    }

    @Override
    public <T> void set(String path, T value) {
        ChatChannelsPlugin.getInstance().getConfig().set(path, value);
        save();
    }

    @Override
    public boolean has(String path) {
        return ChatChannelsPlugin.getInstance().getConfig().contains(path);
    }

    public Set<String> getKeys() {
        return ChatChannelsPlugin.getInstance().getConfig().getKeys(false);
    }

    public Set<String> getKeys(String path) {
        ConfigurationSection section = ChatChannelsPlugin.getInstance().getConfig().getConfigurationSection(path);

        if (section != null) {
            return section.getKeys(false);
        } else {
            return Set.of();
        }
    }
}
