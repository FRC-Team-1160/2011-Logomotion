/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.team1160.logomotion.controlCommands.arm;

/**
 *
 * @author CJ
 */
public class TurnTableCommand {

    public int turntable_victor_pwm;

    public String toString(){
        String output = "/-----\n";
        output += "TurnTableCommand\n";
        output += "TurnTable Victor PWM: " + this.turntable_victor_pwm + '\n';
        return output;
    }

}
