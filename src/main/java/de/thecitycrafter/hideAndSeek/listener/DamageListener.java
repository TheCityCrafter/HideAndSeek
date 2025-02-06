package de.thecitycrafter.hideAndSeek.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class DamageListener implements Listener {
    @EventHandler
    public void onDamage(EntityDamageEvent event){
        if (event.getEntity() instanceof Player target && event.getDamageSource().getCausingEntity() instanceof Player sender){
            if (target.getScoreboardTags().contains("seeker") && sender.getScoreboardTags().contains("seeker")){
                event.setCancelled(true);
                sender.sendMessage("§6§lModGames §8| §c" + target.getDisplayName() + " ist ein Sucher.");
                return;
            }
            if (!target.getScoreboardTags().contains("seeker") && !sender.getScoreboardTags().contains("seeker")){
                event.setCancelled(true);
            }
        }
    }
}
