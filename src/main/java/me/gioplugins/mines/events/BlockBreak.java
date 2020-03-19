package me.gioplugins.mines.events;

import me.gioplugins.mines.Mines;
import me.gioplugins.mines.misc.GameManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

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
                        GameManager.diamondQuantity.remove(player.getUniqueId().toString());
                        GameManager.diamondQuantity.put(player.getUniqueId().toString(), playerDiamonds + 1);
                        for(Player target : player.getWorld().getPlayers())
                        {
                            target.sendMessage(ChatColor.YELLOW + player.getDisplayName() + ChatColor.GOLD + " Got 1 Diamond " + ChatColor.GRAY + "(0" + (playerDiamonds + 1) + "/06)");
                        }

                    }
                    else if(playerDiamonds + 1 == 6)
                    {
                        GameManager.stopGame(player.getWorld(), player);
                    }
                }
            }
        }
    }
}
