package de.thecitycrafter.hideAndSeek;

import de.thecitycrafter.hideAndSeek.commands.HideAndSeekCommand;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class HideAndSeek extends JavaPlugin {
    private static HideAndSeek plugin;
    @Override
    public void onEnable() {
        plugin = this;

        this.saveDefaultConfig();


        getCommand("hideandseek").setExecutor(new HideAndSeekCommand());

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }


    public static Plugin getPlugin() {
        return plugin;
    }
}
