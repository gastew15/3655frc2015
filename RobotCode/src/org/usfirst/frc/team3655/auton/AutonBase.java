package org.usfirst.frc.team3655.auton;

/**
 * Base class for auton Modes
 * @author G. Stewart
 * @version 3/31/2015
 */

public abstract class AutonBase 
{
	int counter;
	boolean isEnabled;
	boolean isAutonomous;
	
	public AutonBase()
	{
		reset();
	}
	
	public abstract void iterate(int milisecondsToRun, double milisecondDelayTimeValue);
	
	public void reset()
	{
		counter = 0;
	}	
}
