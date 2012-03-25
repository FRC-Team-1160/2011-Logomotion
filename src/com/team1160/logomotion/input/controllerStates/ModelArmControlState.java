/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.team1160.logomotion.input.controllerStates;

/**
 *
 * @author CJ
 */
public class ModelArmControlState {

    public double shoulderPotValue;
    public double wristPotValue;
    public boolean enabled;
    public TurnTableJoystickState state;

    public ModelArmControlState(){
        this.state = TurnTableJoystickState.kStop;
    }

    public String toString(){
        String output = "/-----\n";
        output += "Model Arm State\n";
        output += "Enabled: " + this.enabled + '\n';
        output += "Shoulder Pot Value: " + shoulderPotValue + '\n';
        output += "Wrist Pot Value: " + wristPotValue + '\n';
        return output;
    }

}
