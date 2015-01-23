package org.usfirst.frc.team3655.robot.commands;

import org.usfirst.frc.team3655.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Simple Command to push in kicker arms
 * @author G. Stewart
 * @version 1/23/2015
 */
public class PushKickers extends Command 
{
	private boolean tripped = false;
	
    public PushKickers() 
    {
    	requires(Robot.elevator);
    	setTimeout(.75);
    }

    // Called just before this Command runs the first time
    protected void initialize() 
    {
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() 
    {
    	if(!tripped) {
    		Robot.elevator.setKickerArm(true);
    		tripped = true;
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() 
    {
    	return isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() 
    {
    	tripped = false;
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted()
    {
    	end();
    }
}
