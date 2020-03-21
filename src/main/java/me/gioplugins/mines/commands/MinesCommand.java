package me.gioplugins.mines.commands;
import me.gioplugins.mines.Mines;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import java.util.Random;

public class MinesCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("join")) {
                    if(!(player.getWorld().getName().startsWith("mines"))) {
                        if (!(player.getWorld().getName().startsWith("lobbyMines"))) {
                            for (World world : Mines.lobbiesList) {
                                if (Mines.isLobbyFull.get(world.getName()) < Mines.config.getInt("maxNumberOfPlayers"))
                                {
                                    int x = new Random().nextInt((Mines.config.getIntegerList("lobbySpawnX").get(0) - Mines.config.getIntegerList("lobbySpawnX").get(1)) + 1) + Mines.config.getIntegerList("lobbySpawnX").get(1);
                                    int y = Mines.config.getInt("lobbySpawnY");
                                    int z = new Random().nextInt((Mines.config.getIntegerList("lobbySpawnZ").get(0) - Mines.config.getIntegerList("lobbySpawnZ").get(1)) + 1) + Mines.config.getIntegerList("lobbySpawnZ").get(1);
                                    player.sendMessage(ChatColor.GREEN + "Joining " + world.getName() + ".");
                                    Location location = new Location(world, x, y, z);
                                    player.teleport(location);
                                    break;
                                }
                            }
                        } else {
                            player.sendMessage(ChatColor.RED + "You are already on a game.");
                        }

                    }else{player.sendMessage(ChatColor.RED + "You are already on a game.") ;}

                } else if (args[0].equalsIgnoreCase("edit")) {

                    if (player.hasPermission("mines.edit")) {

                        World minesEdit = Mines.schem;
                        player.teleport(minesEdit.getSpawnLocation());

                    }else{player.sendMessage("you don't have permission to execute this command.");}

                } else if (args[0].equalsIgnoreCase("leave"))

                {
                    if(!(player.getWorld().getName().startsWith("mines")))
                    {
                        if (!(player.getWorld().getName().startsWith("lobbyMines-")))
                        {
                            player.sendMessage(ChatColor.RED + "You are not in a game of mines.");
                        }else
                        {
                            World spawn = Bukkit.getWorld("world");
                            player.sendMessage(ChatColor.GREEN + "Leaving " + player.getWorld().getName());
                            player.teleport(spawn.getSpawnLocation());
                        }
                    }
                    else
                    {
                        World spawn = Bukkit.getWorld("world");
                        player.sendMessage(ChatColor.GREEN + "Leaving " + player.getWorld().getName());
                        player.teleport(spawn.getSpawnLocation());
                    }
                }
            }
        }

        return true;
    }
}
