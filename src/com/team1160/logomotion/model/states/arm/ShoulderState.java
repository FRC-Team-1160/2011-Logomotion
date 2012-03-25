/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.team1160.logomotion.model.states.arm;

/**
 *
 * @author CJ
 */
public class ShoulderState {

    public int lastPWMRight;
    public int lastPWMLeft;
    public double shoulderAngle;

    public String toString(){
        String output = "/-----\n";
        output += "Shoulder State\n";
        output += "Last PWM Right: " + this.lastPWMRight + '\n';
        output += "Last PWM Left: " + this.lastPWMLeft + '\n';
        output += "Shoulder Angle: " + this.shoulderAngle + '\n';
        return output;
    }

}
