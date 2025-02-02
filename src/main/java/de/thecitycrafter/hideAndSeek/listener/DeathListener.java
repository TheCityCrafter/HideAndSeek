package de.thecitycrafter.hideAndSeek.listener;

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
            player.ban("§cDu wurdest gefunden! Danke fürs Mitspielen.", (Date) null, "2", true);

        }

    }


}
