/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.team1160.logomotion.teleopManager;

import com.team1160.logomotion.api.Constants;
import com.team1160.logomotion.arm.positionStates.DOFPosition;
import com.team1160.logomotion.model.states.arm.DOFState;
import com.team1160.logomotion.controlCommands.arm.ArmCommand;
import com.team1160.logomotion.controlCommands.arm.ShoulderCommand;
import com.team1160.logomotion.controlCommands.arm.TurnTableCommand;
import com.team1160.logomotion.controlCommands.arm.WristCommand;
import com.team1160.logomotion.input.controllerStates.ShoulderJoystickState;
import com.team1160.logomotion.input.controllerStates.WristJoystickState;
import com.team1160.logomotion.controlCommands.DriveTrainCommand;
import com.team1160.logomotion.drive.pid.PidComputation;
import com.team1160.logomotion.input.InputState;
import com.team1160.logomotion.controlCommands.DeploymentSystemCommand;
import com.team1160.logomotion.input.controllerStates.ModelArmControlState;
import com.team1160.logomotion.input.controllerStates.TurnTableJoystickState;
import com.team1160.logomotion.model.RobotState;
import edu.wpi.first.wpilibj.DriverStation;

/**
 *
 * @author CJ
 */
public class TeleopManager {

    private static TeleopManager _INSTANCE = new TeleopManager();

    protected PidComputation pidControllerRight;
    protected PidComputation pidControllerLeft;

    public TeleopManager(){
        this.pidControllerRight = new PidComputation();
        this.pidControllerLeft = new PidComputation();
    }

    public static TeleopManager getInstance(){
        if(_INSTANCE == null){
            _INSTANCE = new TeleopManager();
        }
        return _INSTANCE;
    }

    public void resetEncoders(){
        this.pidControllerLeft.reset();
        this.pidControllerRight.reset();
    }

    public RobotCommand getRobotCommand(InputState inputState, RobotState robotState){
        RobotCommand robotCommand = new RobotCommand();

        ArmCommand armCommand = getArmCommand(inputState, robotState);
        DeploymentSystemCommand deploymentSystemCommand = getDeploymentSystemCommand(inputState, robotState);
        DriveTrainCommand driveTrainCommand = getDriveTrainCommand(inputState, robotState);

        robotCommand.setDeploymentSystemCommand(deploymentSystemCommand);
        robotCommand.setArmCommand(armCommand);
        robotCommand.setDriveTrainCommand(driveTrainCommand);

        return robotCommand;
    }

    protected DeploymentSystemCommand getDeploymentSystemCommand(InputState inputState, RobotState robotState){
        DeploymentSystemCommand deploymentSystemCommand = new DeploymentSystemCommand();

        if(robotState.getDeploymentState().getActuatorState().isExtended){
            deploymentSystemCommand.actuator_extend = true;
        }else{
            deploymentSystemCommand.actuator_extend = inputState.getMinibotControlState().runActuator;
        }

        deploymentSystemCommand.panel_deploy = inputState.getMinibotControlState().triggerPanel;

        return deploymentSystemCommand;
    }

    protected ArmCommand getArmCommand(InputState inputState, RobotState robotState){
        ArmCommand armCommand = new ArmCommand();

        ShoulderCommand shoulderCommand = getShoulderCommand(inputState, robotState);
        WristCommand wristCommand = getWristCommand(inputState, robotState);
        TurnTableCommand turnTableCommand = getTurnTableCommand(inputState, robotState);

        armCommand.setShoulderCommand(shoulderCommand);
        armCommand.setWristCommand(wristCommand);
        armCommand.setTurnTableCommand(turnTableCommand);

        return armCommand;
    }

    protected TurnTableCommand getTurnTableCommand(InputState inputState, RobotState robotState){
        TurnTableCommand turnTableCommand = new TurnTableCommand();

        TurnTableJoystickState joystickState = inputState.getTurnTableJoystickState();

        if(joystickState.equals(TurnTableJoystickState.kStop)){
            turnTableCommand.turntable_victor_pwm = 128;
        }else if(joystickState.equals(TurnTableJoystickState.kClockWise)){
            turnTableCommand.turntable_victor_pwm = 70;
        }else if(joystickState.equals(TurnTableJoystickState.kCounterClockWise)){
            turnTableCommand.turntable_victor_pwm = 177;
        }

        return turnTableCommand;
    }

    protected WristCommand getWristCommand(InputState inputState, RobotState robotState){
        WristCommand wristCommand = new WristCommand();

        WristJoystickState joystickState = inputState.getDOF2JoystickState();
        ModelArmControlState modelArmControlState = inputState.getModelArmState();

        if(inputState.getModelArmState().enabled){
            double desired = modelArmControlState.wristPotValue/Constants.K_MODEL_ARM_MINI_WRIST_MAX;
            double current = 1 - robotState.getArmState().getWristState().potValue/Constants.K_MODEL_ARM_REAL_WRIST_MIN;

            if(Math.abs(current - desired) < Constants.K_MODEL_ARM_DELTA_WRIST){
                wristCommand.wrist_victor_pwm = 128;
            }
            else if(desired > current){
                //must move wrist upwards
                wristCommand.wrist_victor_pwm = (int)(Constants.K_ARM_WRIST_SPEED * 127) + 128;
            }else if(desired < current){
                //must move wrist downward
                wristCommand.wrist_victor_pwm = -(int)(Constants.K_ARM_WRIST_SPEED * 127) + 128;
            }else{
                 throw new IllegalArgumentException("In TeleopManager:getWristCommand, Fell through on model arm determination.");
            }
            
        }else if(joystickState.dofPosition.equals(DOFPosition.kUserMove)){
            if(joystickState.dofState == DOFState.kStop){
                wristCommand.wrist_victor_pwm = 128;
            }else if(joystickState.dofState == DOFState.kForward){
                wristCommand.wrist_victor_pwm = (int)(joystickState.desiredSpeed * 127 * 0.375) + 128;
            }else if(joystickState.dofState == DOFState.kBackward){
                wristCommand.wrist_victor_pwm = -(int)(joystickState.desiredSpeed * 127 * 0.375) + 128;
            }else{
                throw new IllegalArgumentException("In TeleopManager: TeleopManager, " +
                        "fell through on wrist state.");
            }
        }else{
            double currentValue = robotState.getArmState().getWristState().potValue;
            double desiredValue = -1;

             if(joystickState.dofPosition.equals(DOFPosition.kScoreLowSide)){
                desiredValue = Constants.K_ARM_WRIST_POT_SIDE_BOT;
            }else if(joystickState.dofPosition.equals(DOFPosition.kScoreMidSide)){
                desiredValue = Constants.K_ARM_WRIST_POT_SIDE_MID;
            }else if(joystickState.dofPosition.equals(DOFPosition.kScoreTopSide)){
                desiredValue = Constants.K_ARM_WRIST_POT_SIDE_TOP;
            }else if(joystickState.dofPosition.equals(DOFPosition.kScoreLowMid)){
                desiredValue = Constants.K_ARM_WRIST_POT_MID_BOT;
            }else if(joystickState.dofPosition.equals(DOFPosition.kScoreMidMid)){
                desiredValue = Constants.K_ARM_WRIST_POT_MID_MID;
            }else if(joystickState.dofPosition.equals(DOFPosition.kScoreTopMid)){
                desiredValue = Constants.K_ARM_WRIST_POT_MID_TOP;
            }else if(joystickState.dofPosition.equals(DOFPosition.kStow)){
                desiredValue = Constants.K_ARM_WRIST_POT_STOW;
            }else if(joystickState.dofPosition.equals(DOFPosition.kReload)){
                desiredValue = Constants.K_ARM_WRIST_POT_RELOAD;
            }else if(joystickState.dofPosition.equals(DOFPosition.kReloadTop)){
                desiredValue = Constants.K_ARM_WRIST_POT_RELOAD_TOP;
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
        }

        return wristCommand;
    }

    protected ShoulderCommand getShoulderCommand(InputState inputState, RobotState robotState){
        ShoulderCommand shoulderCommand = new ShoulderCommand();

        ShoulderJoystickState joystickState = inputState.getDOF1JoystickState();

        ModelArmControlState modelArmControlState = inputState.getModelArmState();
//        double upSpeed = 2.420/3.3;
//        double downSpeed = 1.029/3.3;
        double upSpeed = Constants.K_ARM_SHOULDER_UP_SPEED;
        double downSpeed = Constants.K_ARM_SHOULDER_DOWN_SPEED;

        if(modelArmControlState.enabled){
            //Desired is percent down
            double desired = Math.abs(modelArmControlState.shoulderPotValue - Constants.K_MODEL_ARM_MINI_SHOULDER_MAX)/
                    (Constants.K_MODEL_ARM_MINI_SHOULDER_MIN - Constants.K_MODEL_ARM_MINI_SHOULDER_MAX);

            //Current is the same percent down
            double current = 1 - Math.abs(robotState.getArmState().getShoulderState().shoulderAngle/Constants.K_MODEL_ARM_REAL_SHOULDER_MAX);


            if(Math.abs(current - desired) < Constants.K_MODEL_ARM_DELTA_SHOULDER){
                shoulderCommand.shoulder_left_victor_pwm = 128;
                shoulderCommand.shoulder_right_victor_pwm = 128;
            }else if(desired > current){
                //Lower Arm
                shoulderCommand.shoulder_left_victor_pwm = -(int)(downSpeed * 127) + 128;
                shoulderCommand.shoulder_right_victor_pwm = -(int)(downSpeed * 127) + 128;
            }else if(desired < current){
                //Raise Arm
                shoulderCommand.shoulder_left_victor_pwm = (int)(upSpeed * 127) + 128;// * leftScale;
                shoulderCommand.shoulder_right_victor_pwm = (int)(upSpeed * 127) + 128;
            }else{
                throw new IllegalArgumentException("In TeleopManager:getShoulderCommand, Fell through on model arm determination.");
            }
            
        }else if(joystickState.dofPosition.equals(DOFPosition.kUserMove)){
            if(!joystickState.adjustLeft && !joystickState.adjustRight){
                if(joystickState.dofState == DOFState.kStop){
                    shoulderCommand.shoulder_left_victor_pwm = 128;
                    shoulderCommand.shoulder_right_victor_pwm = 128;
                }else if(joystickState.dofState == DOFState.kForward){
                    shoulderCommand.shoulder_left_victor_pwm = (int)(upSpeed * 127) + 128;// * leftScale;
                    shoulderCommand.shoulder_right_victor_pwm = (int)(upSpeed * 127) + 128;// * rightScale;
                }else if(joystickState.dofState == DOFState.kBackward){
                    shoulderCommand.shoulder_left_victor_pwm = -(int)(downSpeed * 127) + 128;// * leftScale;
                    shoulderCommand.shoulder_right_victor_pwm = -(int)(downSpeed * 127) + 128;// * rightScale;
                }else{
                    throw new IllegalArgumentException("In TeleopManger:getShoulderCommand, Fell through on desired DOF states.");
                }
            }else if(joystickState.adjustLeft){
                shoulderCommand.shoulder_right_victor_pwm = 128;
                shoulderCommand.shoulder_left_victor_pwm = (int)(joystickState.desiredAdjustSpeed * 127) + 128;
            }else if(joystickState.adjustRight){
                shoulderCommand.shoulder_left_victor_pwm = 128;
                shoulderCommand.shoulder_right_victor_pwm = (int)(joystickState.desiredAdjustSpeed * 127) + 128;
            }else{
                throw new IllegalArgumentException("In TeleopMaanger:getShoulderCommand, Fell through on arm adjusting.");
            }
        }else{

            double currentAngle = robotState.getArmState().getShoulderState().shoulderAngle;
            double desiredAngle = -1;

            if(joystickState.dofPosition.equals(DOFPosition.kScoreLowSide)){
                desiredAngle = Constants.K_ARM_SHOULDER_GYRO_SIDE_BOT;
            }else if(joystickState.dofPosition.equals(DOFPosition.kScoreMidSide)){
                desiredAngle = Constants.K_ARM_SHOULDER_GYRO_SIDE_MID;
            }else if(joystickState.dofPosition.equals(DOFPosition.kScoreTopSide)){
                desiredAngle = Constants.K_ARM_SHOULDER_GYRO_SIDE_TOP;
            }else if(joystickState.dofPosition.equals(DOFPosition.kScoreLowMid)){
                desiredAngle = Constants.K_ARM_SHOULDER_GYRO_MID_BOT;
            }else if(joystickState.dofPosition.equals(DOFPosition.kScoreMidMid)){
                desiredAngle = Constants.K_ARM_SHOULDER_GYRO_MID_MID;
            }else if(joystickState.dofPosition.equals(DOFPosition.kScoreTopMid)){
                desiredAngle = Constants.K_ARM_SHOULDER_GYRO_MID_TOP;
            }else if(joystickState.dofPosition.equals(DOFPosition.kStow)){
                desiredAngle = Constants.K_ARM_SHOULDER_GYRO_STOW;
            }else if(joystickState.dofPosition.equals(DOFPosition.kReload)){
                desiredAngle = Constants.K_ARM_SHOULDER_GYRO_RELOAD;
            }else if(joystickState.dofPosition.equals(DOFPosition.kReloadTop)){
                desiredAngle = Constants.K_ARM_SHOULDER_GYRO_RELOAD_TOP;
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

        }


        shoulderCommand.reset_gyro = joystickState.resetGyro;

        return shoulderCommand;
    }

    protected DriveTrainCommand getDriveTrainCommand(InputState inputState, RobotState robotState){
        DriveTrainCommand driveTrainCommand = new DriveTrainCommand();

        //Regular sending right now
        double rightSpeed = inputState.getDriveJoystickState().drive_joystick_right_y_axis;
        double leftSpeed = inputState.getDriveJoystickState().drive_joystick_left_y_axis;
        
        double currentLeftSpeed = robotState.getDriveTrainState().encoder_left / Constants.DT_ENCODER_MAX_LEFT;
        double currentRightSpeed = robotState.getDriveTrainState().encoder_right / Constants.DT_ENCODER_MAX_RIGHT;

        double outputLeftSpeed = this.pidControllerLeft.pidNext(currentLeftSpeed, leftSpeed);
        double outputRightSpeed = this.pidControllerRight.pidNext(currentRightSpeed, rightSpeed);

        //if(DriverStation.getInstance().getDigitalIn(2) && DriverStation.getInstance().getDigitalIn(4)){
        /*    driveTrainCommand.drivetrain_speed_left_jag = leftSpeed * (DriverStation.getInstance().getAnalogIn(2)/3.3);
            driveTrainCommand.drivetrain_speed_right_jag = -rightSpeed * (DriverStation.getInstance().getAnalogIn(1)/3.3);

            System.out.println("Left: " + (DriverStation.getInstance().getAnalogIn(2)/3.3));
            System.out.println("Right: " + (DriverStation.getInstance().getAnalogIn(1)/3.3));*/
        //}else{
            double moveValue = DriverStation.getInstance().getStickAxis(Constants.C_JOYSTICK_RIGHT, 2);
            double rotateValue =  DriverStation.getInstance().getStickAxis(Constants.C_JOYSTICK_RIGHT, 1) * (2.6/3.3);
            if (moveValue > 0.0) {
                if (rotateValue > 0.0) {
                    driveTrainCommand.drivetrain_speed_left_jag = moveValue - rotateValue;
                    driveTrainCommand.drivetrain_speed_right_jag = Math.max(moveValue, rotateValue);
                } else {
                    driveTrainCommand.drivetrain_speed_left_jag = Math.max(moveValue, -rotateValue);
                    driveTrainCommand.drivetrain_speed_right_jag = moveValue + rotateValue;
                }
            } else {
                if (rotateValue > 0.0) {
                    driveTrainCommand.drivetrain_speed_left_jag = -Math.max(-moveValue, rotateValue);
                    driveTrainCommand.drivetrain_speed_right_jag= moveValue + rotateValue;
                } else {
                    driveTrainCommand.drivetrain_speed_left_jag = moveValue - rotateValue;
                    driveTrainCommand.drivetrain_speed_right_jag = -Math.max(-moveValue, -rotateValue);
                }
            }
            driveTrainCommand.drivetrain_speed_right_jag *= -1;

            driveTrainCommand.drivetrain_speed_left_jag *= 2.9/3.3;
//            driveTrainCommand.drivetrain_speed_right_jag *= (DriverStation.getInstance().getAnalogIn(1)/3.3);

          //  System.out.println("Left: " + (DriverStation.getInstance().getAnalogIn(2)/3.3));
          //  System.out.println("Right: " + (DriverStation.getInstance().getAnalogIn(1)/3.3));

    //    }

        return driveTrainCommand;
    }

}
