/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.scriblon.plugins.chancecraft.container;

import java.util.HashMap;

/**
 * Stores variables for calculating the chance.
 * @author Coen
 */
public class ItemChance {

    private int itemID;
    private int normalChance;
    private boolean exclusive;
    private HashMap professions;

    public ItemChance(int theID, int normalC, boolean professionExclusive, HashMap professionConfig) {
        itemID = theID;
        normalChance = normalC;
        professions = professionConfig;
        exclusive = professionExclusive;
    }

    public boolean isExclusive() {
        return exclusive;
    }

    public int getItemID() {
        return itemID;
    }

    public int getNormalChance() {
        return normalChance;
    }

    public HashMap getProfessions() {
        return professions;
    }

}
