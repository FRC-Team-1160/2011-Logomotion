/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.team1160.logomotion.drive;

import com.team1160.logomotion.model.states.DriveTrainState;
import com.team1160.logomotion.api.Constants;
import com.team1160.logomotion.model.Model;
import com.team1160.logomotion.model.ModelNotifier;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Jaguar;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A class for the drive train ... pretty self explanatory.
 * @author sakekasi, cj
 */
public class DriveTrain implements ModelNotifier{
    protected Jaguar leftJaguar,rightJaguar;

    protected final Object LOCK = new Object();

    protected Encoder rightEncoder;
    protected Encoder leftEncoder;

    protected double lastRightEncoderValue;
    protected double lastLeftEncoderValue;    

    protected Timer encoderTimer;

    public DriveTrain(){
        initJaguars();
        //initEncoders();
    }

    protected void initJaguars(){
        //Init Jaguars
        this.leftJaguar = new Jaguar(Constants.C_DT_JAG_LEFT);
        this.rightJaguar = new Jaguar(Constants.C_DT_JAG_RIGHT);
    }

    protected void initEncoders(){
        //Init Encoders
        this.leftEncoder = new Encoder(Constants.C_DT_LEFT_ENC_A, Constants.C_DT_LEFT_ENC_B,
                Constants.DT_LEFT_ENC_REVERSED, Constants.DT_ENCODINGTYPE);
        this.rightEncoder = new Encoder(Constants.C_DT_RIGHT_ENC_A, Constants.C_DT_RIGHT_ENC_B,
                Constants.DT_RIGHT_ENC_REVERSED, Constants.DT_ENCODINGTYPE);

        //Config Encoder
        this.leftEncoder.setDistancePerPulse(Constants.DT_DISTANCE_PER_PULSE);
        this.rightEncoder.setDistancePerPulse(Constants.DT_DISTANCE_PER_PULSE);

        //Start Encoders
        this.leftEncoder.start();
        this.rightEncoder.start();

        initEncoderTask();
    }

    protected void initEncoderTask(){
        this.encoderTimer = new Timer();
        this.encoderTimer.schedule(new EncoderPollTask(), Constants.DT_ENCODER_POLL_DELAY_TIME);
    }

    public void resetEncoders(){
        this.leftEncoder.reset();
        this.rightEncoder.reset();
    }

    public void driveRaw(int left, int right){
        this.leftJaguar.setRaw(left);
        this.rightJaguar.setRaw(right);
    }

    public void driveSpeeds(double left, double right){
        leftJaguar.set(left);
        rightJaguar.set(right); 
    }

    public void updateModel(Model model){
        updateDriveTrain(model.getCurrentRobotState().getDriveTrainState());
    }

    protected void updateDriveTrain(DriveTrainState driveTrainState){
        updateDriveTrainEncoders(driveTrainState);
        updateDriveTrainSetSpeed(driveTrainState);
    }

    protected void updateDriveTrainSetSpeed(DriveTrainState driveTrainState){
        driveTrainState.last_set_left_speed = this.leftJaguar.getRaw();
        driveTrainState.last_set_right_speed = this.rightJaguar.getRaw();
    }

    protected void updateDriveTrainEncoders(DriveTrainState driveTrainState){
        //Use the distance traveled since the last loop interation
        synchronized(LOCK){
            driveTrainState.encoder_left = this.lastLeftEncoderValue;
            driveTrainState.encoder_right = this.lastRightEncoderValue;
        }
    }

    class EncoderPollTask extends TimerTask{
        public void run(){
            try{
                synchronized(LOCK){
                    lastLeftEncoderValue = leftEncoder.getDistance();
                    lastRightEncoderValue = rightEncoder.getDistance();
                }
                resetEncoders();
                encoderTimer.schedule(new EncoderPollTask(), Constants.DT_ENCODER_POLL_DELAY_TIME);
            } catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}
