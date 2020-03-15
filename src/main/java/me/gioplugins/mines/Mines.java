package me.gioplugins.mines;
import me.gioplugins.mines.events.PlayerSwitchedWorlds;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public final class Mines extends JavaPlugin implements Listener {

    public static HashMap<String,Boolean> isMineInUse = new HashMap<String,Boolean>();
    public static HashMap<String,Integer> isLobbyFull = new HashMap<String,Integer>();
    public static List<World> mines = new ArrayList<>();
    public static List<World> lobbies = new ArrayList<>();

    @Override
    public void onEnable() {
        //Events
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerSwitchedWorlds(), this);
        //
        //Worlds
        List<World> worlds = Bukkit.getServer().getWorlds();
        for(World world : worlds ) {
            if(world.getName().startsWith("mines-")){
                isMineInUse.put(world.getName(), false);
            }
            else if(world.getName().startsWith("lobby-")){
                isLobbyFull.put(world.getName(), 0);
            }
        }
        createWorlds();
        //
    }
    public void createWorlds(){
        for(int i = 0 ; i < 1; i++){ //Change to config
            World mine;
            World lobby;
            WorldCreator m = new WorldCreator("mines-" + i);
            WorldCreator l = new WorldCreator("lobbyMines-" + i);
            m.generateStructures(false);
            l.generateStructures(false);
            mine = m.createWorld();
            lobby = l.createWorld();
            lobbies.add(lobby);
            mines.add(mine);
        }
    }
}
