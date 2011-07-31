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
