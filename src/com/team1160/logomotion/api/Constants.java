/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.team1160.logomotion.api;

import edu.wpi.first.wpilibj.CounterBase.EncodingType;

/**
 *  All constants for the robot can be placed here.  I have plans to make it
 * so the robot reads an xml and then executes depending on the xml. this means
 * that future programmers only have to modify an xml file and the code here
 * takes care of the rest.
 * @author CJ, saki
 */
public interface Constants {
    //--------------------------------------------------------------------------
    //Autonomous file Constants

    String LEFT_FILE = "file:///left";
    String RIGHT_FILE = "file:///right";
    String CENTER_LEFT_FILE = "file:///left_center";
    String CENTER_RIGHT_FILE = "file:///right_center";


    //-------------------------------------------------------------------------
    //DriverStation Button and Channel Constants
    int C_JOYSTICK_RIGHT = 1;
    int C_JOYSTICK_LEFT = 2;
    int C_JOYSTICK_ARM_DOF1 = 3;
    int C_JOYSTICK_ARM_DOF2 = 3;
    int C_JOYSTICK_TURNTABLE = 3;

    int C_AUTONOMOUS_WRITE = 2;
    int C_AUTONOMOUS_READ = 4;
    int C_AUTONOMOUS_SIDE_MID = 1;
    int C_AUTONOMOUS_MID_MID = 2;
    int C_AUTONOMOUS_SIDE_TOP = 3;
    int C_AUTONOMOUS_MID_TOP = 4;

    //-------------------------------------------------------------------------
    //PID constants
    double Kp = 1;
    double Ki = 1;
    double Kd = 1;
    double MARGIN=.01;

    //-------------------------------------------------------------------------
    //Model Arm Constants
    int C_MODEL_ARM_ENABLED = 8;
    int C_MODEL_ARM_SHOULDER = 1;
    int C_MODEL_ARM_WRIST = 2;

    int C_MODEL_ARM_TURN_LEFT = 1;
    int C_MODEL_ARM_TURN_RIGHT = 3;

    double K_MODEL_ARM_DELTA_WRIST = 0.05;
    double K_MODEL_ARM_DELTA_SHOULDER = 0.1;

    double K_MODEL_ARM_MINI_SHOULDER_MAX = 1.0;
    double K_MODEL_ARM_MINI_SHOULDER_MIN = 3.1;
    double K_MODEL_ARM_MINI_WRIST_MAX = 3.3;
    double K_MODEL_ARM_MINI_WRIST_MIN = 0.0;

    double K_MODEL_ARM_REAL_SHOULDER_MAX = -152.6306133475506;
    double K_MODEL_ARM_REAL_SHOULDER_MIN = 0.0;
    double K_MODEL_ARM_REAL_WRIST_MAX = 0.0;
    double K_MODEL_ARM_REAL_WRIST_MIN = 5.071200537;


    //---------------------------------------------------------------------
    //DOF1 JOYSTICK CONSTANTS
    //1 2 3 4 5   6
    //1 2 4 8 64 128 256 512

    int B_GAMEPAD_ARM_DOF1_AXIS_NUM = 2;
    int B_GAMEPAD_ARM_DOF2_AXIS_NUM =4;
    
    int B_GAMEPAD_ARM_DOF1_ADJUST_SAFETY_1 = 5;
    int B_GAMEPAD_ARM_DOF1_ADJUST_SAFETY_2 = 6;
    int B_GAMEPAD_ARM_DOF1_ADJUST_SAFETY_3 = 7;
    int B_GAMEPAD_ARM_DOF1_ADJUST_SAFETY_4 = 8;
    int B_GAMEPAD_ARM_DOF1_ADJUST_RIGHT = 10;
    int B_GAMEPAD_ARM_DOF1_ADJUST_LEFT = 9;

    int B_GAMEPAD_ARM_DOF1_GYRO_BOT = 2;
    int B_GAMEPAD_ARM_DOF1_GYRO_MID = 3;
    int B_GAMEPAD_ARM_DOF1_GYRO_TOP = 4;
    int B_GAMEPAD_ARM_DOF1_GYRO_MID_MODIFY = 6;

    int B_GAMEPAD_ARM_DOF2_POT_USE = 9;
    int B_GAMEPAD_ARM_DOF1_GYRO_DISABLE = 10;

    int B_GAMEPAD_ARM_DOF1_RESET_GYRO_1 = 9;
    int B_GAMEPAD_ARM_DOF1_RESET_GYRO_2 = 10;

    int B_GAMEPAD_MINIBOT_ACTUATOR_TRIGGER = 6;
    int B_GAMEPAD_MINIBOT_DEPLOYMENT_TRIGGER = 5;

    int B_GAMEPAD_TURNTALBE_RIGHT = 8;
    int B_GAMEPAD_TURNTABLE_LEFT = 7;
    int B_GAMEPAD_TURNTABLE_SAFETY_ON_1 = 5;
    int B_GAMEPAD_TURNTABLE_SAFETY_ON_2 = 6;

    //These masks are in binary, literalty, 2^(button num - 1)
    int B_GAMEPAD_ARM_DOF1_ADJUST_SAFETY_MASK_1 = 16;
    int B_GAMEPAD_ARM_DOF1_ADJUST_SAFETY_MASK_2 = 32;
    int B_GAMEPAD_ARM_DOF1_ADJUST_SAFETY_MASK_3 = 64;
    int B_GAMEPAD_ARM_DOF1_ADJUST_SAFETY_MASK_4 = 128;
    int B_GAMEPAD_ARM_DOF1_ADJUST_RIGHT_MASK = 512;
    int B_GAMEPAD_ARM_DOF1_ADJUST_LEFT_MASK = 256;

    int B_GAMEPAD_ARM_DOF1_GYRO_BOT_MASK = 2;
    int B_GAMEPAD_ARM_DOF1_GYRO_MID_MASK = 4;
    int B_GAMEPAD_ARM_DOF1_GYRO_TOP_MASK = 8;
    int B_GAMEPAD_ARM_DOF1_GYRO_MID_MOD_MASK = 32;
    int B_GAMEPAD_ARM_DOF1_GYRO_COMPLETE_MASK = B_GAMEPAD_ARM_DOF1_GYRO_BOT_MASK +
            B_GAMEPAD_ARM_DOF1_GYRO_MID_MASK + B_GAMEPAD_ARM_DOF1_GYRO_TOP_MASK;

    int B_GAMEPAD_ARM_DOF2_POT_USE_MASK = 256;
    int B_GAMEPAD_ARM_DOF1_GYRO_DISABLE_MASK = 512;

    int B_GAMEPAD_ARM_DOF1_RESET_GYRO_MASK_1 = 256;
    int B_GAMEPAD_ARM_DOF1_RESET_GYRO_MASK_2 = 512;
    int B_GAMEPAD_ARM_DOF1_RESET_GYRO_MASK = B_GAMEPAD_ARM_DOF1_RESET_GYRO_MASK_1 +
            B_GAMEPAD_ARM_DOF1_RESET_GYRO_MASK_2;

    int B_GAMEPAD_TURNTABLE_RIGHT_MASK = 128;
    int B_GAMEPAD_TURNTABLE_LEFT_MASK = 64;
    int B_GAMEPAD_TURNTABLE_SAFETY_ON_MASK_1 = 16;
    int B_GAMEPAD_TURNTABLE_SAFETY_ON_MASK_2 = 32;
    int B_GAMEPAD_TURNTABLE_SAFETY_ON_MASK = B_GAMEPAD_TURNTABLE_SAFETY_ON_MASK_1 +
            B_GAMEPAD_TURNTABLE_SAFETY_ON_MASK_2;
    
    int B_GAMEPAD_MINIBOT_ACTUATOR_TRIGGER_MASK = 32;
    int B_GAMEPAD_MINIBOT_PANEL_TRIGGER_MASK = 16;

    //Masks for the safety
    int B_GAMEPAD_ARM_DOF1_ADJUST_SAFETY_COMMAND_MASK = B_GAMEPAD_ARM_DOF1_ADJUST_SAFETY_MASK_1
            + B_GAMEPAD_ARM_DOF1_ADJUST_SAFETY_MASK_2 + B_GAMEPAD_ARM_DOF1_ADJUST_SAFETY_MASK_3
            + B_GAMEPAD_ARM_DOF1_ADJUST_SAFETY_MASK_4;
    int B_GAMEPAD_ARM_DOF1_ADJUST_COMMAND_RIGHT_MASK = B_GAMEPAD_ARM_DOF1_ADJUST_SAFETY_COMMAND_MASK
            + B_GAMEPAD_ARM_DOF1_ADJUST_RIGHT_MASK;
    int B_GAMEPAD_ARM_DOF1_ADJUST_COMMAND_LEFT_MASK = B_GAMEPAD_ARM_DOF1_ADJUST_SAFETY_COMMAND_MASK
            + B_GAMEPAD_ARM_DOF1_ADJUST_LEFT_MASK;
    int B_GAMEPAD_ARM_DOF1_ADJUST_COMMAND_BOTH_MASK = B_GAMEPAD_ARM_DOF1_ADJUST_COMMAND_LEFT_MASK |
            B_GAMEPAD_ARM_DOF1_ADJUST_COMMAND_RIGHT_MASK;

    //-------------------------------------------------------------------------
    //DriveTrain Channel Constants

    //Jaguars
    int C_DT_JAG_LEFT = 2;
    int C_DT_JAG_RIGHT = 1;

    //Encoders
    EncodingType DT_ENCODINGTYPE = EncodingType.k4X;
    double DT_DISTANCE_PER_PULSE = 0.1628;
    long DT_ENCODER_POLL_DELAY_TIME = 200;
    double DT_ENCODER_MAX_RIGHT = 165;
    double DT_ENCODER_MAX_LEFT = 185;

    //Right Drive
    int C_DT_RIGHT_ENC_A = 2;
    int C_DT_RIGHT_ENC_B = 3;
    boolean DT_RIGHT_ENC_REVERSED = true;

    //Left Drive
    int C_DT_LEFT_ENC_A = 4;
    int C_DT_LEFT_ENC_B = 5;
    boolean DT_LEFT_ENC_REVERSED = false;

    //-------------------------------------------------------------------------
    //Arm Channel Constants

    //Victors

    //Shoulder
    int C_ARM_SHOULDER_VIC_RIGHT = 3;
    int C_ARM_SHOULDER_VIC_LEFT = 4;
    double K_ARM_SHOULDER_UP_SPEED = 0.75;
    double K_ARM_SHOULDER_DOWN_SPEED = 0.311818182;
    int C_ARM_SHOULER_GYRO = 1;

    //Wrist
    int C_ARM_WRIST_VIC = 5;
    int C_ARM_WRIST_POT = 2;

    //Turntable
    int C_ARM_TURNTABLE_JAG = 6;
    int C_ARM_TURNTABLE_POT = 3;

    //Shoulder Gyro Constants
    double K_ARM_SHOULDER_GYRO_DEADBAND = 10.;
    double K_ARM_SHOULDER_GYRO_STOW = -1;
    double K_ARM_SHOULDER_GYRO_SIDE_BOT = -38.98097104910975;
    double K_ARM_SHOULDER_GYRO_SIDE_MID = -83.00512244301198;
    double K_ARM_SHOULDER_GYRO_SIDE_TOP = -137.68741450461593;
    double K_ARM_SHOULDER_GYRO_MID_BOT = -48.599484422167315;
    double K_ARM_SHOULDER_GYRO_MID_MID = -89.65942105141886;
    double K_ARM_SHOULDER_GYRO_MID_TOP = -1;
    double K_ARM_SHOULDER_GYRO_RELOAD = -42.35258627121463;
    double K_ARM_SHOULDER_GYRO_RELOAD_TOP = -64.58380676912914;

    //Wrist Pot Constants
    double K_ARM_WRIST_POT_DEADBAND = 0.1;
    double K_ARM_WRIST_SPEED = 0.2;
    double K_ARM_WRIST_POT_SIDE_BOT = 2.350083785;
    double K_ARM_WRIST_POT_SIDE_MID = 2.813033705;
    double K_ARM_WRIST_POT_SIDE_TOP = 4.062998489;
    double K_ARM_WRIST_POT_MID_BOT = 2.288357129;
    double K_ARM_WRIST_POT_MID_MID = 2.8387531449999996;
    double K_ARM_WRIST_POT_MID_TOP = -1;
    double K_ARM_WRIST_POT_STOW = -1;
    double K_ARM_WRIST_POT_RELOAD = 4.263610121;
    double K_ARM_WRIST_POT_RELOAD_TOP = 3.8366674169999997;

    //-------------------------------------------------------------------------
    //Pnuematics Constants

    //DIO Pressure Switch
    int C_PNEU_SWITCH = 1;

    //Relay Spike Connected to compressor
    int C_PNEU_COMPRESS = 1;

    //-------------------------------------------------------------------------
    //Minibot Deployment System Constants

    //Singular Piston Constants
    int C_ACTUATOR_SOLENOID_FRONT = 1;
    int C_ACTUATOR_SOLENOID_BACK = 2;

    //Servo
    int C_SERVO = 7;
    double C_SERVO_REST = 0.0;
    double C_SERVO_DEPLOY = 1.0;

    //--------------------------------------------------------------------------
    //Dashboard update constants

    int DASHBOARD_UPDATE_TIME = 200;

    
}
