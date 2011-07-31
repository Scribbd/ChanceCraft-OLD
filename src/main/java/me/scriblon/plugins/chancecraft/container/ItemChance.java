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
