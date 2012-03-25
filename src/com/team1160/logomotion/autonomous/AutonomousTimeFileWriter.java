/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.team1160.logomotion.autonomous;

import com.sun.squawk.io.BufferedWriter;
import com.sun.squawk.microedition.io.FileConnection;
import com.team1160.logomotion.controlCommands.DriveTrainCommand;
import com.team1160.logomotion.controlCommands.arm.ArmCommand;
import com.team1160.logomotion.model.RobotState;
import com.team1160.logomotion.teleopManager.RobotCommand;
import java.io.IOException;
import java.io.OutputStreamWriter;
import javax.microedition.io.Connector;

/**
 * TODO: do this when solomon puts gyro and all other crap in.
 * 
 * writes code during teleop to a file with the following format:
 *
 * DT_RIGHT_SPEED_JAG,DT_LEFT_SPEED_JAG, TURNTABLE_SPEED_JAG , SHOULDER_RIGHT_SPEED_VIC, SHOULDER_LEFT_SPEED_VIC, WRIST_SPEED_VIC, DELTA_TIME;
 * @author sakekasi
 */
public class AutonomousTimeFileWriter {
    private FileConnection connection;
    private BufferedWriter file;
    private boolean error;
    private static final String SEPARATOR=";";




    public AutonomousTimeFileWriter(String path) throws IOException{
        try{
            this.connection = (FileConnection) Connector.open(path,Connector.WRITE);
//            if(!this.connection.exists()){
                this.connection.create();
 //           }
            this.file=new BufferedWriter(
                    new OutputStreamWriter(
                    this.connection.openOutputStream()));
        }catch (IOException ioe){
            throw ioe;
        }
    }

    public void write(double time, RobotCommand st) throws IOException{
        try{
            String cmdStr = convertToString(st);
            String line = time + SEPARATOR + cmdStr;
            file.write(line);
            file.newLine();
            file.flush();
        } catch (IOException ioe){
            throw ioe;
        }

    }

    protected String convertToString(RobotCommand cmd){
        DriveTrainCommand dcmd = cmd.getDriveTrainCommand();
        StringBuffer buffer = new StringBuffer();
        buffer.append(dcmd.getDTSpeedRightJag());
        buffer.append(AutonomousTimeFileWriter.SEPARATOR);

        buffer.append(dcmd.getDTSpeedLeftJag());
        buffer.append(AutonomousTimeFileWriter.SEPARATOR);

        ArmCommand acmd = cmd.getArmCommand();
        buffer.append(acmd.getTurnTableCommand().turntable_victor_pwm);
        buffer.append(AutonomousTimeFileWriter.SEPARATOR);

        buffer.append(acmd.getShoulderCommand().shoulder_right_victor_pwm);
        buffer.append(AutonomousTimeFileWriter.SEPARATOR);

        buffer.append(acmd.getShoulderCommand().shoulder_left_victor_pwm);
        buffer.append(AutonomousTimeFileWriter.SEPARATOR);

        buffer.append(acmd.getWristCommand().wrist_victor_pwm);
        buffer.append(AutonomousTimeFileWriter.SEPARATOR);

        return buffer.toString();
    }



    public void close() throws IOException{
        if(this.file != null){
            this.file.close();
        }
    }
    
}
