package de.thecitycrafter.hideAndSeek.timer;

import de.thecitycrafter.hideAndSeek.HideAndSeek;
import de.thecitycrafter.hideAndSeek.utils.ItemBuilder;
import de.thecitycrafter.hideAndSeek.utils.secondsToTimer;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class TimerEngine {


    public static void setStartTime(int sec) {
        List<Integer> timerValues = secondsToTimer.secondsToTimer(sec);
        FileConfiguration config = HideAndSeek.getPluginConfig();
        config.set("timer.sec", timerValues.get(2));
        config.set("timer.min", timerValues.get(1));
        config.set("timer.hrs", timerValues.get(0));
        config.set("started", true);
        config.set("timer.countdown", true);
        HideAndSeek.getPlugin().saveConfig();
    }


    public static void showTimer() {
        if (HideAndSeek.getPluginConfig().getBoolean("started")) {
            if (HideAndSeek.getPluginConfig().getBoolean("timer.countdown") && HideAndSeek.getPluginConfig().getInt("timer.sec") == 0 && HideAndSeek.getPluginConfig().getInt("timer.min") == 0 && HideAndSeek.getPluginConfig().getInt("timer.hrs") == 0){
                HideAndSeek.getPluginConfig().set("timer.countdown", false);
                HideAndSeek.getPlugin().saveConfig();
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (p.getScoreboardTags().contains("seeker")){
                        p.teleport(HideAndSeek.getPluginConfig().getLocation("game"));
                        p.sendMessage("§6§lModGames §8| §7Die Sucherzeit hat gestartet! Du kannst nun suchen.");
                        ItemStack sword = new ItemBuilder(Material.DIAMOND_SWORD,1, (byte) 0).setName("§bDer Aufräumer").toItemStack();
                        p.getInventory().setItem(0,sword);
                    }else{
                        p.sendMessage("§6§lModGames §8| §7Die Sucherzeit hat gestartet! Viel Glück.");
                    }
                }
            }


            for (Player p : Bukkit.getOnlinePlayers()) {
                p.spigot().sendMessage(
                        ChatMessageType.ACTION_BAR,
                        TextComponent.fromLegacy("§6" + HideAndSeek.getPluginConfig()
                                .getInt("timer.hrs") + "h " + HideAndSeek.getPluginConfig()
                                .getInt("timer.min") + "m " + HideAndSeek.getPluginConfig().getInt("timer.sec") + "s")
                );
            }
        } else {
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacy("§6--- Warte auf Start ---"));
            }
        }
    }


    public static void TimerEngine() {
        FileConfiguration config = HideAndSeek.getPluginConfig();
        if (!HideAndSeek.getPluginConfig().getBoolean("timer.countdown")){
            if (config.getBoolean("started")) {
                HideAndSeek.getPluginConfig().set("timer.sec", HideAndSeek.getPluginConfig().getInt("timer.sec") + 1);
                if (config.getInt("timer.sec") >= 60) {
                    config.set("timer.sec", 0);
                    config.set("timer.min", config.getInt("timer.min") + 1);
                }
                if (config.getInt("timer.min") >= 60) {
                    config.set("timer.min", 0);
                    config.set("timer.hrs", config.getInt("timer.hrs") + 1);
                }
                HideAndSeek.getPlugin().saveConfig();
            }
        }else{
            if (config.getBoolean("started")) {
                if (HideAndSeek.getPluginConfig().getInt("timer.sec") == 0 && HideAndSeek.getPluginConfig()
                        .getInt("timer.min") == 0 && HideAndSeek.getPluginConfig().getInt("timer.hrs") == 0) {
                    return;
                }
                HideAndSeek.getPluginConfig().set("timer.sec", HideAndSeek.getPluginConfig().getInt("timer.sec") - 1);
                if (config.getInt("timer.sec") <= -1) {
                    config.set("timer.sec", 59);
                    config.set("timer.min", config.getInt("timer.min") - 1);
                }
                if (config.getInt("timer.min") <= -1) {
                    config.set("timer.min", 59);
                    config.set("timer.hrs", config.getInt("timer.hrs") - 1);
                }
                HideAndSeek.getPlugin().saveConfig();
            }
        }
    }

}
