/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.team1160.logomotion.camera;

import edu.wpi.first.wpilibj.camera.AxisCamera;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author CJ
 */
public class Camera {

    protected AxisCamera camera;

    protected Timer cameraPollTimer;

    public Camera(){
        this.camera = AxisCamera.getInstance();
        this.cameraPollTimer = new Timer();
    }

    public void startCamera(){
        this.cameraPollTimer.schedule(new CameraPollTimer(), 500);
    }

    public class CameraPollTimer extends TimerTask{
        public void run(){
            try{
                if(camera.freshImage()){
                    System.out.println("Poll.");
                    camera.getImage();
                }
                cameraPollTimer.schedule(new CameraPollTimer(), 500);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

}
