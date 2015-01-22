package org.usfirst.frc.team3655.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team3655.robot.RobotMap;
import edu.wpi.first.wpilibj.Gyro;

/**
 * Gyro system for robot
 * @author G. Stewart
 * @version 1/21/2015
 */

public class Gyroscope extends Subsystem 
{
	Gyro gyroscope = new Gyro(RobotMap.gyroInput);

    public void initDefaultCommand() 
    {
        gyroscope.initGyro();
    }
    
    public double getGyroAngle()
    {
    	return gyroscope.getAngle();
    }
}

