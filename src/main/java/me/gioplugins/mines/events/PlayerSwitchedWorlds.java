package me.gioplugins.mines.events;
import me.gioplugins.mines.Mines;
import me.gioplugins.mines.misc.GameManager;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

public class PlayerSwitchedWorlds implements Listener {



    @EventHandler
    public void event(PlayerChangedWorldEvent e) {
        Player player = e.getPlayer();
        World world = player.getWorld();
        if (player.getWorld().getName().startsWith("lobbyMines-")) {
            int playerQuantity = Mines.isLobbyFull.get(world.getName()) + 1;
            if (playerQuantity != 6) //Change To Config Later
            {
                Mines.isLobbyFull.remove(player.getWorld().getName());
                Mines.isLobbyFull.put(player.getWorld().getName(), playerQuantity);
                for (Player target : player.getWorld().getPlayers()) {
                    target.sendMessage(ChatColor.GOLD + player.getName() + " Joined the game " + ChatColor.GRAY + "(0" + (playerQuantity) + "/06)"); //Change To Config Later
                }
                if (playerQuantity == 2)//Change To Config Later
                {
                    GameManager.Lobby(world);
                }
                else if(playerQuantity > 2)
                {
                    for (Player target : player.getWorld().getPlayers())
                    {
                        target.sendMessage(ChatColor.GOLD + player.getName() + " Joined the game " + ChatColor.GRAY + "(0" + (playerQuantity) + "/06)"); //Change To Config Later
                    }
                }
            }
        }

        else if(e.getFrom().getName().startsWith("lobbyMines-"))
        {
            int playerQuantity = Mines.isLobbyFull.get(e.getFrom().getName()) -1;
            Mines.isLobbyFull.remove(e.getFrom().getName());
            Mines.isLobbyFull.put(e.getFrom().getName(), playerQuantity);
            for(Player target : e.getFrom().getPlayers())
            {
                target.sendMessage(ChatColor.GOLD + player.getName() + " Left the game " + ChatColor.GRAY + "(0" + playerQuantity + "/06)"); //Change To Config Later
            }
        }
    }
}
