package org.usfirst.frc.team3655.auton;

import org.usfirst.frc.team3655.robot.Robot;
import edu.wpi.first.wpilibj.Timer;

/**
 * Auton Class for single acuation auton modes
 * @author G. Stewart
 * @version 3/31/2015
 */

public abstract class AutonSingleAcuation extends AutonBase
{
	private boolean isTripped;
	
	public AutonSingleAcuation()
	{
		super();
		isTripped = false;
	}
	
	@Override
	public void iterate(int milisecondsToRun, double milisecondDelayTimeValue) 
	{
		counter = 0;
		isTripped = false;
		while(counter * milisecondDelayTimeValue < milisecondsToRun && Robot.isEnabled && Robot.isAutonomous)
		{
			if(!isTripped)
			{
				 acuationTask();
				isTripped = true;
			}
			Timer.delay(milisecondDelayTimeValue);
			counter++;
		}		
	}
	
	public abstract void acuationTask();
}
