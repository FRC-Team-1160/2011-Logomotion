/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.team1160.logomotion.util;

import edu.wpi.first.wpilibj.Joystick;

/**
 * A wrapper class for the joystick interface to a logitech gamepad.
 * @author sakekasi
 */
public class GamePad {
    Joystick gamepad;

    public GamePad(int channel) {  // The constructor takes a channel to let the computer know where to "find" the gamepad
        this.gamepad = new Joystick(channel);
    }


    public double getLeftJoystickX() {
        return gamepad.getRawAxis(1);
    }

    public double getLeftJoystickY() {
        return gamepad.getRawAxis(2);
    }

    public double getRightJoystickX(){
        return gamepad.getRawAxis(3);
    }

    public double getRightJoystickY() {
        return gamepad.getRawAxis(4);
    }

    public boolean getRawButton(int button) {  // Checks the state of a given button
        return gamepad.getRawButton(button);
    }

  

}
