package org.usfirst.frc.team3655.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team3655.robot.RobotMap;
import edu.wpi.first.wpilibj.Victor;

/**
 *
 */
public class Elevator extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	Victor motor1 = new Victor(RobotMap.armMotor1);
	Victor motor2 = new Victor(RobotMap.armMotor2);

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void up() {
    	motor1.set(1);
    	motor2.set(1);
    }
    
    public void down() {
    	motor1.set(-1);
    	motor2.set(-1);
    }
    
    public void stop() {
    	motor1.set(0);
    	motor2.set(0);
    }
}

