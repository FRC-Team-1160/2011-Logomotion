/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.team1160.logomotion.model.states.arm;

/**
 *
 * @author CJ
 */
public class TurnTableState {

    public int lastSetPWM;
    public double potValue;

    public String toString(){
        String output = "/-----\n";
        output += "TurnTableState\n";
        output += "Last Set PWM: " + this.lastSetPWM + '\n';
        output += "Pot Value: " + this.potValue + '\n';
        return output;
    }

}
