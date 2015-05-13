package org.usfirst.frc.team3655.auton;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.RobotDrive;

/**
 * Auton class for driving auton modes
 * @author G. Stewart
 * @version 5/15/2015
 */

public class AutonDrive extends AutonBase
{
	double x;
	double y;
	double rotation;
	RobotDrive drive;
	enum DriveType {Mecanum, Tank};
	DriveType driveType;
	
	/**
	 * Main constructor (Mecanum Drive)
	 * @param x what x value to pass into base
	 * @param y what y value to pass into base
	 * @param rotation what rotation value to pass into base
	 * @param drive what drive base to set the values to
	 */
	public AutonDrive(double x, double y, double rotation, RobotDrive drive)
	{
		super();
		this.x = x;
		this.y = y;
		this.rotation = rotation;
		this.drive = drive;
		driveType = DriveType.Mecanum;
	}
	
	/**
	 * Main constructor (Tank Drive)
	 * @param left the leftStick value to pass into base
	 * @param right the rightStick value to pass into base
	 * @param drive what drive base to set the values to
	 */
	public AutonDrive(double left, double right, RobotDrive drive)
	{
		super();
		this.x = left;
		this.y = right;
		this.drive = drive;
		driveType = DriveType.Tank;
	}
	
	@Override
	public void iterate(int milisecondsToRun, double milisecondDelayTimeValue) 
	{
		counter = 0;
		while(counter * milisecondDelayTimeValue * 1000 < milisecondsToRun && DriverStation.getInstance().isEnabled() && DriverStation.getInstance().isAutonomous())
		{
			switch(driveType)
			{
				case Mecanum:
					drive.mecanumDrive_Polar(Math.sqrt(x * x + y * y), (Math.toDegrees(Math.atan2(y, x)) - 90), rotation);
				break;
				case Tank:
					drive.tankDrive(x, y);
				break;
			}	
			Timer.delay(milisecondDelayTimeValue);
			counter++;
		}		
	}
}
