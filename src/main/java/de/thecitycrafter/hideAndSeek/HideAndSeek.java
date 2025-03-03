package de.thecitycrafter.hideAndSeek;

import de.thecitycrafter.hideAndSeek.commands.HideAndSeekCommand;
import de.thecitycrafter.hideAndSeek.listener.DamageListener;
import de.thecitycrafter.hideAndSeek.listener.DeathListener;
import de.thecitycrafter.hideAndSeek.timer.TimerEngine;
import de.thecitycrafter.hideAndSeek.utils.Placeholder;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public final class HideAndSeek extends JavaPlugin {
    private static HideAndSeek plugin;
    private static File langFile;
    private static FileConfiguration lang;


    @Override
    public void onEnable() {

        new Placeholder().register();

        plugin = this;

        this.saveDefaultConfig();
        this.createLangConfig();


        getCommand("hideandseek").setExecutor(new HideAndSeekCommand());


        getServer().getPluginManager().registerEvents(new DeathListener(), this);
        getServer().getPluginManager().registerEvents(new DamageListener(), this);

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, TimerEngine::showTimer, 1L, 1L);
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, TimerEngine::TimerEngine, 20L, 20L);


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }


    public static FileConfiguration getLang() {
        return lang;
    }

    private void createLangConfig() {
        langFile = new File(getDataFolder(), "lang.yml");
        if (!langFile.exists()) {
            langFile.getParentFile().mkdirs();
            saveResource("lang.yml", false);
        }

        lang = new YamlConfiguration();
        try {
            lang.load(langFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }


    public static Plugin getPlugin() {
        return plugin;
    }

    public static FileConfiguration getPluginConfig() {
        return getPlugin().getConfig();
    }
}
