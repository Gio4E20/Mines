package me.gioplugins.mines.events;

import me.gioplugins.mines.Mines;
import me.gioplugins.mines.misc.GameManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.scoreboard.Score;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class BlockBreak implements Listener {


    @EventHandler
    public void onBreak(BlockBreakEvent e)
    {
        Player player = e.getPlayer();
        Block block = e.getBlock();

        if(player.getWorld().getName().startsWith("mines-")) {
            if (Mines.isFinished.get(player.getWorld().getName())) {
                e.setCancelled(true);
            } else {
                if (block.getType().equals(Material.DIAMOND_ORE)) {

                    int playerDiamonds = GameManager.diamondQuantity.get(player.getUniqueId().toString());

                    if (playerDiamonds + 1 < 6) {
                        GameManager.scoreboardManager(player.getWorld());
                        GameManager.diamondQuantity.remove(player.getUniqueId().toString());
                        GameManager.diamondQuantity.put(player.getUniqueId().toString(), playerDiamonds + 1);
                        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 10f, 1f);
                        for(Player target : player.getWorld().getPlayers())
                        {
                            target.sendMessage(ChatColor.YELLOW + player.getDisplayName() + ChatColor.GOLD + " Got one Diamond " + ChatColor.GRAY + "(0" + (playerDiamonds + 1) + "/0" + Mines.config.getInt("diamondsToWin") + ")");
                        }

                    }
                    else if(playerDiamonds + 1 == Mines.config.getInt("diamondsToWin"))
                    {
                        GameManager.scoreboardManager(player.getWorld());
                        GameManager.stopGame(player.getWorld(), player);
                    }

                }
            }
        }
    }
}
