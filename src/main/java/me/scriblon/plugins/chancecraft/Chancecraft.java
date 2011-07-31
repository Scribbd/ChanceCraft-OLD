/*
 *Copyright (C) 2011 Coen Meulenkamp (Scriblon, ~theJaf) <coenmeulenkamp at gmail.com>
 *This program is free software: you can redistribute it and/or modify
 *it under the terms of the GNU General Public License as published by
 *the Free Software Foundation, either version 3 of the License, or
 *(at your option) any later version.
 *
 *This program is distributed in the hope that it will be useful,
 *but WITHOUT ANY WARRANTY; without even the implied warranty of
 *MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *GNU General Public License for more details.
 *
 *You should have received a copy of the GNU General Public License
 *along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package me.scriblon.plugins.chancecraft;

import java.io.File;
import java.util.logging.Logger;
import me.scriblon.plugins.chancecraft.container.ChanceConfig;
import me.scriblon.plugins.chancecraft.container.ListenerLink;
import me.scriblon.plugins.chancecraft.listeners.ChanceClickListener;
import me.scriblon.plugins.chancecraft.listeners.ChanceCraftListener;
import me.scriblon.plugins.chancecraft.readers.ChanceConfigReader;
import me.scriblon.plugins.chancecraft.util.Downloader;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.config.Configuration;

public class Chancecraft extends JavaPlugin {
    
    private boolean jobsAvailable;
    private org.bukkitcontrib.event.inventory.InventoryListener cListener;
    private org.bukkit.event.inventory.InventoryListener dListener;
    
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
        ListenerLink link = new ListenerLink();
        cListener = new ChanceCraftListener(jobsAvailable, chanceConfig, pm, link);
        dListener = new ChanceClickListener(chanceConfig, link);
        //The high setup is chosen because this should be done as the final thing
        pm.registerEvent(Type.CUSTOM_EVENT, cListener, Priority.High, this);
        pm.registerEvent(Type.INVENTORY_CLICK, dListener, Priority.Low, this);
        System.out.println(this + " is now enabled!");
    }
    
    
}