package org.usfirst.frc.team3655.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team3655.robot.RobotMap;
import edu.wpi.first.wpilibj.RobotDrive;

/**
 * Mecanum Drive base for robot
 * @author G. Stewart
 * @version 1/21/2015
 */

public class MecanumDrive extends Subsystem 
{
	RobotDrive mecanumBase = new RobotDrive(RobotMap.driveForwardLeftMotor, RobotMap.driveRearLeftMotor, RobotMap.driveForwardRightMotor, RobotMap.driveRearRightMotor);

    public void initDefaultCommand() 
    {
        
    }
    
    public void setDrive(double x, double y, double rotation, double gyroAngle)
    {
    	mecanumBase.mecanumDrive_Cartesian(x, y, rotation, gyroAngle);
    }
    
}

