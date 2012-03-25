/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.team1160.logomotion.dashboard;

import com.team1160.logomotion.api.Constants;
import com.team1160.logomotion.model.Model;
import edu.wpi.first.wpilibj.Dashboard;
import edu.wpi.first.wpilibj.DriverStation;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author sakekasi
 */
public class DashboardManager {
    protected Dashboard highDashData;
    protected Model model;
    protected final Object LOCK = new Object();
    protected Timer updateTimer;

    public DashboardManager() {
        this.model = Model.getInstance();
        reset();
        this.updateTimer = new Timer();
        this.updateTimer.schedule(new DSUpdateTask(), Constants.DASHBOARD_UPDATE_TIME);
    }

    public void addCluster(){
        this.reset();
        this.highDashData.addCluster();
        {
            this.highDashData.addString(this.model.getCurrentRobotState().toString());
        }
        this.highDashData.finalizeCluster();
        this.commit();
    }

    public void reset(){
        this.highDashData = DriverStation.getInstance().getDashboardPackerHigh();
    }

    public void commit(){
        this.highDashData.commit();
    }


    class DSUpdateTask extends TimerTask{
        public void run(){
            try{
                synchronized(LOCK){
                    addCluster();
                }
                updateTimer.schedule(new DSUpdateTask(), Constants.DASHBOARD_UPDATE_TIME);
            } catch(Exception e){
                e.printStackTrace();
            }
        }
    }

}
