/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.team1160.logomotion.controlCommands.arm;

/**
 *
 * @author CJ
 */
public class WristCommand {

    public int wrist_victor_pwm;

    public String toString(){
        String output = "/-----\n";
        output += "Wrist Command\n";
        output += "Wrist Victor PWM: " + this.wrist_victor_pwm + '\n';
        return output;
    }

}
