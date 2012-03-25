/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.team1160.logomotion.outputManager;

import com.team1160.logomotion.arm.Arm;
import com.team1160.logomotion.controlCommands.DeploymentSystemCommand;
import com.team1160.logomotion.controlCommands.arm.ArmCommand;
import com.team1160.logomotion.controlCommands.arm.ShoulderCommand;
import com.team1160.logomotion.controlCommands.arm.TurnTableCommand;
import com.team1160.logomotion.controlCommands.arm.WristCommand;
import com.team1160.logomotion.drive.DriveTrain;
import com.team1160.logomotion.controlCommands.DriveTrainCommand;
import com.team1160.logomotion.minibot.DeploymentSystem;
import com.team1160.logomotion.pnuematics.Pnuematics;
import com.team1160.logomotion.teleopManager.RobotCommand;

/**
 *
 * @author CJ
 */
public class OutputManager {

    private static OutputManager _INSTANCE = new OutputManager();

    protected Pnuematics pnuematics;
    protected Arm arm;
    protected DriveTrain driveTrain;
    protected DeploymentSystem deploymentSystem;
    
    private OutputManager(){
        
    }

    public static OutputManager getInstance(){
        if(_INSTANCE == null){
            _INSTANCE = new OutputManager();
        }
        return _INSTANCE;
    }

    public void setArm(Arm arm){ this.arm = arm;}
    public void setPnuematics(Pnuematics pnuematics){ this.pnuematics = pnuematics;}
    public void setDriveTrain(DriveTrain driveTrain){ this.driveTrain = driveTrain;}
    public void setDeploymentSystem(DeploymentSystem deploymentSystem){ this.deploymentSystem = deploymentSystem; }

    public void outputToRobot(RobotCommand robotCommand){
        if(robotCommand == null){
            throw new IllegalArgumentException("In OutputManger:outputToRobot, " +
                    "robotCommand instance was null.");
        }
        outputArm(robotCommand.getArmCommand());
        outputDriveTrain(robotCommand.getDriveTrainCommand());
        outputDeploymentSystem(robotCommand.getDeploymentSystemCommand());
    }

    protected void outputDeploymentSystem(DeploymentSystemCommand deploymentSystemCommand){
        if(this.deploymentSystem == null){
            throw new IllegalStateException("In OutputManager:outputDeploymentSystem, " +
                    "deployment instance was null.");
        }
        this.deploymentSystem.setActuate(deploymentSystemCommand.actuator_extend);
        this.deploymentSystem.setPanelDeploy(deploymentSystemCommand.panel_deploy);
    }

    protected void outputDriveTrain(DriveTrainCommand driveTrainCommand){
        if(this.driveTrain == null){
            throw new IllegalStateException("In OutputManager:outputDriveTrain, " +
                    "drivetrain instance was null.");
        }
        this.driveTrain.driveSpeeds(driveTrainCommand.drivetrain_speed_left_jag, driveTrainCommand.drivetrain_speed_right_jag);
    }

    protected void outputArm(ArmCommand armCommand){
        outputShoulder(armCommand.getShoulderCommand());
        outputWrist(armCommand.getWristCommand());
        outputTurnTable(armCommand.getTurnTableCommand());
    }

    protected void outputTurnTable(TurnTableCommand turnTableCommand){
        this.arm.getTurnTable().rotateRight(turnTableCommand.turntable_victor_pwm);
    }

    protected void outputWrist(WristCommand wristCommand){
        this.arm.getWrist().rotateRight(wristCommand.wrist_victor_pwm);
    }

    protected void outputShoulder(ShoulderCommand shoulderCommand){
        this.arm.getShoulder().rotateLeft(shoulderCommand.shoulder_left_victor_pwm);
        this.arm.getShoulder().rotateRight(shoulderCommand.shoulder_right_victor_pwm);
        if(shoulderCommand.reset_gyro)
            this.arm.resetShoulderGyro();
    }
}
