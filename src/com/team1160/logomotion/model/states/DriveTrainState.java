/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.team1160.logomotion.model.states;

/**
 *
 * @author CJ
 */
public class DriveTrainState {

    /**
     * Encoder value for right drivetrain.
     */
    public double encoder_right;

    /**
     * Encoder value for left drivetrain.
     */
    public double encoder_left;

    /**
     * The last set right pwm value, for the jags.
     */
    public double last_set_right_speed;

    /**
     * The last set left pwm value, for the jags.
     */
    public double last_set_left_speed;

    public String toString(){
        String output = "";
        output += "/---\n";
        output += "Drive Train State\n";
        output += "Encoder Right: " + this.encoder_right + '\n';
        output += "Encoder Left: " + this.encoder_left + '\n';
        output += "Last Set Speed Left: " + this.last_set_left_speed + '\n';
        output += "Last Set Speed Right: " + this.last_set_right_speed + '\n';
        return output;
    }

}
