/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.team1160.logomotion.model.states.arm;

/**
     * Represents the state of an arm DOF.
 *
 * @author CJ
*/
public class DOFState{

    protected int value;

    static final int kStop_val = -1;
    static final int kForward_val = -69;
    static final int kBackward_val = 69;

    private DOFState(int value){
        this.value = value;
    }

    public String toString(){
        switch(value){
            case kStop_val: return "Stop";
            case kForward_val: return "Forward";
            case kBackward_val: return "Backward";
        }
        throw new IllegalStateException("In DOF_State:toString, instance has" +
                "a nonvalid value.");
    }

    public static final DOFState kStop = new DOFState(kStop_val);
    public static final DOFState kForward = new DOFState(kForward_val);
    public static final DOFState kBackward = new DOFState(kBackward_val);

    public boolean equals(DOFState one, DOFState two){
        return one.value == two.value;
    }
}
