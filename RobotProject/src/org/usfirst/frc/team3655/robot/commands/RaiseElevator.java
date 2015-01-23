package org.usfirst.frc.team3655.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3655.robot.Robot;

/**
 * Simple Command to raise elevator
 * @author G. Stewart
 * @version 1/22/2015
 */
public class RaiseElevator extends Command 
{
	private boolean tripped = false;
	
    public RaiseElevator() 
    {
    	requires(Robot.elevator);
    	setTimeout(1);
    }

    // Called just before this Command runs the first time
    protected void initialize() 
    {	
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() 
    {
    	if(!tripped) {
    		Robot.elevator.setMainArm(false);
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
