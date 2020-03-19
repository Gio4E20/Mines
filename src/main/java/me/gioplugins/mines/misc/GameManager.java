package me.gioplugins.mines.misc;

import com.sun.tools.javac.util.List;
import me.gioplugins.mines.Mines;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class GameManager {

    //public static HashMap<String, String> playerMine = new HashMap<String,String>();
    public static HashMap<String, Integer> diamondQuantity = new HashMap<String,Integer>();
    private static Plugin plugin = Mines.getPlugin(Mines.class);

    public static void start(World world)
    {
        ArrayList<Location> locations = new ArrayList<>();

        for (World mine : Mines.minesList)
        {
            if (!(Mines.isMineInUse.get(mine.getName())))
            {
                generateOres(mine);
                Mines.isMineInUse.remove(mine.getName());
                Mines.isMineInUse.put(mine.getName(), true);

                locations.add(new Location(mine, -259, 7 , 32, 92, 4));
                locations.add(new Location(mine, -259, 7 , 26, 92 , 4));
                Random random = new Random();

                for(Player target : world.getPlayers())
                {
                    Location spawn = locations.get(random.nextInt(locations.size()));
                    target.sendMessage(spawn.toString() + " //" + target.toString());
                    target.teleport(spawn);
                    locations.remove(spawn);

                    ItemStack pickaxe = new ItemStack(Material.IRON_PICKAXE);
                    ItemMeta pickaxe_meta = pickaxe.getItemMeta();
                    pickaxe_meta.setUnbreakable(true);
                    pickaxe.setItemMeta(pickaxe_meta);
                    target.getInventory().addItem(pickaxe);

                    diamondQuantity.put(target.getUniqueId().toString(), 0);
                    //playerMine.put(target.getUniqueId().toString(), mine.getName());
                }
                break;
            }
        }
    }
    public static void Lobby(World world)
    {
        new BukkitRunnable() {
            int lobbyCounter = 5 + 1; // Change to config
            @Override
            public void run() {
                lobbyCounter--;
                for (Player target : world.getPlayers()) {
                    if(lobbyCounter >= 0) {
                        if (lobbyCounter == 30) { // Change config
                            target.sendMessage(ChatColor.GOLD + "Game starting in " + lobbyCounter + " seconds.");
                        } else if (lobbyCounter <= 15 && lobbyCounter != 0) {
                            target.sendMessage(ChatColor.GOLD + "Game starting in " + lobbyCounter + " seconds.");
                        } else if (lobbyCounter == 0) {
                            this.cancel();
                            target.sendMessage(ChatColor.GOLD + "The game has started.");
                            for (World mine : Mines.minesList) {
                                if (!(Mines.isMineInUse.get(mine.getName()))) {
                                    start(world);
                                }
                            }
                        }
                    }
                }
            }
        }.runTaskTimer(plugin , 0, 20);
    }

    public static void generateOres(World mine)
    {

        //Change to config
        double x = -331;
        double y = 1;
        double z = 0;

        double xb = -271;
        double yb = 11;
        double zb = 35;
        //change to config
        for (int xx = (int) x; xx < xb; xx++) {
            for (int yy = (int) y; yy < yb; yy++) {
                for (int zz = (int) z; zz < zb; zz++) {
                    Block block = mine.getBlockAt(xx, yy, zz);
                    if (block != null) {

                        if (block.getType().equals(Material.STONE)) {
                            int random = new Random().nextInt((100 - 0) + 1) + 0;
                            if(random == 1 )
                            {
                                block.setType(Material.DIAMOND_ORE);
                            }
                            else if(random > 1 && random < 12 )
                            {
                                block.setType(Material.GOLD_ORE);
                            }
                            else if(random > 12 && random < 23 )
                            {
                                block.setType(Material.NETHER_QUARTZ_ORE);
                            }
                            else if(random > 23 && random < 34 )
                            {
                                block.setType(Material.COAL_ORE);
                            }
                            else if(random > 34 && random < 45 )
                            {
                                block.setType(Material.LAPIS_ORE);
                            }
                            else if(random > 45 && random < 56 )
                            {
                                block.setType(Material.STONE);
                            }
                            else if (random > 56 && random < 67 )
                            {
                                block.setType(Material.DIRT);
                            }
                            else if (random > 67 && random < 78 )
                            {
                                block.setType(Material.REDSTONE_ORE);
                            }
                            else if(random > 78 && random <= 100)
                            {
                                block.setType(Material.EMERALD_ORE);
                            }
                        }
                    }
                }
            }
        }
    }

    public static void stopGame(World mines, Player winner)
    {
        Mines.isFinished.remove(mines.getName());
        Mines.isFinished.put(mines.getName(), true);

        for(Player target : mines.getPlayers())
        {
            target.sendMessage(ChatColor.YELLOW + "Match Complete!");
            target.sendMessage(ChatColor.YELLOW + winner.getDisplayName() + ChatColor.GOLD + " Got the diamonds");
            target.getInventory().clear();
            target.teleport(Bukkit.getWorld("world").getSpawnLocation());
        }

        Mines.unloadWorld(mines);
        Mines.copyWorld(Mines.schem, mines.getName());
        Mines.isFinished.remove(mines.getName());
        Mines.isFinished.put(mines.getName(), false);
        Mines.isMineInUse.remove(mines.getName());
        Mines.isMineInUse.put(mines.getName(), false);
        for(int i = 0 ; i < Mines.minesList.size(); i++)
        {
            if(Mines.minesList.get(i).getName().equals(mines.getName()))
            {
                Mines.minesList.remove(i);
                Mines.minesList.add(mines);
            }
        }
    }
}
