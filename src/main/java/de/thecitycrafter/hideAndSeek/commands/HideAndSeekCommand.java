package de.thecitycrafter.hideAndSeek.commands;

import de.thecitycrafter.hideAndSeek.HideAndSeek;
import de.thecitycrafter.hideAndSeek.timer.TimerEngine;
import de.thecitycrafter.hideAndSeek.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class HideAndSeekCommand implements CommandExecutor, TabExecutor {
    private static FileConfiguration connfick = HideAndSeek.getPlugin().getConfig();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player player) {
            switch (args[0]) {
                case "setlobby" -> setLobby(player);
                case "setgame" -> setGame(player);
                case "addseeker" -> addseeker(player, args);
                case "removeseeker" -> removeseeker(player, args);
                case "start" -> start(player, args);
                case "stop" -> stop(args);
                case "perks" -> perks(args);
            }
        }
        return true;
    }


    private static void setLobby(Player player) {
        connfick.set("lobby", player.getLocation());
        HideAndSeek.getPlugin().saveConfig();
        player.sendMessage("§6§lModGames §8| §7Lobby erfolgreich gesetzt.");
    }

    private static void setGame(Player player) {
        connfick.set("game", player.getLocation());
        HideAndSeek.getPlugin().saveConfig();
        player.sendMessage("§6§lModGames §8| §7Spiel erfolgreich gesetzt.");
    }

    private static void addseeker(Player player, String[] args) {
        Player target = Bukkit.getPlayer(args[1]);
        if (target == null) {
            player.sendMessage("§6§lModGames §8| §4Kein Spieler gefunden.");
            return;
        }
        target.addScoreboardTag("seeker");
        player.sendMessage("§6§lModGames §8| §7" + target.getDisplayName() + " ist nun ein Sucher");
    }

    private static void removeseeker(Player player, String[] args) {
        Player target = Bukkit.getPlayer(args[1]);
        if (target == null) {
            player.sendMessage("§6§lModGames §8| §4Kein Spieler gefunden.");
            return;
        }
        if (!target.getScoreboardTags().contains("seeker")) {
            player.sendMessage("§6§lModGames §8| §4" + target.getDisplayName() + " ist kein Sucher.");
            return;
        }
        target.removeScoreboardTag("seeker");
        player.sendMessage("§6§lModGames §8| §7" + target.getDisplayName() + " ist kein Sucher mehr.");
    }


    private static void start(Player player, String[] args) {
        try {
            int hideTime = Integer.parseInt(args[1]);
        } catch (Exception e) {
            player.sendMessage("§6§lModGames §8| §4" + args[1] + " ist keine Nummer!");
            return;
        }
        int hideTime = Integer.parseInt(args[1]);
        TimerEngine.setStartTime(hideTime);
        for (Player i : Bukkit.getOnlinePlayers()) {
            i.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, PotionEffect.INFINITE_DURATION, 255, false, false, false));
            i.setGameMode(GameMode.ADVENTURE);
            if (!i.getScoreboardTags().contains("seeker")) {
                i.setRespawnLocation(connfick.getLocation("game"), true);
                i.teleport(connfick.getLocation("game"));
                i.sendMessage("§6§lModGames §8| §7Die Versteckzeit hat gestartet! Du hast nun: " + (connfick.getInt(
                        "timer.min") + connfick.getInt("timer.hrs") * 60) + " Minuten und " + connfick.getInt(
                        "timer.sec") + " Sekunden Zeit. Verstecke dich gut!");
                i.setSaturation(20);
                i.setHealth(20);
                ItemStack hoe = new ItemBuilder(Material.WOODEN_HOE,1, (byte) 59).addEnchant(Enchantment.KNOCKBACK, 10).setName("§r§cDer Klopper" ).toItemStack();
                i.getInventory().setItemInMainHand(hoe);
            }else{
                i.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, hideTime*20, 255, false, false, false));
                i.setSaturation(20);
                i.setHealth(20);
                i.setRespawnLocation(connfick.getLocation("lobby"), true);
            }
        }

    }

    private static void stop(String[] args) {
        connfick.set("timer.sec", 0);
        connfick.set("timer.min", 0);
        connfick.set("timer.hrs", 0);
        connfick.set("started", false);
        HideAndSeek.getPlugin().saveConfig();
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.teleport(connfick.getLocation("lobby"));
            p.getInventory().clear();
            p.sendMessage("§6§lModGames §8| §7Das Spiel wurde gestoppt. Du wirst zur Lobby zurück teleportiert.");
        }
    }


    private static void perks( String[] args) {
        switch (args[1]) {
            case "glowing": {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, Integer.valueOf(args[2])*20, 0, false, true, true));
                }
                break;
            }
            case "speed": {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (p.getScoreboardTags().contains("seeker")) {
                        p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.valueOf(args[2])*20, 2, false, false, true));
                    }
                }
                break;
            }
            case "fly": {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (p.getScoreboardTags().contains("seeker")) {
                        p.setAllowFlight(!p.getAllowFlight());
                    }
                }
                break;
            }
        }
    }


    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        List<String> tabcomplete = new ArrayList<>();
        if (args.length == 1) {
            tabcomplete.add("setlobby");
            tabcomplete.add("setgame");
            tabcomplete.add("addseeker");
            tabcomplete.add("removeseeker");
            tabcomplete.add("start");
            tabcomplete.add("stop");
            tabcomplete.add("perks");
        }

        if (args.length == 2 && (args[0].equals("addseeker") || args[0].equals("removeseeker"))) {
            for (Player i : Bukkit.getOnlinePlayers()) {
                tabcomplete.add(i.getDisplayName());
            }
        }
        if (args.length == 2 && args[0].equals("perks")) {
            tabcomplete.add("glowing");
            tabcomplete.add("fly");
            tabcomplete.add("speed");
        }

        return tabcomplete;
    }
}
