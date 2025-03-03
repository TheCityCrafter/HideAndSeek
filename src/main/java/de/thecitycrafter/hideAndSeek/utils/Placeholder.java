package de.thecitycrafter.hideAndSeek.utils;

import de.thecitycrafter.hideAndSeek.HideAndSeek;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Placeholder extends PlaceholderExpansion {

    @Override
    public @NotNull String getIdentifier() {
        return "hideandseek";
    }

    @Override
    public @NotNull String getAuthor() {
        return "TheCityCrafter";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.0";
    }
    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onRequest(OfflinePlayer player, @NotNull String params) {
        switch (params){
            case "seekers" -> {
                return seekers();
            }
            case "hiders" -> {
                return hiders();
            }
            case "timer_sec" ->{
                return String.valueOf(HideAndSeek.getPluginConfig().getInt("timer.sec"));
            }
            case "timer_min" ->{
                return String.valueOf(HideAndSeek.getPluginConfig().getInt("timer.min"));
            }
            case "timer_hrs" ->{
                return String.valueOf(HideAndSeek.getPluginConfig().getInt("timer.hrs"));
            }
            case "" -> {
                return "Available placeholders: %hideandseek_seekers%, %hideandseek_hiders%, %hideandseek_timer_sec%, %hideandseek_timer_min%, %hideandseek_timer_hrs%";
            }
        }
        return null;
    }



    private static String seekers(){
        int count = 0;
        for(Player p :Bukkit.getOnlinePlayers()){
            if(p.getScoreboardTags().contains("seeker")){
                count++;
            }
        }
        return String.valueOf(count);
    }


    private static String hiders(){
        int count = 0;
        for(Player p :Bukkit.getOnlinePlayers()){
            if(!p.getScoreboardTags().contains("seeker")){
                count++;
            }
        }
        return String.valueOf(count);
    }
}
