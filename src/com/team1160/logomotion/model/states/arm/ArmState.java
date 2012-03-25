/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.team1160.logomotion.model.states.arm;

/**
 *
 * @author CJ
 */
public class ArmState {

    public ArmState(){
        this.shoulderState = new ShoulderState();
        this.wristState = new WristState();
        this.turnTableState = new TurnTableState();
    }

    protected ShoulderState shoulderState;
    public ShoulderState getShoulderState(){return this.shoulderState;}
    public void setShoulderState(ShoulderState shoulderState){
        this.shoulderState = shoulderState;
    }

    protected WristState wristState;
    public WristState getWristState(){return this.wristState;}
    public void setWristState(WristState wristState){
        this.wristState = wristState;
    }

    protected TurnTableState turnTableState;
    public TurnTableState getTurnTableState(){return this.turnTableState;}
    public void setTurnTableState(TurnTableState turnTableState){
        this.turnTableState = turnTableState;
    }

    public String toString(){
        String output = "/-------\n";
        output += "Arm State\n";
        output += this.shoulderState.toString();
        output += this.wristState.toString();
        output += this.turnTableState.toString();
        return output;
    }

}
