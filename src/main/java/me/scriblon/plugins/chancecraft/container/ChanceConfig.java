/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.scriblon.plugins.chancecraft.container;

import java.util.HashMap;

/**
 *
 * @author Coen
 */
public class ChanceConfig {

    private int normalChance;
    private boolean professionExculsive;
    private boolean commandPrint;
    private boolean detailPrint;
    private boolean returnOnFail;
    private boolean failToss;
    private HashMap chances;

    public ChanceConfig(boolean command, boolean detail, boolean failReturn, boolean tossOnFail, HashMap itemChances) {
        commandPrint = command;
        detailPrint = detail;
        returnOnFail = failReturn;
        failToss = tossOnFail;

        chances = itemChances;
    }

    public HashMap getChances() {
        return chances;
    }

    public boolean isCommandPrint() {
        return commandPrint;
    }

    public boolean isDetailPrint() {
        return detailPrint;
    }

    public boolean isFailToss() {
        return failToss;
    }

    public int getNormalChance() {
        return normalChance;
    }

    public boolean isProfessionExculsive() {
        return professionExculsive;
    }

    public boolean isReturnOnFail() {
        return returnOnFail;
    }
    
    
}
