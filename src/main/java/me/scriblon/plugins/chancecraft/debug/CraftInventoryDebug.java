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
package me.scriblon.plugins.chancecraft.debug;

import org.bukkit.inventory.ItemStack;
import org.bukkitcontrib.inventory.CraftingInventory;

/**
 *
 * @author Coen
 */
public class CraftInventoryDebug {
    
    public static void printInfo(CraftingInventory inventory)
    {
        ItemStack[] matrix = inventory.getMatrix();
        for(int i = 0; i<matrix.length; i++)
        {
            if(matrix[i] == null)
                System.out.println(i + ": has no item");
            else
                System.out.println(i + ": itemID: " + matrix[i].getTypeId() + " amount: " + matrix[i].getAmount());
        }
    }
}
