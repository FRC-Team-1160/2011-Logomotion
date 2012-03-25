/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.team1160.logomotion.arm;

import com.team1160.logomotion.model.states.arm.ArmState;
import com.team1160.logomotion.api.Constants;
import com.team1160.logomotion.model.states.arm.ShoulderState;
import com.team1160.logomotion.model.states.arm.TurnTableState;
import com.team1160.logomotion.model.states.arm.WristState;
import com.team1160.logomotion.model.Model;
import com.team1160.logomotion.model.ModelNotifier;
import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.Gyro;

/**
 *
 * @author CJ
 */
public class Arm implements ModelNotifier{

    protected DOF shoulder;
    protected Gyro shoulderAngle;

    protected DOF wrist;
    protected AnalogChannel wristPot;

    protected DOF turnTable;
    protected AnalogChannel tablePot;

    public Arm(){
        this.shoulder = new DOF(Constants.C_ARM_SHOULDER_VIC_RIGHT, Constants.C_ARM_SHOULDER_VIC_LEFT);
        this.wrist = new DOF(Constants.C_ARM_WRIST_VIC, false);
        this.turnTable = new DOF(Constants.C_ARM_TURNTABLE_JAG, true);

        this.shoulderAngle = new Gyro(Constants.C_ARM_SHOULER_GYRO);
        this.wristPot = new AnalogChannel(Constants.C_ARM_WRIST_POT);
        this.tablePot = new AnalogChannel(Constants.C_ARM_TURNTABLE_POT);
    }

    public DOF getShoulder(){
        return this.shoulder;
    }

    public void resetShoulderGyro(){
        this.shoulderAngle.reset();
    }

    public DOF getWrist(){
        return this.wrist;
    }

    public DOF getTurnTable(){
        return this.turnTable;
    }

    public void updateModel(Model model){
        updateArmState(model.getCurrentRobotState().getArmState());
    }

    protected void updateArmState(ArmState armState){
        updateArmStateShoulder(armState.getShoulderState());
        updateArmStateWrist(armState.getWristState());
        updateArmStateTurn(armState.getTurnTableState());
    }

    protected void updateArmStateWrist(WristState wristState){
        wristState.lastSetPWM = this.wrist.rightVictor.getRaw();
        wristState.potValue = this.wristPot.getAverageVoltage();
    }

    protected void updateArmStateTurn(TurnTableState turnTableState){
        turnTableState.lastSetPWM = this.turnTable.motor.getRaw();
        turnTableState.potValue = this.tablePot.getAverageVoltage();
    }

    protected void updateArmStateShoulder(ShoulderState shoulderState){
        shoulderState.lastPWMLeft = this.shoulder.leftVictor.getRaw();
        shoulderState.lastPWMRight = this.shoulder.rightVictor.getRaw();
        shoulderState.shoulderAngle = this.shoulderAngle.getAngle();
    }
}
