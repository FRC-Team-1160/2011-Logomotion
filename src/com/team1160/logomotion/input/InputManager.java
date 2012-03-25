/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.team1160.logomotion.input;

import com.team1160.logomotion.input.controllerStates.DriveJoysticksState;
import com.team1160.logomotion.api.Constants;
import com.team1160.logomotion.model.states.arm.DOFState;
import com.team1160.logomotion.input.controllerStates.ShoulderJoystickState;
import com.team1160.logomotion.input.controllerStates.WristJoystickState;
import com.team1160.logomotion.input.controllerStates.MinibotControlState;
import com.team1160.logomotion.input.controllerStates.TurnTableJoystickState;
import com.team1160.logomotion.arm.positionStates.DOFPosition;
import com.team1160.logomotion.input.controllerStates.ModelArmControlState;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;

/**
 *
 * @author CJ
 */
public class InputManager {

    //Instance Variables
    protected static InputManager _INSTANCE = new InputManager();

    protected DriverStation driverStation;

    private InputManager(){
        this.driverStation = DriverStation.getInstance();
    }

    public static InputManager getInstance(){
        if(_INSTANCE == null){
            _INSTANCE = new InputManager();
        }
        return _INSTANCE;
    }

    public InputState getInputState(){
        InputState currentInputState = new InputState();
        
        DriveJoysticksState driveJoystickState = getDriveJoystickState();
        ShoulderJoystickState dof1JoystickState = getDOF1JoystickState();
        WristJoystickState dof2JoystickState = getDOF2JoystickState();
        TurnTableJoystickState turnTableJoystickState = getTurnTableJoystickState();
        MinibotControlState minibotControlState = getMinibotControlState();
        ModelArmControlState modelArmControlState = getModelArmControlState();

        currentInputState.setDriveJoystickState(driveJoystickState);
        currentInputState.setDOF1JoystickState(dof1JoystickState);
        currentInputState.setDOF2JoystickState(dof2JoystickState);
        currentInputState.setTurnTableJoystickState(turnTableJoystickState);
        currentInputState.setMinibotControlState(minibotControlState);
        currentInputState.setModelArmState(modelArmControlState);

        return currentInputState;
    }

    protected ModelArmControlState getModelArmControlState(){
        ModelArmControlState modelArmControlState = new ModelArmControlState();

        modelArmControlState.enabled = this.driverStation.getDigitalIn(Constants.C_MODEL_ARM_ENABLED);
        modelArmControlState.shoulderPotValue = this.driverStation.getAnalogIn(Constants.C_MODEL_ARM_SHOULDER);
        modelArmControlState.wristPotValue = this.driverStation.getAnalogIn(Constants.C_MODEL_ARM_WRIST);

        return modelArmControlState;
    }

    protected MinibotControlState getMinibotControlState(){
        MinibotControlState controlstate = new MinibotControlState();

        int minibot_buttons = this.driverStation.getStickButtons(Constants.C_JOYSTICK_ARM_DOF1);

        boolean runActuator = (!((minibot_buttons & Constants.B_GAMEPAD_ARM_DOF1_ADJUST_SAFETY_COMMAND_MASK)
                == Constants.B_GAMEPAD_ARM_DOF1_ADJUST_SAFETY_COMMAND_MASK))
                && ((minibot_buttons & Constants.B_GAMEPAD_MINIBOT_ACTUATOR_TRIGGER_MASK)
                == Constants.B_GAMEPAD_MINIBOT_ACTUATOR_TRIGGER_MASK);

        boolean panelDeploy = (!((minibot_buttons & Constants.B_GAMEPAD_ARM_DOF1_ADJUST_SAFETY_COMMAND_MASK)
                == Constants.B_GAMEPAD_ARM_DOF1_ADJUST_SAFETY_COMMAND_MASK))
                && ((minibot_buttons & Constants.B_GAMEPAD_MINIBOT_PANEL_TRIGGER_MASK)
                == Constants.B_GAMEPAD_MINIBOT_PANEL_TRIGGER_MASK);


        controlstate.runActuator = runActuator;
        controlstate.triggerPanel = panelDeploy;

        return controlstate;

    }

    protected DriveJoysticksState getDriveJoystickState(){
        DriveJoysticksState driveJoystickState = new DriveJoysticksState();

        driveJoystickState.drive_joystick_left_y_axis = this.driverStation.getStickAxis(Constants.C_JOYSTICK_LEFT,
                Joystick.AxisType.kY.value + 1);
        driveJoystickState.drive_joystick_right_y_axis = this.driverStation.getStickAxis(Constants.C_JOYSTICK_RIGHT,
                Joystick.AxisType.kY.value + 1);

        return driveJoystickState;
    }

    protected ShoulderJoystickState getDOF1JoystickState(){
        ShoulderJoystickState dof1JoystickState = new ShoulderJoystickState();

        double joystickAxis = this.driverStation.getStickAxis(Constants.C_JOYSTICK_ARM_DOF1, Constants.B_GAMEPAD_ARM_DOF1_AXIS_NUM);
        dof1JoystickState.desiredAdjustSpeed = joystickAxis;

         if(joystickAxis < 0.09 && joystickAxis > -0.09){
            //DeadBand
            dof1JoystickState.dofState = DOFState.kStop;
        }else if(joystickAxis > 0){
            dof1JoystickState.dofState = DOFState.kBackward;
        }else if(joystickAxis < 0){
            dof1JoystickState.dofState = DOFState.kForward;
        }else{
            throw new IllegalStateException("In InputManager:getDOF1JoystickState, " +
                    "Fell through in desired speeds.");
        }
        //Safety for Adjusting
        if(!this.driverStation.isFMSAttached()){
            //Adjust the arm
            int dof1_stick_buttons = this.driverStation.getStickButtons(Constants.C_JOYSTICK_ARM_DOF1);
            boolean dof1_safety_masked_buttons =
                    (dof1_stick_buttons & Constants.B_GAMEPAD_ARM_DOF1_ADJUST_SAFETY_COMMAND_MASK ) ==
                    Constants.B_GAMEPAD_ARM_DOF1_ADJUST_SAFETY_COMMAND_MASK;
            if(dof1_safety_masked_buttons){
                //Look for other safety direction now
                if((dof1_stick_buttons & Constants.B_GAMEPAD_ARM_DOF1_ADJUST_COMMAND_BOTH_MASK) ==
                    Constants.B_GAMEPAD_ARM_DOF1_ADJUST_COMMAND_BOTH_MASK){
                    //Both are pressed, do nothing
                    dof1JoystickState.adjustLeft = false;
                    dof1JoystickState.adjustRight = false;
                }else if((dof1_stick_buttons & Constants.B_GAMEPAD_ARM_DOF1_ADJUST_RIGHT_MASK) ==
                        Constants.B_GAMEPAD_ARM_DOF1_ADJUST_RIGHT_MASK){
                    //Right is pressed
                    dof1JoystickState.adjustLeft = false;
                    dof1JoystickState.adjustRight = true;
                }else if((dof1_stick_buttons & Constants.B_GAMEPAD_ARM_DOF1_ADJUST_LEFT_MASK) ==
                        Constants.B_GAMEPAD_ARM_DOF1_ADJUST_LEFT_MASK){
                    //Left is pressed
                    dof1JoystickState.adjustLeft = true;
                    dof1JoystickState.adjustRight = false;
                }else{
                    //Neither is pressed
                    dof1JoystickState.adjustLeft = false;
                    dof1JoystickState.adjustRight = false;
                }
            }
        }

        //Reseting Gyro

        int button_mask = this.driverStation.getStickButtons(Constants.C_JOYSTICK_ARM_DOF1);
        if(!((button_mask & Constants.B_GAMEPAD_ARM_DOF1_ADJUST_SAFETY_COMMAND_MASK)
                == Constants.B_GAMEPAD_ARM_DOF1_ADJUST_SAFETY_COMMAND_MASK)
                && ((button_mask & Constants.B_GAMEPAD_ARM_DOF1_RESET_GYRO_MASK)
                == Constants.B_GAMEPAD_ARM_DOF1_RESET_GYRO_MASK)){
            dof1JoystickState.resetGyro = true;
        }else{
            dof1JoystickState.resetGyro = false;
        }


        //TODO Positions
        int position_button_mask = this.driverStation.getStickButtons(Constants.C_JOYSTICK_ARM_DOF1);
        boolean top = (position_button_mask & Constants.B_GAMEPAD_ARM_DOF1_GYRO_TOP_MASK) == Constants.B_GAMEPAD_ARM_DOF1_GYRO_TOP_MASK;
        boolean mid = (position_button_mask & Constants.B_GAMEPAD_ARM_DOF1_GYRO_MID_MASK) == Constants.B_GAMEPAD_ARM_DOF1_GYRO_MID_MASK;
        boolean bot = (position_button_mask & Constants.B_GAMEPAD_ARM_DOF1_GYRO_BOT_MASK) == Constants.B_GAMEPAD_ARM_DOF1_GYRO_BOT_MASK;
        boolean midMod = (position_button_mask & Constants.B_GAMEPAD_ARM_DOF1_GYRO_MID_MOD_MASK) == Constants.B_GAMEPAD_ARM_DOF1_GYRO_MID_MOD_MASK;
        if((position_button_mask & Constants.B_GAMEPAD_ARM_DOF1_GYRO_DISABLE_MASK)
                == Constants.B_GAMEPAD_ARM_DOF1_GYRO_DISABLE_MASK){
                dof1JoystickState.dofPosition = DOFPosition.kUserMove;
        }else if(midMod){
            if(!top && !mid && bot){
                //Score Mid Bot
                dof1JoystickState.dofPosition = DOFPosition.kScoreLowMid;
            }else if(!top && mid && !bot){
                //Score Mid Mid
                dof1JoystickState.dofPosition = DOFPosition.kScoreMidMid;
            }else if(top && !mid && !bot){
                //Score Mid Top
                dof1JoystickState.dofPosition = DOFPosition.kScoreTopMid;
            }else{
                dof1JoystickState.dofPosition = DOFPosition.kUserMove;
            }
        }else{
            if(!top && !mid && bot){
                //Score Side Bot
                dof1JoystickState.dofPosition = DOFPosition.kScoreLowSide;
            }else if(!top && mid && !bot){
                //Score Side Mid
                dof1JoystickState.dofPosition = DOFPosition.kScoreMidSide;
            }else if(top && !mid && !bot){
                //Score Side Top
                dof1JoystickState.dofPosition = DOFPosition.kScoreTopSide;
            }else{
                dof1JoystickState.dofPosition = DOFPosition.kUserMove;
            }
        }

        return dof1JoystickState;
    }

    protected WristJoystickState getDOF2JoystickState(){
        WristJoystickState dof2JoystickState = new WristJoystickState();

        double joystickAxis = this.driverStation.getStickAxis(Constants.C_JOYSTICK_ARM_DOF2,
                Constants.B_GAMEPAD_ARM_DOF2_AXIS_NUM);
        dof2JoystickState.desiredSpeed = Math.abs(joystickAxis);

        if(joystickAxis < 0.09 && joystickAxis > -0.09){
            dof2JoystickState.dofState = DOFState.kStop;
        }else if(joystickAxis < 0){
            dof2JoystickState.dofState = DOFState.kForward;
        }else if(joystickAxis > 0){
            dof2JoystickState.dofState = DOFState.kBackward;
        }else{
            throw new IllegalStateException("In getDOF2JoystickState:InputManager, " +
                    "Fell through on the joystick axis states.");
        }

        int position_button_mask = this.driverStation.getStickButtons(Constants.C_JOYSTICK_ARM_DOF2);
        if(!((position_button_mask & Constants.B_GAMEPAD_ARM_DOF2_POT_USE_MASK)
                == Constants.B_GAMEPAD_ARM_DOF2_POT_USE_MASK)){
            boolean top = (position_button_mask & Constants.B_GAMEPAD_ARM_DOF1_GYRO_TOP_MASK) == Constants.B_GAMEPAD_ARM_DOF1_GYRO_TOP_MASK;
            boolean mid = (position_button_mask & Constants.B_GAMEPAD_ARM_DOF1_GYRO_MID_MASK) == Constants.B_GAMEPAD_ARM_DOF1_GYRO_MID_MASK;
            boolean bot = (position_button_mask & Constants.B_GAMEPAD_ARM_DOF1_GYRO_BOT_MASK) == Constants.B_GAMEPAD_ARM_DOF1_GYRO_BOT_MASK;
            boolean midMod = (position_button_mask & Constants.B_GAMEPAD_ARM_DOF1_GYRO_MID_MOD_MASK) == Constants.B_GAMEPAD_ARM_DOF1_GYRO_MID_MOD_MASK;
            if(midMod){
                if(!top && !mid && bot){
                    //Score Mid Bot
                    dof2JoystickState.dofPosition = DOFPosition.kScoreLowMid;
                }else if(!top && mid && !bot){
                    //Score Mid Mid
                    dof2JoystickState.dofPosition = DOFPosition.kScoreMidMid;
                }else if(top && !mid && !bot){
                    //Score Mid Top
                    dof2JoystickState.dofPosition = DOFPosition.kScoreTopMid;
                }else{
                    dof2JoystickState.dofPosition = DOFPosition.kUserMove;
                }
            }else{
                if(!top && !mid && bot){
                    //Score Side Bot
                    dof2JoystickState.dofPosition = DOFPosition.kScoreLowSide;
                }else if(!top && mid && !bot){
                    //Score Side Mid
                    dof2JoystickState.dofPosition = DOFPosition.kScoreMidSide;
                }else if(top && !mid && !bot){
                    //Score Side Top
                    dof2JoystickState.dofPosition = DOFPosition.kScoreTopSide;
                }else{
                    dof2JoystickState.dofPosition = DOFPosition.kUserMove;
                }
            }
        }else{
            dof2JoystickState.dofPosition = DOFPosition.kUserMove;
        }

        return dof2JoystickState;
    }
    
    protected TurnTableJoystickState getTurnTableJoystickState(){

       int turntableButtons = this.driverStation.getStickButtons(Constants.C_JOYSTICK_TURNTABLE);

       boolean right = (((turntableButtons & Constants.B_GAMEPAD_TURNTABLE_RIGHT_MASK)
                == Constants.B_GAMEPAD_TURNTABLE_RIGHT_MASK) &&
                !((turntableButtons & Constants.B_GAMEPAD_TURNTABLE_SAFETY_ON_MASK)
                == Constants.B_GAMEPAD_TURNTABLE_SAFETY_ON_MASK));

       boolean left = (((turntableButtons & Constants.B_GAMEPAD_TURNTABLE_LEFT_MASK)
               == Constants.B_GAMEPAD_TURNTABLE_LEFT_MASK) &&
                !((turntableButtons & Constants.B_GAMEPAD_TURNTABLE_SAFETY_ON_MASK)
                == Constants.B_GAMEPAD_TURNTABLE_SAFETY_ON_MASK));

       if(this.driverStation.getDigitalIn(Constants.C_MODEL_ARM_ENABLED)){

           right = !(this.driverStation.getDigitalIn(Constants.C_MODEL_ARM_TURN_RIGHT));
           left = !(this.driverStation.getDigitalIn(Constants.C_MODEL_ARM_TURN_LEFT));

       }

       if(left && right){
           return TurnTableJoystickState.kStop;
       }else if(left){
           return TurnTableJoystickState.kCounterClockWise;
       }else if(right){
           return TurnTableJoystickState.kClockWise;
       }else{
           return TurnTableJoystickState.kStop;
       }

    }
}
