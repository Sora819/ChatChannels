package me.sora819.chatchannels;

import me.sora819.chatchannels.localization.LocalizationHandler;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;

import java.util.Set;

public final class ChatChannelsPlugin extends JavaPlugin {
    private static ChatChannelsPlugin instance;

    public ChatChannelsPlugin() {
        instance = this;
    }

    @Override
    public void onEnable() {
        setCommandExecutors();
        registerListeners();

        getLogger().info(LocalizationHandler.getMessage("plugin.enabled"));
    }

    @Override
    public void onDisable() {
        getLogger().info(LocalizationHandler.getMessage("plugin.disabled"));
    }

    static public ChatChannelsPlugin getInstance(){
        return instance;
    }

    /**
     * Dynamically try to set the CommandExecutors for the commands registered in plugin.yml
     */
    private void setCommandExecutors() {
        for (String cmdName : getDescription().getCommands().keySet()) {
            try {

                Class<?> clazz = Class.forName(
                    this.getClass().getPackage().getName() + ".commands." +
                    cmdName.substring(0, 1).toUpperCase() +
                    cmdName.substring(1) +
                    "Command");

                CommandExecutor executor = (CommandExecutor) clazz.getDeclaredConstructor().newInstance();

                getCommand(cmdName).setExecutor(executor);
                getLogger().info(LocalizationHandler.getMessage("success.command_load") + cmdName);

            } catch (Exception e) {
                getLogger().warning(LocalizationHandler.getMessage("error.command_load") + cmdName);
                getLogger().warning(e.toString());
            }
        }
    }

    /**
     * Dynamically try to register all event listeners
     */
    private void registerListeners() {
        PluginManager pluginManager = getServer().getPluginManager();

        Reflections reflections = new Reflections(getClass().getPackage().getName());

        Set<Class<? extends Listener>> listenerClasses = reflections.getSubTypesOf(Listener.class);

        for (Class<? extends Listener> clazz : listenerClasses) {
            try {
                Listener listener = clazz.getDeclaredConstructor().newInstance();
                pluginManager.registerEvents(listener, this);
                getLogger().info(LocalizationHandler.getMessage("success.listener_load") + clazz.getSimpleName());
            } catch (Exception e) {
                getLogger().warning(LocalizationHandler.getMessage("error.listener_load") + clazz.getSimpleName());
                getLogger().warning(e.toString());
            }
        }
    }
}
