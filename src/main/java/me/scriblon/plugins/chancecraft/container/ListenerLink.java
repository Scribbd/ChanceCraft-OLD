/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.scriblon.plugins.chancecraft.container;

/**
 *
 * @author Coen
 */
public class ListenerLink {
    
    private boolean recentFailure;
    
    public ListenerLink()
    {
        recentFailure = false;
    }

    public boolean isRecentFailure() {
        return recentFailure;
    }

    public void setRecentFailure(boolean recentFailure) {
        this.recentFailure = recentFailure;
    }
    
}
