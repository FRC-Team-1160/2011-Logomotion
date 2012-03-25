/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.team1160.logomotion.util;

import edu.wpi.first.wpilibj.Timer;

/**
 *
 * @author nttoole
 */
public class CommandTimer extends Timer {

    boolean isRunning;

    public CommandTimer()
    {
        super();
        stop();
        isRunning = false;
    }

    public synchronized void stop()
    {
        if (isRunning)
        {
            super.stop();
            this.isRunning = false;
        }
    }

    public synchronized void start()
    {
        if (!isRunning)
        {
            super.start();
            this.isRunning = true;
        }
    }

    public synchronized double get()
    {
        if (isRunning)
        {
            return super.get();
        }
        else
            return 0.0;
    }

    public boolean isRunning()
    {
        return this.isRunning;
    }

    public synchronized void reset()
    {
        this.stop();
        super.reset();
    }

    public String toString()
    {
        return "CommandTime = "+get();
    }
}
