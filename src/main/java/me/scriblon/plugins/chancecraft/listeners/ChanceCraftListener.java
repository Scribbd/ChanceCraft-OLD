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
package me.scriblon.plugins.chancecraft.listeners;

import com.zford.jobs.config.container.Job;
import com.zford.jobs.config.container.PlayerJobInfo;
import com.zford.jobs.Jobs;
import com.zford.jobs.config.container.JobProgression;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import me.scriblon.plugins.chancecraft.container.ItemChance;
import me.scriblon.plugins.chancecraft.container.ChanceConfig;
import me.scriblon.plugins.chancecraft.container.ListenerLink;
import me.scriblon.plugins.chancecraft.container.ProfessionConfig;
import me.scriblon.plugins.chancecraft.debug.CraftInventoryDebug;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkitcontrib.event.inventory.InventoryClickEvent;
import org.bukkitcontrib.event.inventory.InventoryCloseEvent;
import org.bukkitcontrib.event.inventory.InventoryCraftEvent;
import org.bukkitcontrib.event.inventory.InventoryListener;
import org.bukkitcontrib.inventory.CraftingInventory;

/**
 * Handles everthing that comes with an craftEvent
 * @author Coen
 */
public class ChanceCraftListener extends InventoryListener {

    private boolean withJobs;
    private ChanceConfig config;
    private PluginManager pm;
    private ListenerLink link;

    public ChanceCraftListener(boolean theJobs, ChanceConfig configuration, PluginManager thePM, ListenerLink linker) {
        withJobs = theJobs;
        config = configuration;
        pm = thePM;
        link = linker;
    }

    @Override
    public void onInventoryCraft(InventoryCraftEvent event) {
        super.onInventoryCraft(event);
        if(event.isCancelled())
            return;
        
        System.out.println("[ChanceCraft] Just got a craft in! The victim: " + event.getPlayer().getName());
        HashMap chances = config.getChances();
        CraftingInventory craft = event.getInventory();
        if (craft.getResult() == null) {
            System.out.println("[ChanceCraft] There was no item crafted you moron!");
            return;
        }

        String itemID = "" + craft.getResult().getTypeId();
        System.out.println("[ChanceCraft] Target acquired: " + itemID);
        //Check if rest has to be run
        if (!chances.containsKey(itemID)) {
            System.out.println("[ChanceCraft] False alarm, ID not found in list");
            return;
        }
        //Get all the data to process event
        ItemChance itemChance = (ItemChance) chances.get(itemID);

        ItemStack result = craft.getResult();
        ItemStack[] recipe = craft.getMatrix();
        CraftInventoryDebug.printInfo(craft);

        Player player = event.getPlayer();

        //Make Roll
        int roll = makeRoll();
        //Determine minimum
        int min = determinMin(itemChance, player);

        if (success(roll, min)) {
            if (config.isCommandPrint()) {
                System.out.println("[ChanceCraft:EVENT] " + player.getName() + " SUCCEEDED in crafting " + result.getTypeId()
                        + " with roll: " + roll + ", and chance: " + min + " (minReq: " + (100 - min) + ")");
            }
            if (config.isDetailPrint()) {
                // TODO: get chatbox thing right
                player.sendMessage("You SUCCEEDED in crafting the item. " + " with roll: " + roll + ", and chance: " + min + " (minReq: " + (100 - min) + ")");
            } else {
                player.sendMessage("You SUCCEEDED in crafting the item.");
            }
            return;
        }

        // if it fails, the punishment begins.
        if (config.isCommandPrint()) {
            System.out.println("[ChanceCraft:EVENT] " + player.getName() + " FAILED in crafting " + result.getTypeId()
                    + " with roll: " + roll + ", and chance: " + min);
        }
        if (config.isDetailPrint()) {
            player.sendMessage("You FAILED in crafting the item. " + " with roll: " + roll + ", and chance: " + min + " (minReq: " + (100 - min) + ")");
        }else{
            player.sendMessage("You FAILED in crafting the item.");
        }

        if (config.isReturnOnFail()) {
            event.setCancelled(true);
            return;
        }
        //When in 'hardcore' mode items in stack are affected.
        craft.setMatrix(mutateRecipe(recipe));
        player.updateInventory();
        event.setCursor(null);
        player.updateInventory();
        CraftInventoryDebug.printInfo(craft);
        link.setRecentFailure(true);
    }
    
    /**
     * Workaround method for having
     * @param event 
     */
    @Override
    public void onInventoryClick(InventoryClickEvent event) {
        super.onInventoryClick(event);
        if(!event.isCancelled() || !link.isRecentFailure())
            return;
        if(event.getCursor() == null)
            return;
        System.out.println("Cursor wasn't empty after all");
        event.setCursor(null);
        link.setRecentFailure(false);
        event.getPlayer().updateInventory();
               
    }

    @Override
    public void onInventoryClose(InventoryCloseEvent event) {
        super.onInventoryClose(event);
        if(!event.isCancelled() || !link.isRecentFailure())
            return;
        link.setRecentFailure(false);
        System.out.println("Recent close event");
        event.getPlayer().updateInventory();
    }
    
    

    private boolean success(int roll, int min) {
        return 100 - min <= roll;
    }

    private int makeRoll() {
        Random rand = new Random();
        return rand.nextInt(101);
    }

    private int determinMin(ItemChance chance, Player player) {

        // When jobs is enabled this will change the min calculation is made.
        if (!withJobs || chance.getProfessions() == null) {
            return chance.getNormalChance();
        }

        // Retrieving Job data
        Jobs jobsPlug = (Jobs) pm.getPlugin("Jobs");
        PlayerJobInfo jobInfo = jobsPlug.getPlayerJobInfo(player);
        List<Job> playerJobs = jobInfo.getJobs();

        // Check Job Match
        HashMap professions = chance.getProfessions();
        int maxChance = -1;
        ProfessionConfig profession;

        for (Job job : playerJobs) {
            if (professions.containsKey(job.getName())) {
                JobProgression currentProgress = jobInfo.getJobsProgression(job);
                profession = (ProfessionConfig) professions.get(job.getName());

                int currentChance = calculateChance(profession, currentProgress.getLevel());

                if (maxChance < currentChance) {
                    maxChance = currentChance;
                }
            }
        }

        if (maxChance == -1) {
            System.out.println("Player doesn't own profession");
            return chance.getNormalChance();
        }

        return maxChance;
    }

    private int calculateChance(ProfessionConfig config, int lvl) {
        //Get basic values
        int minChance = config.getMinChance();
        int zeroChance = config.getBaseChance();
        int maxChance = config.getMaxChance();
        int minLvl = config.getMinLvl();
        int maxLvl = config.getMaxLvl();
        // if the current lvl is smaller than the minimum lvl in config, the zeroChance is given back
        if (minLvl > lvl) {
            return zeroChance;
        }
        // if current lvl surpasses the maximum lvl in config, the maxChance is given back
        if (maxLvl < lvl) {
            return maxChance;
        }
        // if it all passes calculate chances
        int difChance = maxChance - minChance;
        int difLvl = maxLvl - minLvl;
        int currentDif = lvl - minLvl;
        if (difLvl < 0) {
            System.out.println("[ChanceCraft] ERROR: difference in maximum LVL and minimum LVL is < -1. Check Configuration");
            return -1;
        }

        return currentDif * (difChance / difLvl) + minChance;
    }

    private ItemStack[] mutateRecipe(ItemStack[] stack) {

        int returnChance = 50;
        ItemStack[] result = new ItemStack[stack.length - 1];
        System.out.println("[ChanceCraft] Length of stack: " + stack.length);
        for (int i = 0; i < result.length; i++) {
            if (stack[i] != null) {
                int amount = stack[i].getAmount();
                System.out.println("[ChanceCraft] Istack size: " + amount + " as item " + stack[i].getTypeId());
                int mutant = 0;
                for (int j = 0; j < amount; j++) {
                    if (success(makeRoll(), returnChance)) {
                        mutant++;
                    }
                }
                System.out.println("[ChanceCraft] New Istack size: " + mutant);
                if (mutant == 0) {
                    result[i] = null;
                } else if (mutant != stack[i].getAmount()) {
                    result[i] = stack[i];
                    result[i].setAmount(mutant);
                } else {
                    result[i] = stack[i];
                }
            } else {
                result[i] = null;
            }
        }

        System.out.println("[ChanceCraft] New stack: " + stack.length);
        return result;
    }
}
