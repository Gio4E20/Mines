package me.gioplugins.mines.events;
import org.bukkit.plugin.Plugin;
import me.gioplugins.mines.Mines;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.scheduler.BukkitScheduler;

public class PlayerSwitchedWorlds implements Listener {

    @EventHandler
    public void event(PlayerChangedWorldEvent e){
        Player player = e.getPlayer();
        int playerQuantity = Mines.isLobbyFull.get(player.getWorld().getName());
        if(player.getWorld().getName().startsWith("lobby-")){
            if (playerQuantity != 6) { //Change To Config Later
                Mines.isLobbyFull.remove(player.getWorld().getName());
                Mines.isLobbyFull.put(player.getWorld().getName(), playerQuantity + 1);
                for(Player target : player.getWorld().getPlayers()){
                    target.sendMessage(ChatColor.GOLD + player.getName() + " Joined the game " + ChatColor.GRAY + "(0" + playerQuantity + "/06"); //Change To Config Later
                }
                if(playerQuantity >= 3 && playerQuantity != 6){ //Change To Config Later
                    for(Player target : player.getWorld().getPlayers()) {
                        target.sendMessage(ChatColor.GOLD + "Game starting in 30 seconds."); //Change to Config Later
                        //Start countdown
                    }
                }
            }
        }
        else if(e.getFrom().getName().startsWith("lobby-")){
            Mines.isLobbyFull.remove(e.getFrom().getName());
            Mines.isLobbyFull.put(e.getFrom().getName(), playerQuantity - 1);
        }
    }
}
