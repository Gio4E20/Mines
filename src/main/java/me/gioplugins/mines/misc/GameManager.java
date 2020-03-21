package me.gioplugins.mines.misc;

import me.gioplugins.mines.Mines;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class GameManager {

    //public static HashMap<String, String> playerMine = new HashMap<String,String>();
    public static HashMap<String, Integer> diamondQuantity = new HashMap<String,Integer>();
    private static Plugin plugin = Mines.getPlugin(Mines.class);

    public static void start(World lobby) {
        ArrayList<Location> locations = new ArrayList<>();

        for(World mine : Bukkit.getServer().getWorlds()) {

            if (mine.getName().startsWith("mines-")) {

                if (!(Mines.isMineInUse.get(mine.getName()))) {

                    for(int i = 0 ; i < Mines.config.getInt("maxNumberOfPlayers"); i++)
                    {
                        int x = Mines.config.getIntegerList("spawnsX").get(i);
                        int y = Mines.config.getIntegerList("spawnsY").get(i);
                        int z = Mines.config.getIntegerList("spawnsZ").get(i);
                        int yaw = Mines.config.getInt("yaw");
                        int pitch = Mines.config.getInt("pitch");

                        locations.add(new Location(mine, x, y, z, yaw, pitch));
                    }
                    generateOres(mine);
                    Mines.isMineInUse.remove(mine.getName());
                    Mines.isMineInUse.put(mine.getName(), true);

                    Random random = new Random();


                    for (Player target : lobby.getPlayers()) {

                        Location spawn = locations.get(random.nextInt(locations.size()));

                        target.teleport(spawn);
                        locations.remove(spawn);

                        if(Mines.config.getBoolean("nightVision")) {
                            target.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 3000000, 1, true, false ));
                        }

                        ItemStack pickaxe = new ItemStack(Material.IRON_PICKAXE);
                        ItemMeta pickaxe_meta = pickaxe.getItemMeta();
                        pickaxe_meta.setUnbreakable(true);
                        pickaxe.setItemMeta(pickaxe_meta);
                        target.getInventory().addItem(pickaxe);

                        diamondQuantity.put(target.getUniqueId().toString(), 0);

                        scoreboardManager(mine);
                        //playerMine.put(target.getUniqueId().toString(), mine.getName());
                    }
                    break;
                }
            }
        }
    }

    public static void Lobby(World world) {
        new BukkitRunnable() {
            int lobbyCounter = Mines.config.getInt("countdownTimer") + 1;

            @Override
            public void run() {
                lobbyCounter--;
                for (Player target : world.getPlayers()) {
                    if (lobbyCounter >= 0) {
                        if (lobbyCounter == Mines.config.getInt("countdownTimer")) {

                            target.sendMessage(ChatColor.GOLD + "Game starting in " + lobbyCounter + " seconds.");
                            target.playSound(target.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 10f, 1f);

                        } else if (lobbyCounter <= Mines.config.getInt("countdownTimer") / 2 && lobbyCounter != 0) {

                            target.sendMessage(ChatColor.GOLD + "Game starting in " + lobbyCounter + " seconds.");
                            target.playSound(target.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 10f, 1f);

                        } else if (lobbyCounter == 0) {

                            this.cancel();
                            target.sendMessage(ChatColor.GOLD + "The game has started.");
                            target.playSound(target.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 10f, 0.2f);
                            start(world);

                        }
                    }
                }
            }
        }.runTaskTimer(plugin, 0, 20);
    }
    public static void generateOres(World mine)
    {

        double x = Mines.config.getIntegerList("x").get(0);
        double y = Mines.config.getIntegerList("y").get(0);
        double z = Mines.config.getIntegerList("z").get(0);

        double xb = Mines.config.getIntegerList("x").get(1);
        double yb = Mines.config.getIntegerList("y").get(1);
        double zb = Mines.config.getIntegerList("z").get(1);

        for (int xx = (int) x; xx < xb; xx++) {
            for (int yy = (int) y; yy < yb; yy++) {
                for (int zz = (int) z; zz < zb; zz++) {
                    Block block = mine.getBlockAt(xx, yy, zz);
                    if (block != null) {

                        if (block.getType().equals(Material.STONE)) {
                            int random = new Random().nextInt((100 - 0) + 1) + 0;
                            if(random > Mines.config.getIntegerList("diamond_ore").get(0) && random < Mines.config.getIntegerList("diamond_ore").get(1))
                            {
                                block.setType(Material.DIAMOND_ORE);
                            }
                            else if(random > Mines.config.getIntegerList("gold_ore").get(0) && random < Mines.config.getIntegerList("gold_ore").get(1))
                            {
                                block.setType(Material.GOLD_ORE);
                            }
                            else if(random > Mines.config.getIntegerList("nether_quartz_ore").get(0) && random < Mines.config.getIntegerList("nether_quartz_ore").get(1))
                            {
                                block.setType(Material.NETHER_QUARTZ_ORE);
                            }
                            else if(random > Mines.config.getIntegerList("coal_ore").get(0) && random < Mines.config.getIntegerList("coal_ore").get(1))
                            {
                                block.setType(Material.COAL_ORE);
                            }
                            else if(random > Mines.config.getIntegerList("lapis_ore").get(0) && random < Mines.config.getIntegerList("lapis_ore").get(1))
                            {
                                block.setType(Material.LAPIS_ORE);
                            }
                            else if(random > Mines.config.getIntegerList("stone").get(0) && random < Mines.config.getIntegerList("stone").get(1))
                            {
                                block.setType(Material.STONE);
                            }
                            else if(random > Mines.config.getIntegerList("dirt").get(0) && random < Mines.config.getIntegerList("dirt").get(1))
                            {
                                block.setType(Material.DIRT);
                            }
                            else if(random > Mines.config.getIntegerList("redstone_ore").get(0) && random < Mines.config.getIntegerList("redstone_ore").get(1))
                            {
                                block.setType(Material.REDSTONE_ORE);
                            }
                            else if(random > Mines.config.getIntegerList("emerald_ore").get(0) && random < Mines.config.getIntegerList("emerald_ore").get(1))
                            {
                                block.setType(Material.EMERALD_ORE);
                            }
                            else if(random > Mines.config.getIntegerList("iron_ore").get(0) && random < Mines.config.getIntegerList("iron_ore").get(1))
                            {
                                block.setType(Material.IRON_ORE);
                            }
                        }
                    }
                }
            }
        }
    }

    public static void stopGame(World mines, Player winner)
    {
        Economy eco = Mines.getEconomy();
        Mines.isFinished.remove(mines.getName());
        Mines.isFinished.put(mines.getName(), true);

        for(Player target : mines.getPlayers())
        {
            target.sendMessage(ChatColor.YELLOW + "Match Complete!");
            target.sendMessage(ChatColor.YELLOW + winner.getDisplayName() + ChatColor.GOLD + " Got the diamonds");
            target.getInventory().clear();
            target.setExp(0f);

            target.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
            target.teleport(Bukkit.getWorld("world").getSpawnLocation());


            target.playSound(target.getLocation(), Sound.BLOCK_CONDUIT_ACTIVATE, 10f, 1f);
            target.sendTitle(ChatColor.GOLD + "Match Complete!!", ChatColor.YELLOW + winner.getDisplayName() + ChatColor.GOLD + " Got the diamonds", 20, 20*3, 20);

            if(Mines.config.getBoolean("nightVision"))
            {
                target.removePotionEffect(PotionEffectType.NIGHT_VISION);
            }

            if(!(target.getUniqueId().equals(winner.getUniqueId())))
            {
                int money = Mines.config.getInt("moneyOnLoss");
                eco.depositPlayer(target, money);
                target.sendMessage(ChatColor.GREEN + "You won " + money + "$ for playing the mines.");
            }
        }

        Mines.unloadWorld(mines);
        Mines.copyWorld(Mines.schem, mines.getName());
        Mines.isFinished.remove(mines.getName());
        Mines.isFinished.put(mines.getName(), false);
        Mines.isMineInUse.remove(mines.getName());
        Mines.isMineInUse.put(mines.getName(), false);

        int money = Mines.config.getInt("moneyOnWin");
        eco.depositPlayer(winner, money);
        winner.sendMessage(ChatColor.GREEN + "You won " + money + "$ for winning the mines.");


    }

    public static void scoreboardManager(World world)
    {
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard scoreboard = manager.getNewScoreboard();

        Objective objective = scoreboard.registerNewObjective("mines", "dummy", ChatColor.RED + "Mines");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);



        for(Player target : world.getPlayers())
        {
            Score score = objective.getScore(ChatColor.BLUE + target.getDisplayName() + " - " + diamondQuantity.get(target.getUniqueId().toString()));

            score.setScore(2);

            target.setScoreboard(scoreboard);
        }
    }
}
