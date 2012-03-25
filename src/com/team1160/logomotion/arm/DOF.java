/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.team1160.logomotion.arm;

import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Victor;

/**
 *
 * @author CJ
 */
public class DOF {


    protected Victor rightVictor;
    protected Victor leftVictor;
    protected Jaguar motor;

    protected boolean rightEnabled;
    protected boolean leftEnabled;
    protected boolean isJag;

    public DOF(int victor_channel_right, int victor_channel_left){

        //Victor Creation
        this.rightVictor = new Victor(victor_channel_right);
        this.leftVictor = new Victor(victor_channel_left);

        this.rightEnabled = true;
        this.leftEnabled = true;
     }

    public DOF(int channel_one, boolean isJag){

        if(isJag){
            this.motor = new Jaguar(channel_one);
        } else {
            this.rightVictor = new Victor(channel_one);
        }

        this.rightEnabled = true;
        this.leftEnabled = false;
        this.isJag = isJag;
    }

    public boolean isJag(){
        return this.isJag;
    }

    public void rotateRight(int pwm){
        if(this.rightEnabled){
            if(this.isJag){
                this.motor.setRaw(pwm);
            } else {
                this.rightVictor.setRaw(pwm);
            }
        }
    }

    public void rotateLeft(int pwm){
        if(this.leftEnabled){
            this.leftVictor.setRaw(pwm);
        }
    }

}
