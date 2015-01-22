package org.usfirst.frc.team3655.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team3655.robot.RobotMap;
import edu.wpi.first.wpilibj.DoubleSolenoid;

/**
 * Pneumatic elevator for robot
 * @author G. Stewart
 * @version 1/21/2015
 */

public class Elevator extends Subsystem 
{
	private DoubleSolenoid solenoidLeft = new DoubleSolenoid(RobotMap.solenoidElevatorLeft1, RobotMap.solenoidElevatorLeft2);
	private DoubleSolenoid solenoidRight = new DoubleSolenoid(RobotMap.solenoidElevatorRight1, RobotMap.solenoidElevatorRight2);
	private DoubleSolenoid solenoidPush = new DoubleSolenoid(RobotMap.solenoidElevatorPushRight, RobotMap.solenoidElevatorPushLeft);

    public void initDefaultCommand() 
    {
    	solenoidPush.set(DoubleSolenoid.Value.kReverse);
    	solenoidLeft.set(DoubleSolenoid.Value.kReverse);
    	solenoidRight.set(DoubleSolenoid.Value.kReverse);
    }
    
    public void setMainArm(boolean solenoidState) 
    {
    	if(solenoidState) {
    		solenoidLeft.set(DoubleSolenoid.Value.kForward);
    		solenoidRight.set(DoubleSolenoid.Value.kForward);
    	}
    	else {
    		solenoidLeft.set(DoubleSolenoid.Value.kReverse);
    		solenoidRight.set(DoubleSolenoid.Value.kReverse);
    	}
    }
    
    public void setKickerArm(boolean solenoidState)
    {
    	if(solenoidState) {
    		solenoidPush.set(DoubleSolenoid.Value.kForward);
    	}
    	else {
    		solenoidPush.set(DoubleSolenoid.Value.kReverse);
    	}
    }
    
    public boolean getMainArmState() 
    {
    	if(solenoidLeft.get() == DoubleSolenoid.Value.kForward) {
    		return true;
    	}
    	else {
    		return false;
    	}
    }
    
    public boolean getKickerArmState()
    {
    	if(solenoidPush.get() == DoubleSolenoid.Value.kForward) {
    		return true;
    	}
    	else {
    		return false;
    	}
    }
    
    public void reset() 
    {
    	solenoidLeft.set(DoubleSolenoid.Value.kOff);
		solenoidRight.set(DoubleSolenoid.Value.kOff);
		solenoidPush.set(DoubleSolenoid.Value.kOff);
    }
}

