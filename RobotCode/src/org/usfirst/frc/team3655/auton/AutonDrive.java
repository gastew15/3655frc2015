package org.usfirst.frc.team3655.auton;

import org.usfirst.frc.team3655.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.RobotDrive;

/**
 * Auton class for driving auton modes
 * @author G. Stewart
 * @version 3/31/2015
 */

public class AutonDrive extends AutonBase
{
	double x;
	double y;
	double rotation;
	RobotDrive drive;
	
	public AutonDrive(double x, double y, double rotation, RobotDrive drive)
	{
		super();
		this.x = x;
		this.y = y;
		this.rotation = rotation;
		this.drive = drive;
	}
	
	@Override
	public void iterate(int milisecondsToRun, double milisecondDelayTimeValue) 
	{
		counter = 0;
		while(counter * milisecondDelayTimeValue < milisecondsToRun && Robot.isEnabled && Robot.isAutonomous)
		{
			drive.mecanumDrive_Polar(y, x, rotation);
			Timer.delay(milisecondDelayTimeValue);
			counter++;
		}		
	}
}
