/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.team1160.logomotion.autonomous;

import com.team1160.logomotion.api.Constants;
import com.team1160.logomotion.teleopManager.RobotCommand;
import edu.wpi.first.wpilibj.DriverStation;
import java.io.IOException;

/**
 *
 * @author sakekasi,cj
 */
public class AutonomousTimeFileManager {

    private static AutonomousTimeFileManager _INSTANCE;

    protected AutonomousTimeFileWriter writer = null;
    protected AutonomousTimeFileReader reader = null;
    boolean write;
    boolean read;
    String path;

    private AutonomousTimeFileManager() throws IOException{
        this.reset();
        try{
//            if (this.read)
            System.out.println(this.path);
                this.reader = new AutonomousTimeFileReader(this.path);

        }catch(Exception e){
            this.reader = null;
            this.read = false;
            System.err.println("Unable to init Autonomous File Reader. Message: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static AutonomousTimeFileManager getInstance() throws IOException{
        if(_INSTANCE == null){
            _INSTANCE = new AutonomousTimeFileManager();
        }
        return _INSTANCE;
    }

    public RobotCommand read(double time) throws IOException {
        if(this.read){
            if (!isPastTime(time))
                return reader.getCommand(time);
            else
                return null;
        }
        return null;
//        throw new IllegalStateException("READING NOT ENABLED");
    }

    public boolean isPastTime(double currentTime){
        return this.reader.isPastTime(currentTime);
    }

    public boolean doneReading(){
        boolean output = true;
        if(this.read){
            output = this.reader.isDoneReading();
        }
        return output;
    }

    public void write(double time, RobotCommand st) throws IOException {
        if(this.write){
            writer.write(time, st);
        }
//        throw new IllegalStateException("WRITING NOT ENABLED");
    }

    public void reset(){
        this.read = DriverStation.getInstance().getDigitalIn(2);
        this.write = DriverStation.getInstance().getDigitalIn(4);

        int one_file = DriverStation.getInstance().getDigitalIn(6)?1:0;
        int two_file =  0;//DriverStation.getInstance().getDigitalIn(6)?2:0;
        int bin_file = two_file + one_file;

        System.out.println("Bin File number is: " + bin_file);

        switch(bin_file){
            case 0:
                this.path=Constants.CENTER_LEFT_FILE;
                break;
            case 1:
                this.path=Constants.RIGHT_FILE;
                break;
            case 2:
                this.path=Constants.LEFT_FILE;
                break;
            case 3:
                this.path=Constants.CENTER_RIGHT_FILE;
                break;
            default:
                throw new RuntimeException("SHOULD NOT RUN");
        }

        try{
            if (this.write)
                this.writer = new AutonomousTimeFileWriter(this.path);
        }catch(Exception e){
            this.writer = null;
            this.write = false;
            System.err.println("Unable to init Autonomous File Writer. Message: " + e.getMessage());
            e.printStackTrace();
        }

    }


}