/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.scriblon.plugins.chancecraft.listeners;

import me.scriblon.plugins.chancecraft.container.ChanceConfig;
import me.scriblon.plugins.chancecraft.container.ListenerLink;
import org.bukkit.event.inventory.InventoryListener;

/**
 *
 * @author Coen
 */
public class ChanceClickListener extends InventoryListener {
    
    private ChanceConfig config;
    private ListenerLink link;
    
    public ChanceClickListener(ChanceConfig chanceConfig, ListenerLink linker)
    {
        config = chanceConfig;
        link = linker;
    }
}
