package org.usfirst.frc.team3655.robot.commands;

import org.usfirst.frc.team3655.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

/**
 * class to allow for teleop driving with the mecanaum base with joystick input
 * @author G. Stewart
 * @version 1/23/2015
 */
public class TeleOpDrive extends Command 
{	
    public TeleOpDrive() 
    {
    	requires(Robot.elevator);
    	//setTimeout(); *Possibly length of TeleOp Period, or just called the end commands
    }

    // Called just before this Command runs the first time
    protected void initialize() 
    {
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() 
    {
    	Robot.mecanumDrive.setDrive(Robot.oi.getXBox1X(), Robot.oi.getXBox1Y(), Robot.oi.getXBox1Z(), Robot.gyro.getGyroAngle());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() 
    {
    	return isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() 
    {
    	
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() 
    {
    	end();
    }
}
