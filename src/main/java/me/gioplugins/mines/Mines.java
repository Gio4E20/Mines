package me.gioplugins.mines;
import me.gioplugins.mines.commands.MinesCommand;
import me.gioplugins.mines.events.BlockBreak;
import me.gioplugins.mines.events.PlayerSwitchedWorlds;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


public final class Mines extends JavaPlugin implements Listener {

    public static FileConfiguration config;
    private static Economy econ = null;
    public static HashMap<String,Boolean> isFinished = new HashMap<String,Boolean>();
    public static HashMap<String,Boolean> isMineInUse = new HashMap<String,Boolean>();
    public static HashMap<String,Integer> isLobbyFull = new HashMap<String,Integer>();
    public static List<World> lobbiesList = new ArrayList<>();
    public static World schem;

    @Override
    public void onEnable()
    {
        //Events
        Bukkit.getServer().getPluginManager().registerEvents(new BlockBreak(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerSwitchedWorlds(), this);
        //
        //Worlds
        schem = WorldCreator.name("minesSchem").createWorld();
        for(int i = 0 ; i < getConfig().getInt("numberOfMines"); i++)
        {
            copyWorld(schem, "mines-" + i);
        }
        for(int i = 0 ; i < getConfig().getInt("numberOfLobbies"); i++)
        {
            copyWorld(schem, "lobbyMines-" + i);
        }

        //
        //Commands
        getCommand("mines").setExecutor(new MinesCommand());
        getCommand("mines").setTabCompleter(this::onTabComplete);
        //
        //Setup economy
        if (!setupEconomy() ) {
            System.out.println(ChatColor.RED + "No economy plugin found. Disabling this plugin 'Mines'");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        //Setup config
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        config = this.getConfig();
        //
    }

    private static void copyFileStructure(File source, File target)
    {
        try {
            ArrayList<String> ignore = new ArrayList<>(Arrays.asList("uid.dat", "session.lock"));
            if(!ignore.contains(source.getName())) {
                if(source.isDirectory()) {
                    if(!target.exists())
                        if (!target.mkdirs())
                            throw new IOException("Couldn't create world directory!");
                    String files[] = source.list();
                    for (String file : files) {
                        File srcFile = new File(source, file);
                        File destFile = new File(target, file);
                        copyFileStructure(srcFile, destFile);
                    }
                } else {
                    InputStream in = new FileInputStream(source);
                    OutputStream out = new FileOutputStream(target);
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = in.read(buffer)) > 0)
                        out.write(buffer, 0, length);
                    in.close();
                    out.close();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void copyWorld(World originalWorld, String newWorldName)
    {
        copyFileStructure(originalWorld.getWorldFolder(), new File(Bukkit.getWorldContainer(), newWorldName));
        World world = new WorldCreator(newWorldName).createWorld();
        if(newWorldName.startsWith("lobbyMines-"))
        {
            lobbiesList.add(world);
            isLobbyFull.put(world.getName(), 0);
        }
        else if(newWorldName.startsWith("mines-"))
        {
            isMineInUse.put(world.getName(), false);
            isFinished.put(world.getName(), false);
        }
    }

    public static boolean unloadWorld(World world)
    {
        return world!=null && Bukkit.getServer().unloadWorld(world, false);
    }


    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }



    public static Economy getEconomy() {
        return econ;
    }


    public ArrayList<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args)
    {
        ArrayList<String> returnString = new ArrayList<>();
        if (cmd.getName().toLowerCase().equals("mines"))
        {
            if(args.length == 1)
            {

                if(!(args[0].equals("")))
                {
                    if(args[0].startsWith("j"))
                    {
                        returnString.add("join");
                        return returnString;
                    }
                    else if(args[0].startsWith("e"))
                    {
                        returnString.add("edit");
                        return returnString;
                    }
                    else if(args[0].startsWith("l"))
                    {
                        returnString.add("leave");
                        return returnString;
                    }
                }
                else
                {
                    returnString.add("join");
                    returnString.add("edit");
                    returnString.add("leave");
                    return returnString;
                }
            }
            if(args.length == 2)
            {
                if(!(args[1].equals("")))
                {
                    if(args[1].startsWith("l"))
                    {
                        returnString.add("lobby");
                        return returnString;
                    }
                    else if(args[1].startsWith("m"))
                    {
                        returnString.add("m");
                        return returnString;
                    }
                }
            }
            else{
                returnString.add("lobby");
                returnString.add("mines");
                return returnString;
            }

        }
        return null;
    }
}
