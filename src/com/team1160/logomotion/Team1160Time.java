/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.team1160.logomotion;


import com.team1160.logomotion.arm.Arm;
import com.team1160.logomotion.autoManager.AutonomousManager;
import com.team1160.logomotion.autonomous.AutonomousTimeFileManager;
import com.team1160.logomotion.camera.Camera;
import com.team1160.logomotion.drive.DriveTrain;
import com.team1160.logomotion.input.InputManager;
import com.team1160.logomotion.input.InputState;
import com.team1160.logomotion.minibot.DeploymentSystem;
import com.team1160.logomotion.model.Model;
import com.team1160.logomotion.model.RobotState;
import com.team1160.logomotion.outputManager.OutputManager;
import com.team1160.logomotion.pnuematics.Pnuematics;
import com.team1160.logomotion.teleopManager.RobotCommand;
import com.team1160.logomotion.teleopManager.TeleopManager;
import com.team1160.logomotion.util.CommandTimer;
import edu.wpi.first.wpilibj.IterativeRobot;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Team1160Time extends IterativeRobot {


    protected long lastPrintTime;

    protected InputManager inputManager;
    protected AutonomousManager autonomousManager;
    protected AutonomousTimeFileManager autonomousFileManager;
    protected Model model;
    protected TeleopManager  teleopManager;
    protected OutputManager outputManager;

    protected DriveTrain driveTrain;
    protected Pnuematics pnuematics;
    protected Arm arm;
    protected Camera camera;
    protected DeploymentSystem deploymentSystem;

    protected CommandTimer commandTimer;

 

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        System.out.println("In robotInit. Thank you for booting up and have a" +
                " nice day");
        initVariables();
        initManagersAndModel();
        initDriveTrain();
        initArm();
        initPnuematics();
        initDeploymentSystem();
        initCamera();

        initModel();
    }

    protected void initDeploymentSystem(){
        this.deploymentSystem = new DeploymentSystem();
        this.model.addModelNotifier(this.deploymentSystem);
        this.outputManager.setDeploymentSystem(this.deploymentSystem);
    }

    protected void initCamera(){
        this.camera = new Camera();
    }

    protected void initManagersAndModel(){
         this.inputManager = InputManager.getInstance();
         this.autonomousManager = AutonomousManager.getInstance();
         initAutoFileManager();
         this.model = Model.getInstance();
         this.teleopManager = TeleopManager.getInstance();
         this.outputManager = OutputManager.getInstance();

    }

    protected void initAutoFileManager(){
        try{
            this.autonomousFileManager = AutonomousTimeFileManager.getInstance();
        }catch(Exception e){
            System.err.println("Unable to init Autonomous File Manager. Message: " + e.getMessage());
            e.printStackTrace();
        }
    }

    protected void initModel(){
        this.model.updateModel();
    }

    protected void initDriveTrain(){
        this.driveTrain = new DriveTrain();
        this.model.addModelNotifier(this.driveTrain);
        this.outputManager.setDriveTrain(this.driveTrain);
    }

    protected void initArm(){
        this.arm = new Arm();
        this.model.addModelNotifier(this.arm);
        this.outputManager.setArm(this.arm);
    }

    protected void initPnuematics(){
        this.pnuematics = new Pnuematics();
        this.model.addModelNotifier(this.pnuematics);
        this.outputManager.setPnuematics(this.pnuematics);
    }

    protected void initVariables(){
        this.lastPrintTime = System.currentTimeMillis();
        this.commandTimer = new CommandTimer();
    }

    public void disabledInit(){
        System.out.println("In DiabledInit.");
        this.teleopManager.resetEncoders();
    }

    public void disabledPeriodic(){
        this.model.updateModel();
    }

    public void disabledContinuous(){
        
    }


    public void autonomousInit(){
        System.out.println("In autonomousInit.");
        this.teleopManager.resetEncoders();
        this.autonomousManager.reset();
        this.autonomousFileManager.reset();

        //stop and reset timer
        commandTimer.reset();

      
    }

    
    public void autonomousContinuous(){
        
    }
    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {

        //calling start while already srunning does nothing
        this.ensureTimerRunning();

        this.model.updateModel();


        /*RobotCommand nextRobotCommand = null;


        try{
            double time = commandTimer.get();

//            System.out.println("Done reading: " + this.autonomousFileManager.doneReading());
            if(!this.autonomousFileManager.isPastTime(time)){
//                System.out.println("Is not past time.");
                if(this.autonomousFileManager != null){
                    nextRobotCommand = this.autonomousFileManager.read(time);
//                    System.out.println("Autonomous File Manager is not Null.");
                }

                if(nextRobotCommand != null){
                    this.outputManager.outputToRobot(nextRobotCommand);
//                    System.out.println("Command sent to robot.");
                }

                if(System.currentTimeMillis() - this.lastPrintTime > 3000){
                    this.lastPrintTime = System.currentTimeMillis();
                    System.out.println("/--------------------------------------------");
//                    if(this.m_ds.getDigitalIn(4 ))
                        System.out.println(nextRobotCommand);
                }
            }else{
                System.out.println("Past time.");
            }
        }catch(Exception e){
            e.printStackTrace();
        }*/

        
        RobotState robotState;
        RobotCommand nextRobotCommand;

        try{
            this.model.updateModel();
            robotState = this.model.getCurrentRobotState();
            nextRobotCommand = this.autonomousManager.getAutoCommand(robotState);
            this.outputManager.outputToRobot(nextRobotCommand);

            /*if(System.currentTimeMillis() - this.lastPrintTime > 3000){
                this.lastPrintTime = System.currentTimeMillis();
                System.out.println("/--------------------------------------------");
                if(this.m_ds.getDigitalIn(6))
                    System.out.println(robotState);
                if(this.m_ds.getDigitalIn(4 ))*/
                    System.out.println(nextRobotCommand);
            //}
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void teleopInit(){
        System.out.println("In teleopInit.");
        this.teleopManager.resetEncoders();
        this.autonomousFileManager.reset();

        this.commandTimer.reset();
    }

    public void teleopContinuous(){
      
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
          InputState inputState;
          RobotState robotState;
          RobotCommand nextRobotCommand;

          ensureTimerRunning();

          try{
            this.model.updateModel();
            inputState = this.inputManager.getInputState();
            robotState = this.model.getCurrentRobotState();
            nextRobotCommand = this.teleopManager.getRobotCommand(inputState, robotState);



            this.autonomousFileManager.write(commandTimer.get(), nextRobotCommand);

            this.outputManager.outputToRobot(nextRobotCommand);

            if(System.currentTimeMillis() - this.lastPrintTime > 3000){
                this.lastPrintTime = System.currentTimeMillis();
                System.out.println("/--------------------------------------------");
                if(this.m_ds.getDigitalIn(8))
                    System.out.println(inputState);
                if(this.m_ds.getDigitalIn(6))
                    System.out.println(robotState);
                if(this.m_ds.getDigitalIn(4 ))
                    System.out.println(nextRobotCommand);
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        
    }

    protected void ensureTimerRunning()
    {
        if (!this.commandTimer.isRunning())
        {
            this.commandTimer.start();
        }
    }
}
