package de.thecitycrafter.hideAndSeek.commands;

import com.sun.tools.javac.Main;
import de.thecitycrafter.hideAndSeek.utils.secondsToTimer;
import de.thecitycrafter.hideAndSeek.HideAndSeek;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class HideAndSeekCommand implements CommandExecutor, TabExecutor {
    private static FileConfiguration connfick = HideAndSeek.getPlugin().getConfig();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player player){
            switch (args[0]){
                case "setlobby" -> setLobby(player);
                case "setgame" -> setGame(player);
                case "addseeker" -> addseeker(player, args);
                case "removeseeker" -> removeseeker(player, args);
                case "start" -> start(player, args);
            }
        }
        return true;
    }



    private static void setLobby(Player player){
        connfick.set("lobby", player.getLocation());
        HideAndSeek.getPlugin().saveConfig();
        player.sendMessage("§6§lModGames §8| §7Lobby erfolgreich gesetzt.");
    }

    private static void setGame(Player player){
        connfick.set("game", player.getLocation());
        HideAndSeek.getPlugin().saveConfig();
        player.sendMessage("§6§lModGames §8| §7Spiel erfolgreich gesetzt.");
    }

    private static void addseeker(Player player, String[] args){
        Player target = Bukkit.getPlayer(args[1]);
        if (target == null){
            player.sendMessage("§6§lModGames §8| §4Kein Spieler gefunden.");
            return;
        }
        target.addScoreboardTag("seeker");
        player.sendMessage("§6§lModGames §8| §7" + target.getDisplayName() + " ist nun ein Sucher");
    }

    private static void removeseeker(Player player, String[] args){
        Player target = Bukkit.getPlayer(args[1]);
        if (target == null){
            player.sendMessage("§6§lModGames §8| §4Kein Spieler gefunden.");
            return;
        }
        if (!target.getScoreboardTags().contains("seeker")){
            player.sendMessage("§6§lModGames §8| §4" + target.getDisplayName() + " ist kein Sucher.");
            return;
        }
        target.removeScoreboardTag("seeker");
        player.sendMessage("§6§lModGames §8| §7" + target.getDisplayName() + " ist kein Sucher mehr.");
    }


    private static void start(Player player, String[] args){
        try {
            int hideTime = Integer.parseInt(args[1]);
        } catch (Exception e) {
            player.sendMessage("§6§lModGames §8| §4" + args[1] + " ist keine Nummer!");
            return;
        }

        int hideTime = Integer.parseInt(args[1]);

        player.sendMessage(secondsToTimer.secondsToMin(hideTime).toString());

        for (Player i: Bukkit.getOnlinePlayers()){
            if (!player.getScoreboardTags().contains("seeker")){
                i.teleport(connfick.getLocation("game"));
            }
        }


    }






    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        List<String> tabcomplete = new ArrayList<>();
        if(args.length == 1){
            tabcomplete.add("setlobby");
            tabcomplete.add("setgame");
            tabcomplete.add("addseeker");
            tabcomplete.add("removeseeker");
            tabcomplete.add("start");
            tabcomplete.add("stop");
            tabcomplete.add("perks");
        }

        if (args.length == 2 && (args[0].equals("addseeker") || args[0].equals("removeseeker"))){
            for (Player i: Bukkit.getOnlinePlayers()){
                tabcomplete.add(i.getDisplayName());
            }
        }

        return tabcomplete;
    }
}
