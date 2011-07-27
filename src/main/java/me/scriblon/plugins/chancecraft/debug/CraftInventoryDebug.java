/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
