package de.thecitycrafter.hideAndSeek;

import de.thecitycrafter.hideAndSeek.commands.HideAndSeekCommand;
import de.thecitycrafter.hideAndSeek.listener.DeathListener;
import de.thecitycrafter.hideAndSeek.listener.JoinListener;
import de.thecitycrafter.hideAndSeek.timer.TimerEngine;
import de.thecitycrafter.hideAndSeek.utils.Placeholder;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class HideAndSeek extends JavaPlugin {
    private static HideAndSeek plugin;

    @Override
    public void onEnable() {
        new Placeholder().register();

        plugin = this;

        this.saveDefaultConfig();


        getCommand("hideandseek").setExecutor(new HideAndSeekCommand());


        getServer().getPluginManager().registerEvents(new DeathListener(), this);
        getServer().getPluginManager().registerEvents(new JoinListener(), this);

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, TimerEngine::showTimer, 1L, 1L);
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, TimerEngine::TimerEngine, 20L, 20L);


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }


    public static Plugin getPlugin() {
        return plugin;
    }

    public static FileConfiguration getPluginConfig() {
        return getPlugin().getConfig();
    }
}
