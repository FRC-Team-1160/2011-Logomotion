/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.team1160.logomotion.model.states;

/**
 *
 * @author CJ
 */
public class DeploymentState {


    public DeploymentState(){
        this.actuator = new PistonState();
        this.servoExtended = false;
    }

    public boolean servoExtended;

    protected PistonState actuator;
    public PistonState getActuatorState(){return this.actuator;}
    public void setActuatorState(PistonState actuator){ this.actuator = actuator;}


    public String toString(){
        String output = "";
        output += "/-----\n";
        output += "DeploymentSystemState\n";
        output += "Actuator State: ";
        output += this.actuator.toString();
        output += "Servo Extended: " + this.servoExtended + '\n';
        return output;
    }

}
