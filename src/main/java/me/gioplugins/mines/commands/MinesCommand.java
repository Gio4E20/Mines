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
                    if(!player.getWorld().getName().startsWith("mines-") || player.getWorld().getName().startsWith("lobbyMines-"))
                    {
                        for (World world : Mines.lobbiesList) {
                            if (!(Mines.isLobbyFull.get(world.getName()) == 6)) //Change Config
                            {
                                int x = new Random().nextInt((-240 - -246) + 1) + -246; //Change Config
                                int y = 7; // Change Config
                                int z = new Random().nextInt((16 - 9) + 1) + 9; //Change Config
                                player.sendMessage(ChatColor.GREEN + "Joining " + world.getName() + ".");
                                Location location = new Location(world, x, y, z);
                                player.teleport(location);
                                break;
                            }
                        }
                    }
                } else if (args[0].equalsIgnoreCase("edit")) {
                    if (player.hasPermission("mines.edit")) {
                        World minesEdit = Mines.schem;
                        player.teleport(minesEdit.getSpawnLocation());
                    }
                } else if (args[0].equalsIgnoreCase("leave"))
                {
                    World spawn = Bukkit.getWorld("world");
                    player.teleport(spawn.getSpawnLocation());
                }
            }
        }

        return true;
    }
}
