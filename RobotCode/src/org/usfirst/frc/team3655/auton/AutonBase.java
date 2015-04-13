package org.usfirst.frc.team3655.auton;

/**
 * Base class for auton Modes
 * @author G. Stewart
 * @version 3/31/2015
 */

public abstract class AutonBase 
{
	int counter;
	
	public AutonBase()
	{
		reset();
	}
	
	/**
	 * Runs the main loop for the system
	 * @param milisecondsToRun how long you want the loop to run before it stops in miliseconds
	 * @param milisecondDelayTimeValue how long the delay value for the loop is in miliseconds (EX: 0.005 loops 200 times a second)
	 */
	public abstract void iterate(int milisecondsToRun, double milisecondDelayTimeValue);
	
	/**
	 * Resets the counter for the run loop to 0, probably not needed ever
	 */
	public void reset()
	{
		counter = 0;
	}	
}
