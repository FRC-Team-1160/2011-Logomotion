/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.team1160.logomotion.controlCommands.arm;

/**
 *
 * @author CJ
 */
public class ArmCommand {
    
    protected ShoulderCommand shoulderCommand;
    public ShoulderCommand getShoulderCommand(){ return this.shoulderCommand; }
    public void setShoulderCommand(ShoulderCommand shoulderCommand){ this.shoulderCommand = shoulderCommand; }

    protected WristCommand wristCommand;
    public WristCommand getWristCommand(){ return this.wristCommand; }
    public void setWristCommand(WristCommand wristCommand){ this.wristCommand = wristCommand;}

    protected TurnTableCommand turnTableCommand;
    public TurnTableCommand getTurnTableCommand(){ return this.turnTableCommand; }
    public void setTurnTableCommand(TurnTableCommand turnTableCommand){ this.turnTableCommand = turnTableCommand; }

    public String toString(){
        String output = "/-------\n";
        output += "Arm Command\n";
        output += this.shoulderCommand.toString();
        output += this.wristCommand.toString();
        output += this.turnTableCommand.toString();
        return output;
    }

}
