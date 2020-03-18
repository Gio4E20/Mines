package me.gioplugins.mines.events;
import me.gioplugins.mines.Mines;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

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
                        Timer timer = new Timer();
                        TimerTask task = new TimerTask() {

                            int counter = 30 + 1; // Change to config

                            public void run() {
                                ArrayList<Location> locations = new ArrayList<Location>();

                                counter--;
                                for(Player target : world.getPlayers())
                                {
                                    if(counter == 30)
                                    {
                                        target.sendMessage(ChatColor.GOLD + "Game starting in " + counter + " seconds.");
                                    }
                                    else if (counter <= 15 && counter != 0) {
                                        target.sendMessage(ChatColor.GOLD + "Game starting in " + counter + " seconds.");
                                    }
                                    else if (counter == 0) {
                                        timer.cancel();
                                        target.sendMessage(ChatColor.GOLD + "The game has started.");
                                        for(World mine : Mines.minesList)
                                        {
                                            if(!(Mines.isMineInUse.get(mine.getName())))
                                            {
                                                locations.add(new Location(mine, -259, 7, 32));
                                                locations.add(new Location(mine , -259, 7 ,26));
                                                int indexOfTp = 0;
                                                target.teleport(locations.get(indexOfTp));
                                                indexOfTp ++;
                                            }
                                        }
                                }
                            }
                        }
                    };
                    timer.scheduleAtFixedRate(task, 1000, 1000);
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
