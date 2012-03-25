/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.team1160.logomotion.input;

import com.team1160.logomotion.input.controllerStates.ShoulderJoystickState;
import com.team1160.logomotion.input.controllerStates.WristJoystickState;
import com.team1160.logomotion.input.controllerStates.MinibotControlState;
import com.team1160.logomotion.input.controllerStates.DriveJoysticksState;
import com.team1160.logomotion.input.controllerStates.ModelArmControlState;
import com.team1160.logomotion.input.controllerStates.TurnTableJoystickState;

/**
 *
 * @author CJ
 */
public class InputState {

    protected DriveJoysticksState driveJoystickState;
    public DriveJoysticksState getDriveJoystickState(){ return this.driveJoystickState; }
    public void setDriveJoystickState(DriveJoysticksState driveJoystickState)
        {this.driveJoystickState = driveJoystickState;}

    protected ShoulderJoystickState dof1JoystickState;
    public ShoulderJoystickState getDOF1JoystickState(){ return this.dof1JoystickState; }
    public void setDOF1JoystickState(ShoulderJoystickState dof1JoystickState)
        {this.dof1JoystickState = dof1JoystickState;}

    protected WristJoystickState dof2JoystickState;
    public WristJoystickState getDOF2JoystickState(){ return this.dof2JoystickState; }
    public void setDOF2JoystickState(WristJoystickState dof2JoystickState)
        {this.dof2JoystickState = dof2JoystickState;}

    protected TurnTableJoystickState turnTableJoystickState;
    public TurnTableJoystickState getTurnTableJoystickState(){return this.turnTableJoystickState;}
    public void setTurnTableJoystickState(TurnTableJoystickState turnTableJoystickState)
        {this.turnTableJoystickState = turnTableJoystickState;}

//    protected DeploymentSystemJoystickState deploymentSystemJoystickState;
//    public DeploymentSystemJoystickState getDeploymentSystemJoystickState() {return this.deploymentSystemJoystickState;}
//    public void setDeploymentSystemJoystickState(DeploymentSystemJoystickState deploymentSystemJoystickState)
//        {this.deploymentSystemJoystickState = deploymentSystemJoystickState;}

    protected ModelArmControlState modelArmControlState;
    public ModelArmControlState getModelArmState() { return this.modelArmControlState; }
    public void setModelArmState(ModelArmControlState modelArmState){
        this.modelArmControlState = modelArmState;
    }

    protected MinibotControlState minibotControlState;
    public MinibotControlState getMinibotControlState() {return this.minibotControlState;}
    public void setMinibotControlState(MinibotControlState minibotControlState) {
        this.minibotControlState = minibotControlState;
    }
    public String toString(){
        String output = "";
        output += "/---------------\n";
        output += "Input State \n";
        output += this.driveJoystickState.toString();
        output += this.dof1JoystickState.toString();
        output += this.dof2JoystickState.toString();
        output += this.turnTableJoystickState.toString();
        output += this.modelArmControlState.toString();
        output += this.minibotControlState.toString();
        return output;
    }

    
}
