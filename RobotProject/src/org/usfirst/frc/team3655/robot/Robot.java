package org.usfirst.frc.team3655.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import org.usfirst.frc.team3655.robot.commands.*;
import org.usfirst.frc.team3655.robot.subsystems.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot 
{
	//Update EVERYtime you make a change to make sure you deployed the correct version!!
	public static final String VERSION = "0.0.04";
	
	//SubSystem Variables
	public static final Elevator elevator = new Elevator();
	public static final Gyroscope gyro = new Gyroscope();
	public static final MecanumDrive mecanumDrive = new MecanumDrive();
	public static final RotaryEncoder encoder = new RotaryEncoder();
	public static OI oi = new OI();
	
	//Commands
    Command teleOpDrive;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() 
    {
    	System.out.println("Robot Init v: " + VERSION);
		teleOpDrive = new TeleOpDrive();      
    }
	
	public void disabledPeriodic() 
	{
		Scheduler.getInstance().run();
	}

    public void autonomousInit() 
    {
        
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() 
    {
        Scheduler.getInstance().run();
    }

    public void teleopInit() 
    {
        //Starts TeleOP drive base
        if (teleOpDrive != null) { 
        	teleOpDrive.start();
        }
    }

    /**
     * This function is called when the disabled button is hit.
     * You can use it to reset subsystems before shutting down.
     */
    public void disabledInit()
    {
    	//Kills the drive base for teleOP
    	if (teleOpDrive != null) { 
        	teleOpDrive.cancel();
        }
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() 
    {
        Scheduler.getInstance().run();
        //Runs the button checking, probably not the best way but it'll work for now.
        oi.teleOpJoyStick();
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() 
    {
        LiveWindow.run();
    }
}
