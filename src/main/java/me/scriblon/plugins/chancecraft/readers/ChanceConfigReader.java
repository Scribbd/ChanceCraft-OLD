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
package me.scriblon.plugins.chancecraft.readers;

import java.util.HashMap;
import java.util.List;
import me.scriblon.plugins.chancecraft.container.ChanceConfig;
import me.scriblon.plugins.chancecraft.container.ItemChance;
import me.scriblon.plugins.chancecraft.container.ProfessionConfig;
import org.bukkit.util.config.Configuration;

/**
 * A class that only for reading the config
 * @author Scriblon (Coen Meulenkamp)
 */
public class ChanceConfigReader {

    public static ChanceConfig readConfig(Configuration config) {
        // General 
        boolean debugPrint;
        boolean commandPrint;
        boolean detailPrint;
        boolean returnOnFail;
        boolean failToss;
        // Item
        HashMap chances;

        //Read Generals config
        String root = "General.";
        debugPrint = config.getBoolean(root + "DebugPrint", false);
        
        if(debugPrint)
            System.out.println("[ChanceCraft/DebugPrint] DebugPrint is enabled");
        
        commandPrint = config.getBoolean(root + "CommandPrint", true);
        detailPrint = config.getBoolean(root + "DetailPlayerPrint", true);
        returnOnFail = config.getBoolean(root + "ReturnOnFail", true);
        failToss = config.getBoolean(root + "FailToss", true);
        
        if(debugPrint)
        {
            System.out.println("[ChanceCraft/DebugPrint] The following settings have been applied:");
            System.out.println("\tCommandPrint: " + commandPrint);
            System.out.println("\tDetailPring: " + detailPrint);
            System.out.println("\tReturnOnFailure: " + returnOnFail);
            System.out.println("\tTossOnFailure: " + failToss);
        }
        
        //Read Items
        chances = ChanceConfigReader.readItems(config, debugPrint);

        //Return container
        return new ChanceConfig(commandPrint, detailPrint, returnOnFail, failToss, chances);
    }

    private static HashMap readItems(Configuration config, boolean debugPrint) {
        HashMap chances = new HashMap();
        HashMap professionsConfig;
        String root = "Items";
        String rootNode = root + "."; // = Items.

        //Get all items
        List<String> items = config.getKeys(root);
        
        if (items == null) {
            System.out.println("[ChanceCraft/LoadConfigError] configurationLoader detects no items have been set.");
        } else {
            
            if(debugPrint)
                System.out.println("[ChanceCraft/DebugPrint] " + items.size() + " itemID's are pressent in config-file");
            
            //Retrieving data
            for (String item : items) {
                //Prepare paths
                String itemName = item.substring(1);
                String itemNode = rootNode + item + "."; // = Items.<item>.
                String professionPath = itemNode + "Professions"; // = (Items.<item>.)Professions
                
                if(debugPrint)
                    System.out.println("[ChanceCraft/DebugPrint] For Current Item: " + itemName + " on " + itemNode + " :");
               
                //Get the basics
                boolean exclusive = config.getBoolean(itemNode + "ProfessionExclusive", false);
                int normal = config.getInt(itemNode + "NormalChance", 100);
                
                if(debugPrint)
                {
                    System.out.println("\t NormalChance: " + normal);
                    System.out.println("\t ProfessionExclusive: " + exclusive);
                }
                        
                //Get Professions
                List<String> professions = config.getKeys(professionPath);
                if(professions != null)
                {
                    professionsConfig = new HashMap();
                    for(String profession : professions)
                    {
                        String professionNode = professionPath + "." + profession + "."; // = (Item.<item>.Professions).<profession>.
                        //Get data
                        int base = config.getInt(professionNode + "ProfessionChance0", normal);
                        int min = config.getInt(professionNode + "ProfessionChancemin", normal);
                        int max = config.getInt(professionNode + "MaxProfChance", normal);
                        int minLevel = config.getInt(professionNode + "MinProflvl", 0);
                        int maxLevel = config.getInt(professionNode + "MaxProflvl", 0);
                        //Make and put in the profession
                        ProfessionConfig professionConfig = new ProfessionConfig(profession, base, min, max, minLevel, maxLevel);
                        professionsConfig.put(profession, professionConfig);
                        
                        if(debugPrint)
                        {
                            System.out.println("\tAdded Profession: " + profession);
                            System.out.println("\t\t professionChance0: " + base);
                            System.out.println("\t\t professionChanceMin: " + min);
                            System.out.println("\t\t maxProfChance: " + max);
                            System.out.println("\t\t minProfLvl: " + minLevel);
                            System.out.println("\t\t maxProfLvl: " + maxLevel);
                        }
                    } // End professions loop
                }
                else
                {
                     professionsConfig = null;
                     System.out.println("\t No professions found");
                }

                
                ItemChance itemChance = new ItemChance(Integer.parseInt(itemName), normal, exclusive, professionsConfig);
                chances.put(itemName, itemChance);    
                
                    if(debugPrint)
                        System.out.println("[ChanceCraft/DebugPrint] Config Read Complete");
            } // End items loop       
        }
        return chances;
    }
}
