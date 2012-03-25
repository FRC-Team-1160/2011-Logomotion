/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.team1160.logomotion.input.controllerStates;

import com.team1160.logomotion.model.states.arm.DOFState;
import com.team1160.logomotion.arm.positionStates.DOFPosition;

/**
 *
 * @author CJ
 */
public class WristJoystickState {
    
    public DOFState dofState;
    public DOFPosition dofPosition;
    public double desiredSpeed;

    public String toString(){
        StringBuffer output = new StringBuffer("/-----\n");
        output.append("DOF2JoystickState\n");
        output.append("State: ").append(dofState.toString()).append('\n');
        output.append("Position: ").append(dofPosition.toString()).append('\n');
        output.append("Desired Speed: ").append(desiredSpeed).append('\n');
        return output.toString();
    }

}
