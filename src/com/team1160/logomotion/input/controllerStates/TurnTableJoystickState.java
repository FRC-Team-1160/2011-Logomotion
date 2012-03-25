/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.team1160.logomotion.input.controllerStates;

/**
 *
 * @author CJ
 */
public class TurnTableJoystickState {

    public int value;

    private TurnTableJoystickState(int value){this.value = value;}

    public static TurnTableJoystickState kStop = new TurnTableJoystickState(1);
    public static TurnTableJoystickState kClockWise = new TurnTableJoystickState(2);
    public static TurnTableJoystickState kCounterClockWise = new TurnTableJoystickState(3);

    public boolean equals(TurnTableJoystickState one, TurnTableJoystickState two){
        return one.value == two.value;
    }

    public String toString(){
        String output = "/-----\n";
        output += "TurnTableJoystickState\n";
        output += "State: ";
        switch(value){
            case 1: output += "Stop"; break;
            case 2: output += "ClockWise"; break;
            case 3: output += "CounterClockWise"; break;
            default: throw new IllegalStateException("In TurnTableJoystickState:" +
                    "toString, instance has invalid value.");
        }
        output += '\n';
        return output;
    }

}
