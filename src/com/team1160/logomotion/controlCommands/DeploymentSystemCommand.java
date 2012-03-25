/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.team1160.logomotion.controlCommands;

/**
 *
 * @author CJ
 */
public class DeploymentSystemCommand {

    public boolean actuator_extend;
    public boolean panel_deploy;
    

    public DeploymentSystemCommand(){
        this.actuator_extend = false;
        this.panel_deploy = false;
    }

    public String toString(){
        String output = "/-----\n";
        output = "DeploymentSystemCommand\n";
        output += "Panel Deploy: " + this.panel_deploy + '\n';
        output += "Release: " + this.actuator_extend + '\n';
        return output;
    }

}
