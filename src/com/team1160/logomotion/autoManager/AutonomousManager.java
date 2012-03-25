/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.team1160.logomotion.autoManager;

import com.team1160.logomotion.api.Constants;
import com.team1160.logomotion.controlCommands.DeploymentSystemCommand;
import com.team1160.logomotion.controlCommands.DriveTrainCommand;
import com.team1160.logomotion.controlCommands.arm.ArmCommand;
import com.team1160.logomotion.controlCommands.arm.ShoulderCommand;
import com.team1160.logomotion.controlCommands.arm.TurnTableCommand;
import com.team1160.logomotion.controlCommands.arm.WristCommand;
import com.team1160.logomotion.model.RobotState;
import com.team1160.logomotion.teleopManager.RobotCommand;

/**
 *
 * @author CJ
 */
public class AutonomousManager {

    private static AutonomousManager _INSTANCE = new AutonomousManager();

    protected boolean firstRun;
    protected boolean foward;
    protected boolean waiting;
    protected boolean placed;
    protected boolean backingUp;
    protected boolean done;

    protected long startTime;
    protected long placeTime;
    protected long backUpStartTime;

    protected final int TIME_FORWARD = 1000;
    protected final int TIME_WAIT = 3000;
    protected final int TIME_BACK = 2000;

    private AutonomousManager(){
        this.reset();
    }

    public static AutonomousManager getInstance(){
        if(_INSTANCE == null){
            _INSTANCE = new AutonomousManager();
        }
        return _INSTANCE;
    }

    public void reset(){
        this.firstRun = true;
        this.foward = false;
        this.placed = false;
        this.waiting = false;
        this.backingUp = false;
        this.done = false;
    }

    public RobotCommand getAutoCommand(RobotState robotState){
        RobotCommand robotCommand = new RobotCommand();

        if(this.done){
            this.waiting = true;
            System.out.println("Done.");
        }else if(this.firstRun){
            System.out.println("First Run.");
            this.firstRun = false;
            this.foward = true;
            this.startTime = System.currentTimeMillis();
        }else if(!placed && ((System.currentTimeMillis() - this.startTime) > this.TIME_FORWARD)){
            System.out.println("Done going Foward.");
            this.foward = false;
            this.placed = true;
            this.waiting = true;
            this.placeTime = System.currentTimeMillis();
        }else if(!foward && !backingUp && ((System.currentTimeMillis() - this.placeTime) > this.TIME_WAIT)){
            System.out.println("Done Waiting.");
            this.waiting = false;
            this.backingUp = true;
            this.backUpStartTime = System.currentTimeMillis();
        }else if(!foward && backingUp && (System.currentTimeMillis() - this.backUpStartTime) > this.TIME_BACK){
            System.out.println("Done going backwards.");
            this.waiting = true;
            this.done = true;
        }

        ArmCommand armCommand = getArmCommand(robotState);
        DeploymentSystemCommand deploymentSystemCommand = getDeploymentSystemCommand(robotState);
        DriveTrainCommand driveTrainCommand = getDriveTrainCommand(robotState);

//        robotCommand.setArmCommand(armCommand);
        robotCommand.setDeploymentSystemCommand(deploymentSystemCommand);
        robotCommand.setDriveTrainCommand(driveTrainCommand);

        return robotCommand;
    }

    protected DeploymentSystemCommand getDeploymentSystemCommand(RobotState robotState){
        DeploymentSystemCommand deploymentSystemCommand = new DeploymentSystemCommand();

        deploymentSystemCommand.actuator_extend = false;
        deploymentSystemCommand.panel_deploy = false;

        return deploymentSystemCommand;
    }

    protected DriveTrainCommand getDriveTrainCommand(RobotState robotState){
        DriveTrainCommand driveTrainCommand = new DriveTrainCommand();

        double speed = 0.5;

        if(this.foward){
            driveTrainCommand.drivetrain_speed_left_jag = speed;
            driveTrainCommand.drivetrain_speed_left_jag *= 2.9/3.3;
            driveTrainCommand.drivetrain_speed_right_jag = -speed;
        }else if(backingUp){
            driveTrainCommand.drivetrain_speed_left_jag = -speed;
            driveTrainCommand.drivetrain_speed_left_jag *= 2.9/3.3;
            driveTrainCommand.drivetrain_speed_right_jag = speed;
        }else if(waiting){
            driveTrainCommand.drivetrain_speed_left_jag = 0.0;
            driveTrainCommand.drivetrain_speed_right_jag = 0.0;
        }else{
            throw new IllegalStateException("In getDrive: fell through in states.");
        }

        return null;
    }

    protected ArmCommand getArmCommand(RobotState robotState){
        ArmCommand armCommand = new ArmCommand();

        ShoulderCommand shoulderCommand = getShoulderCommand(robotState);
        WristCommand wristCommand = getWristCommand(robotState);
        TurnTableCommand turnTableCommand = getTurnTableCommand(robotState);

        armCommand.setShoulderCommand(shoulderCommand);
        armCommand.setWristCommand(wristCommand);
        armCommand.setTurnTableCommand(turnTableCommand);

        return armCommand;
    }

    protected ShoulderCommand getShoulderCommand(RobotState robotState){
        ShoulderCommand shoulderCommand = new ShoulderCommand();

        double currentAngle = robotState.getArmState().getShoulderState().shoulderAngle;
        double desiredAngle = -1;

        double upSpeed = Constants.K_ARM_SHOULDER_UP_SPEED;
        double downSpeed = Constants.K_ARM_SHOULDER_DOWN_SPEED;

        if(this.foward){
            desiredAngle = Constants.K_ARM_SHOULDER_GYRO_SIDE_MID;
        }else if(this.waiting){
            desiredAngle = -1;
        }else if(this.backingUp){
            desiredAngle = -1;
        }else{
            throw new IllegalStateException("In getShoulder: fell through in states.");
        }

        if(desiredAngle == -1){
            shoulderCommand.shoulder_left_victor_pwm = 128;
            shoulderCommand.shoulder_right_victor_pwm = 128;
        }else if((Math.abs(currentAngle - desiredAngle) < Constants.K_ARM_SHOULDER_GYRO_DEADBAND)){
            shoulderCommand.shoulder_left_victor_pwm = 128;
            shoulderCommand.shoulder_right_victor_pwm = 128;
        }else if(desiredAngle < currentAngle){
            //Increase our angle, go up
            shoulderCommand.shoulder_left_victor_pwm = (int)(upSpeed * 127) + 128;
            shoulderCommand.shoulder_right_victor_pwm = (int)(upSpeed * 127) + 128;
        }else if(desiredAngle > currentAngle){
            //Descrease our angle, go down{
            shoulderCommand.shoulder_left_victor_pwm = -(int)(downSpeed * 127) + 128;
            shoulderCommand.shoulder_right_victor_pwm = -(int)(downSpeed * 127) + 128;
        }

        return shoulderCommand;
    }

    protected WristCommand getWristCommand(RobotState robotState){
        
        WristCommand wristCommand = new WristCommand();

        double currentValue = robotState.getArmState().getWristState().potValue;
        double desiredValue = -1;

        if(this.foward){
            desiredValue = Constants.K_ARM_WRIST_POT_SIDE_MID;
        }else if(this.waiting){
            desiredValue = Constants.K_ARM_WRIST_POT_SIDE_TOP;
        }else if(this.backingUp){
            desiredValue = Constants.K_ARM_WRIST_POT_SIDE_TOP;
        }else{
            throw new IllegalStateException("In getShoulder: fell through in states.");
        }

        if(desiredValue == -1){
            wristCommand.wrist_victor_pwm = 128;
        }else if((Math.abs(currentValue - desiredValue) < Constants.K_ARM_WRIST_POT_DEADBAND)){
            wristCommand.wrist_victor_pwm = 128;
        }else if(desiredValue > currentValue){
            //Increase our angle, go down
            wristCommand.wrist_victor_pwm = -(int)(Constants.K_ARM_WRIST_SPEED * 127) + 128;
        }else if(desiredValue < currentValue){
            //Descrease our angle, go up
            wristCommand.wrist_victor_pwm = (int)(Constants.K_ARM_WRIST_SPEED * 127) + 128;
        }

        return wristCommand;
    }

    protected TurnTableCommand getTurnTableCommand(RobotState robotState){
        TurnTableCommand turnTableCommand = new TurnTableCommand();

        turnTableCommand.turntable_victor_pwm = 128;

        return turnTableCommand;
    }
}
