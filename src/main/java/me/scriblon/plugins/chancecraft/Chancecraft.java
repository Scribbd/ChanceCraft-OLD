package me.scriblon.plugins.chancecraft;

import java.io.File;
import java.util.logging.Logger;
import me.scriblon.plugins.chancecraft.container.ChanceConfig;
import me.scriblon.plugins.chancecraft.listeners.ChanceCraftListener;
import me.scriblon.plugins.chancecraft.readers.ChanceConfigReader;
import me.scriblon.plugins.chancecraft.util.Downloader;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.config.Configuration;
import org.bukkitcontrib.event.inventory.InventoryListener;

public class Chancecraft extends JavaPlugin {
    
    private boolean jobsAvailable;
    private InventoryListener listener;
    
    public void onDisable() {
        // TODO: Place any custom disable code here.
        System.out.println(this + " is now disabled!");
    }

    public void onEnable() {
        // TODO: Place any custom enable code here, such as registering events
        Logger log = getServer().getLogger();
        final PluginManager pm = getServer().getPluginManager();
        
        //Check jobs
        Downloader.check(log, pm);
        
        //Load configuration, pass on to right class and set the right booleans
        if (pm.getPlugin("Jobs") == null) {
            jobsAvailable = false;
            System.out.println(this + " detected that no Jobs plugin is installed, no Job-features will be available");
        } else {
            jobsAvailable = true;
        }
        
        //Check config-file 
        // TODO: place this inside configReader
        File file = new File("plugins/ChanceCraft/config.yml");
        ChanceConfig chanceConfig = null;
        if(file.exists())
        {
            Configuration config = new Configuration(file);
            config.load();
            chanceConfig = ChanceConfigReader.readConfig(config);        
        }
        else
        {
            System.out.println("[ChanceCraft/LoadError] No config-file found. \tDisabeling ChanceCraft");
            pm.disablePlugin(this);
        }

        //Setup listener.
        listener = new ChanceCraftListener(jobsAvailable, chanceConfig, pm);
        //The high setup is chosen because this should be done as the final thing
        pm.registerEvent(Type.CUSTOM_EVENT, listener, Priority.High, this);
        System.out.println(this + " is now enabled!");
    }
    
    
}