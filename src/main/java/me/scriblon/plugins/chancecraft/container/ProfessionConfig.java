/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.scriblon.plugins.chancecraft.container;

/**
 * Contains all configurations for one profession.
 * @author Scriblon (Coen Meulenkamp)
 */
public class ProfessionConfig {
    private String name;
    private int baseChance; //Base value, this is the chance on lvl 0
    private int minChance; //Value when lvl is minLvl
    private int maxChance; //Value when maxLvl is reached
    private int minLvl; //Level from which chance will increase
    private int maxLvl; //Level from which chance equals maxChance
    
    /**
     * 
     * @param theName
     * @param base
     * @param min
     * @param max
     * @param minLevel
     * @param maxLevel 
     */
    public ProfessionConfig(String theName, int base, int min, int max, int minLevel, int maxLevel)
    {
        name = theName;
        baseChance = base;
        minChance = min;
        maxChance = max;
        minLvl = minLevel;
        maxLvl = maxLevel;
    }

    public int getBaseChance() {
        return baseChance;
    }

    public int getMaxChance() {
        return maxChance;
    }

    public int getMaxLvl() {
        return maxLvl;
    }

    public int getMinChance() {
        return minChance;
    }

    public int getMinLvl() {
        return minLvl;
    }

    public String getName() {
        return name;
    }
}
