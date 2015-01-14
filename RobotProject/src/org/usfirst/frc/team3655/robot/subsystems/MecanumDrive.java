package org.usfirst.frc.team3655.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team3655.robot.RobotMap;
import edu.wpi.first.wpilibj.RobotDrive;

/**
 *
 */
public class MecanumDrive extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	RobotDrive mecanumBase = new RobotDrive(RobotMap.forwardLeftMotor, RobotMap.rearLeftMotor, RobotMap.forwardRightMotor, RobotMap.rearRightMotor);

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

