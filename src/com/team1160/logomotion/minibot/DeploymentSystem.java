/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.team1160.logomotion.minibot;

import com.team1160.logomotion.model.states.DeploymentState;
import com.team1160.logomotion.api.Constants;
import com.team1160.logomotion.model.Model;
import com.team1160.logomotion.model.ModelNotifier;
import com.team1160.logomotion.pnuematics.Piston;
import edu.wpi.first.wpilibj.Servo;

/**
 *
 * @author CJ
 */
public class DeploymentSystem implements ModelNotifier{

    protected Piston actuator;
    protected Servo panelServo;

    public DeploymentSystem(){
        this.actuator = new Piston(Constants.C_ACTUATOR_SOLENOID_FRONT,
                Constants.C_ACTUATOR_SOLENOID_BACK,
                false);
        this.panelServo = new Servo(Constants.C_SERVO);
    }

    public void setActuate(boolean released){
        if(released){
            this.actuator.extend();
        }else{
            this.actuator.retract();
        }
    }

    public void setPanelDeploy(boolean deploy){
        if(deploy){
            this.panelServo.set(Constants.C_SERVO_DEPLOY);
        }else{
            this.panelServo.set(Constants.C_SERVO_REST);
        }
    }

    public void updateModel(Model model){
        updateDeploymentSystem(model.getCurrentRobotState().getDeploymentState());
    }

    protected void updateDeploymentSystem(DeploymentState deploymentState){
        deploymentState.getActuatorState().isExtended = this.actuator.isExtended();
        deploymentState.servoExtended = (this.panelServo.get() == Constants.C_SERVO_DEPLOY);
    }
}
