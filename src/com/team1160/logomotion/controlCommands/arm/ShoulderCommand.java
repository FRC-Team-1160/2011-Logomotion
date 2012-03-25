/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.team1160.logomotion.controlCommands.arm;

/**
 *
 * @author CJ
 */
public class ShoulderCommand {

    public int shoulder_right_victor_pwm;
    public int shoulder_left_victor_pwm;

    public boolean reset_gyro;

    public String toString(){
        String output = "/-----\n";
        output += "Shoulder Command\n";
        output += "Shoulder right victor PWM: " + this.shoulder_right_victor_pwm + '\n';
        output += "Shoulder left victor PWM: " + this.shoulder_left_victor_pwm + '\n';
        output += "Reset Gyro: ? " + this.reset_gyro + '\n';
        return output;
    }

}
