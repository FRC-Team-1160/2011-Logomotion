/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.team1160.logomotion.autonomous;

import com.sun.squawk.io.BufferedReader;
import com.sun.squawk.microedition.io.FileConnection;
import com.sun.squawk.util.StringTokenizer;
import com.team1160.logomotion.controlCommands.DeploymentSystemCommand;
import com.team1160.logomotion.controlCommands.DriveTrainCommand;
import com.team1160.logomotion.controlCommands.arm.ArmCommand;
import com.team1160.logomotion.controlCommands.arm.ShoulderCommand;
import com.team1160.logomotion.controlCommands.arm.TurnTableCommand;
import com.team1160.logomotion.controlCommands.arm.WristCommand;
import com.team1160.logomotion.teleopManager.RobotCommand;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.microedition.io.Connector;

/**
 *
 * reads code during autonomous from a file with the following format:
 *
 * DT_RIGHT_SPEED_JAG,DT_LEFT_SPEED_JAG, TURNTABLE_SPEED_JAG , SHOULDER_RIGHT_SPEED_VIC, SHOULDER_LEFT_SPEED_VIC, WRIST_SPEED_VIC, DELTA_TIME;
 * @author sakekasi
 */
public class AutonomousFileReader {
    private FileConnection connection;
    private BufferedReader file;
    private boolean error;
    private static final String SEPARATOR=";";
    private StringTokenizer tokenizer;
    private boolean doneReading = false;

    public AutonomousFileReader(String path) throws IOException{
        
        try{
            this.connection = (FileConnection) Connector.open(path,Connector.READ);
            System.out.println(this.connection.exists());
//            if(!this.connection.exists()){
//                this.connection.create();
//            }
            this.file=new BufferedReader(
                    
                    new InputStreamReader(
                    this.connection.openInputStream()));
        }catch (IOException ioe){
            throw ioe;
        }
    }
    public boolean isDoneReading()
    {
        return this.doneReading;
    }
    public RobotCommand read() throws IOException{
        RobotCommand command = null;

        if (!this.doneReading)
        {
            try{
                String line = this.file.readLine();
//                System.out.println(line);
                if (line != null)
                    command = this.convertToCommand(line);
                else
                    this.doneReading = true;

            } catch (IOException ioe) {
                throw ioe;
            }
        }
        return command;
    }

    protected RobotCommand convertToCommand(String line){
        this.tokenizer = new StringTokenizer(line, AutonomousFileReader.SEPARATOR);
        RobotCommand command = new RobotCommand();

        String rightJag=tokenizer.nextToken(),leftJag=tokenizer.nextToken(),
            turntableJag=tokenizer.nextToken(), rightShoulder=tokenizer.nextToken(),
            leftShoulder=tokenizer.nextToken(), wristVictor=tokenizer.nextToken();

//        StringBuffer buffer = new StringBuffer();
//        buffer.append(rightJag);
//        buffer.append("\n");
//        buffer.append(leftJag);
//        buffer.append("\n");
//        buffer.append(turntableJag);
//        buffer.append("\n");
//        buffer.append(rightShoulder);
//        buffer.append("\n");
//        buffer.append(leftShoulder);
//        buffer.append("\n");
//        buffer.append(wristVictor);
        
        DriveTrainCommand dcmd = new DriveTrainCommand();
        dcmd.setDTSpeedRightJag(Double.parseDouble(rightJag));
        dcmd.setDTSpeedLeftJag(Double.parseDouble(leftJag));

        ArmCommand acmd = new ArmCommand();

        TurnTableCommand tcmd = new TurnTableCommand();
        tcmd.turntable_victor_pwm = Integer.parseInt(turntableJag);

        ShoulderCommand scmd = new ShoulderCommand();
        scmd.shoulder_right_victor_pwm = Integer.parseInt(rightShoulder);
        scmd.shoulder_left_victor_pwm = Integer.parseInt(leftShoulder);

        WristCommand wcmd = new WristCommand();
        wcmd.wrist_victor_pwm = Integer.parseInt(wristVictor);

        acmd.setTurnTableCommand(tcmd);
        acmd.setShoulderCommand(scmd);
        acmd.setWristCommand(wcmd);

        command.setDriveTrainCommand(dcmd);
        command.setArmCommand(acmd);
        command.setDeploymentSystemCommand(new DeploymentSystemCommand());

        return command;
    }

    public void close() throws IOException{
        if(this.file != null){
            this.file.close();
        }
    }
}
