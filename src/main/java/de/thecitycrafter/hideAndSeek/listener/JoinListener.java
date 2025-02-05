package de.thecitycrafter.hideAndSeek.listener;

import de.thecitycrafter.hideAndSeek.HideAndSeek;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Objects;

public class JoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (HideAndSeek.getPluginConfig().getBoolean("started")){
            event.getPlayer().teleport(Objects.requireNonNull(HideAndSeek.getPluginConfig().getLocation("game")));
        }else {
            event.getPlayer().teleport(Objects.requireNonNull(HideAndSeek.getPluginConfig().getLocation("lobby")));
        }

    }
}
