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
