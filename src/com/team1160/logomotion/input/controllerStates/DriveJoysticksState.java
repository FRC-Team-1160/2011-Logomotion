/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.team1160.logomotion.input.controllerStates;

/**
 *
 * @author CJ
 */
public class DriveJoysticksState {

    //-------------------------------------------------------------------------
    //Drive Stick Axis

    /**
     * A value from -1.0 to 1.0 representing the value of the joystick.  If
     * Neg is forward.
     */
    public double drive_joystick_right_y_axis;
    public double getDriveJoystickRightYAxis(){return this.drive_joystick_right_y_axis;}

    /**
     * A value from -1.0 to 1.0 representing the value of the joystick.  If
     * Neg is forward.
     */
    public double drive_joystick_left_y_axis;
    public double getDriveJoystickLeftYAxis(){return this.drive_joystick_left_y_axis;}

    public String toString(){
        String output = "";
        output += "/------\n";
        output += "JoystickState\n";
        output += "Right Drive Joystick: " + this.drive_joystick_right_y_axis + '\n';
        output += "Left Drive Joystick: " + this.drive_joystick_left_y_axis + '\n';
        return output;
    }
}
