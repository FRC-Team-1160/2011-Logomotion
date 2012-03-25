/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.team1160.logomotion.drive.pid;

import com.team1160.logomotion.api.Constants;
import edu.wpi.first.wpilibj.DriverStation;

/**
 * Computes the current point in a PID curve based on the error.
 * Constants to adjust the "strength" of each element are in @see com.team1160.logomotion.api.Constants Constants
 *
 * PID has three terms.
 *
 * Proportional, which is directly proportional to the error.
 * Integral, which is the area under the error curve.
 * Derivative, which is the current slope of the error curve.
 *
 *
 * error is a signed number indicating the difference between the current value and the value that needs to be achieved.
 * if the current point is greater than the set point, then the error is negative.
 * if the current point is less than the set point, then the error is positive.
 * @author sakekasi, cj
 */
public class PidComputation {

    double setpoint,
           currentpoint,
           integral,
           derivative,
           dt,
           lasttime,
           lasterror;

    double lastprint;

    protected DriverStation m_ds = DriverStation.getInstance();

    /**
     * default constructor. Initializes default values through reset.
     */
    public PidComputation() {
        this.lastprint = System.currentTimeMillis();
        reset();
    }

    /**
     * computes the required delta from the current setting to accelerate towards the setpoint.
     * @param currentpoint current value
     * @param setpoint value that you want to achieve
     * @return the delta that must be added to the current point.
     */
    public double getPidDelta(double currentpoint, double setpoint){
        if(!isWithin(setpoint, this.setpoint, Constants.MARGIN)){
            reset();
            this.setpoint=setpoint;
        }
        this.currentpoint=currentpoint;

        double error = this.setpoint-this.currentpoint;
        double currenttime = System.currentTimeMillis();

        double Kp = this.m_ds.getAnalogIn(1)/3.3;
        double Ki = this.m_ds.getAnalogIn(2)/3.3;
        double Kd = this.m_ds.getAnalogIn(3)/3.3;

        this.dt = currenttime-lasttime;
        this.integral += error*dt;
        this.derivative = (error-lasterror)/dt;
//        double delta = (Constants.Kp*error)+(Constants.Ki*this.integral)+(Constants.Kd*this.derivative);
        double delta = Kp*error;// + Ki*this.integral+Kd*this.derivative;

        //2.016/3.3 = Kp
        //0.370/3.3 = Ki
        //0.029/3.3 = Kd


        this.lasterror = error;
        this.lasttime = currenttime;

        return delta;

    }

    /**
     * gets what the current point should be from the current point and set point.
     * @param currentpoint current value
     * @param setpoint value that you want to achieve
     * @return the delta that must be added to the current point.
     */
    public double pidNext(double currentpoint, double setpoint){
        return currentpoint+getPidDelta(currentpoint, setpoint);
    }

    /**
     * resets instance variables that must be reset upon a setpoint change.
     */
    public void reset() {
        this.integral=0;
        this.derivative=0;
        this.lasterror=0;
        this.lasttime = System.currentTimeMillis();
    }

    /**
     * checks whether or not a test number is within a certain range of another number.
     * @param test the number to be tested
     * @param number the number to test test relative to
     * @param margin the range of error allowed
     * @return whether or not test is within margin of number.
     */
    protected boolean isWithin(double test, double number, double margin){
        double difference = Math.abs(test-number);
        return difference <= margin;
    }

    

}
