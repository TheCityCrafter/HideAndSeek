package de.thecitycrafter.hideAndSeek.listener;

import de.thecitycrafter.hideAndSeek.HideAndSeek;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.Date;


public class DeathListener implements Listener {
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        if (!player.getScoreboardTags().contains("seeker")) {
            event.setDeathMessage("§6§lModGames §8| §4" + player.getDisplayName() + " wurde gefunden!");
            int place = 1;
            for (Player p: Bukkit.getOnlinePlayers()){
                if (!p.getScoreboardTags().contains("seeker")){
                   place++;
                }
            }

            HideAndSeek.getPluginConfig().set("leaderboard." + player.getDisplayName(), place-1);
            HideAndSeek.getPlugin().saveConfig();
            player.ban("§cDu wurdest gefunden! Danke fürs Mitspielen. Deine Platzierung: " + HideAndSeek.getPluginConfig().getString("leaderboard." + player.getDisplayName()), (Date) null, "2", true);
        }

    }


}
