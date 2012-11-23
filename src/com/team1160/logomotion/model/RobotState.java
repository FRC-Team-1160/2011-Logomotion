/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.team1160.logomotion.model;

import com.team1160.logomotion.model.states.arm.ArmState;
import com.team1160.logomotion.model.states.DriveTrainState;
import com.team1160.logomotion.model.states.DeploymentState;
import com.team1160.logomotion.model.states.PnuematicsState;

/**
 * Compiles all the subsytem states into one robot state
 * @author CJ
 */
public class RobotState {

    public RobotState(){
        this.driveTrainState = new DriveTrainState();
        this.armState = new ArmState();
        this.pnuematicsState = new PnuematicsState();
        this.deploymentState = new DeploymentState();
    }

    /**
     * State that holds the deployment System.
     */
     protected DeploymentState deploymentState;
     public DeploymentState getDeploymentState(){ return this.deploymentState;}
     public void setDeploymentState(DeploymentState deploymentState){ this.deploymentState = deploymentState;}

    /**
     * State that holds the drivetrain.
     */
    protected DriveTrainState driveTrainState;
    public DriveTrainState getDriveTrainState(){return this.driveTrainState;}
    public void setDriveTrainState(DriveTrainState driveTrainState){this.driveTrainState = driveTrainState;}

    /**
     * State that holds the arm.
     */
    protected ArmState armState;
    public ArmState getArmState(){return this.armState;}
    public void setArmState(ArmState armState){this.armState = armState;}

    /**
     * State that holds the pneumatics.
     */
    protected PnuematicsState pnuematicsState;
    public PnuematicsState getPnuematicsState(){return this.pnuematicsState;}
    public void setPnuematicsState(PnuematicsState pnuematicsState){this.pnuematicsState = pnuematicsState;}

    public String toString(){
        String output = "";
        output += "/---------------\n";
        output += "Robot State\n";
        output += this.driveTrainState.toString();
        output += this.pnuematicsState.toString();
        output += this.armState.toString();
        output += this.deploymentState.toString();
        return output;
    }

}
