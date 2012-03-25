/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.team1160.logomotion.input.controllerStates;

/**
 *
 * @author sakekasi, Cj
 */
public class MinibotControlState {

    public boolean runActuator;
    public boolean triggerPanel;

    public MinibotControlState(){
        this.runActuator = false;
        this.triggerPanel = false;
    }

    public String toString(){
        StringBuffer buffer = new StringBuffer("/-----\n");
        buffer.append("MinibotControlState\n");
        buffer.append("Trigger Panel: ").append(this.triggerPanel).append('\n');
        buffer.append("Run Actuator: ").append(this.runActuator).append('\n');
        return buffer.toString();
    }

}
