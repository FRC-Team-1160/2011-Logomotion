/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.team1160.logomotion.teleopManager;

import com.team1160.logomotion.controlCommands.arm.ArmCommand;
import com.team1160.logomotion.controlCommands.DriveTrainCommand;
import com.team1160.logomotion.controlCommands.DeploymentSystemCommand;

/**
 *
 * @author CJ
 */
public class RobotCommand {


    protected DriveTrainCommand driveTrainCommand;
    public DriveTrainCommand getDriveTrainCommand(){ return this.driveTrainCommand; }
    public void setDriveTrainCommand(DriveTrainCommand driveTrainCommand){ this.driveTrainCommand = driveTrainCommand; }

    protected ArmCommand armCommand;
    public ArmCommand getArmCommand() { return this.armCommand; }
    public void setArmCommand(ArmCommand armCommand){ this.armCommand = armCommand; }

    protected DeploymentSystemCommand deploymentSystemCommand;
    public DeploymentSystemCommand getDeploymentSystemCommand(){ return this.deploymentSystemCommand; }
    public void setDeploymentSystemCommand(DeploymentSystemCommand deploymentSystemCommand){ this.deploymentSystemCommand = deploymentSystemCommand; }

    public String toString(){
        StringBuffer buffer = new StringBuffer();
        buffer.append("/---------------\n");
        buffer.append("RobotCommand\n");
        buffer.append(this.driveTrainCommand.toString());
        buffer.append(this.armCommand.toString());
        buffer.append(this.deploymentSystemCommand.toString());
        return buffer.toString();
    }
}
