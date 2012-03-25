/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.team1160.logomotion.pnuematics;

import edu.wpi.first.wpilibj.Solenoid;

/**
 *
 * @author CJ
 */
public class Piston {

    protected Solenoid frontSolenoid;
    protected Solenoid backSolenoid;

    public Piston(int c_front, int c_back, boolean startExtended){
        this.frontSolenoid = new Solenoid(8, c_front);
        this.backSolenoid = new Solenoid(8, c_back);
        if(startExtended){
            this.extend();
        }else{
            this.retract();
        }
    }

    public void retract(){
        if(this.frontSolenoid == null ||
                this.backSolenoid == null){
            throw new IllegalStateException("In Piston:retract(), one solenoid " +
                    "was null.");
        }
        this.frontSolenoid.set(true);
        this.backSolenoid.set(false);
    }

    public void extend(){
        if(this.frontSolenoid == null ||
                this.backSolenoid == null){
            throw new IllegalStateException("In Piston:extend(), one solenoid " +
                    "was null.");
        }
        this.frontSolenoid.set(false);
        this.backSolenoid.set(true);
    }

    public boolean isRetracted(){
        if(this.frontSolenoid == null ||
                this.backSolenoid == null){
            throw new IllegalStateException("In Piston:isRetracted, one solenoid " +
                    "was null.");
        }
        return this.frontSolenoid.get() && !this.backSolenoid.get();
    }

    public boolean isExtended(){
        if(this.frontSolenoid == null ||
                this.backSolenoid == null){
            throw new IllegalStateException("In Piston:isExtended, one solenoid " +
                    "was null.");
        }
        return !this.frontSolenoid.get() && this.backSolenoid.get();
    }
}
