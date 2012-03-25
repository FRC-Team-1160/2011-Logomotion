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
public class ShoulderJoystickState {
    
    public DOFState dofState;
    public DOFPosition dofPosition;
    public double desiredAdjustSpeed;
    public boolean adjustRight;
    public boolean adjustLeft;
    public boolean resetGyro;

    public String toString(){
        StringBuffer buffer = new StringBuffer("/-----\n");
        buffer.append("DOF1JoystickState\n");
        buffer.append("State: ").append(dofState.toString()).append('\n');
        buffer.append("Position: ").append(dofPosition.toString()).append('\n');
        buffer.append("Adjust Right: ").append(adjustRight).append('\n');
        buffer.append("Adjust Left: ").append(adjustLeft).append('\n');
        buffer.append("Adjust Desired Speed: ").append(desiredAdjustSpeed).append('\n');
        buffer.append("Reset Gyro? :").append(resetGyro).append('\n');
        return buffer.toString();
    }
}
