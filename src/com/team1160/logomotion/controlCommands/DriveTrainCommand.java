/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.team1160.logomotion.controlCommands;

/**
 *
 * @author CJ
 */
public class DriveTrainCommand {
      /**
     * The speed from -1.0 to 1.0 for the right jaguar.
     */
    public double drivetrain_speed_right_jag;
    public double getDTSpeedRightJag(){return this.drivetrain_speed_right_jag;}
    public void setDTSpeedRightJag(double speed){this.drivetrain_speed_right_jag=speed;};

    /**
     * The speed from -1.0 to 1.0 for the left jaguar.
     */
    public double drivetrain_speed_left_jag;
    public double getDTSpeedLeftJag(){return this.drivetrain_speed_left_jag;}
    public void setDTSpeedLeftJag(double drivetrain_speed_left_jag)
    {this.drivetrain_speed_left_jag = drivetrain_speed_left_jag;}

    public String toString(){
        String output = "/-----\n";
        output += "DriveTrain Command \n";
        output += "Right Jag Speed: " + this.drivetrain_speed_right_jag + '\n';
        output += "Left Jag Speed: " + this.drivetrain_speed_left_jag + '\n';
        return output;
    }
}
