
package org.usfirst.frc.team3655.robot;


import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;

/**
 * Main Robot Class
 * @author G. Stewart
 * @version 1/27/2015
 */
public class Robot extends SampleRobot
{
    private RobotDrive mecanumDrive;
    private Joystick xBox1;
    private DoubleSolenoid solenoidMainElevator;
    private DoubleSolenoid solenoidElevatorKickers;
    private Gyro gyroscope;
    private double y = 0;
	private double x = 0;
	private double rotation = 0;
	private double xDeadzone = .1;
	private double yDeadzone = .1;
	private double rotationDeadzone = .1;
	private int autonStepNum = 0;
	private int autonMode = 0;
	

    public Robot() 
    {
    	mecanumDrive = new RobotDrive(RobotMap.driveForwardLeftMotor, RobotMap.driveRearLeftMotor, RobotMap.driveForwardRightMotor, RobotMap.driveRearRightMotor);
    	solenoidMainElevator = new DoubleSolenoid(RobotMap.solenoidElevator1, RobotMap.solenoidElevator2);
    	solenoidElevatorKickers = new DoubleSolenoid(RobotMap.solenoidElevatorKicker1, RobotMap.solenoidElevatorKicker2); 
    	gyroscope = new Gyro(RobotMap.gyroInput);
    	mecanumDrive.setExpiration(0.1);
    	mecanumDrive.setInvertedMotor(RobotDrive.MotorType.kFrontLeft, true);
    	mecanumDrive.setInvertedMotor(RobotDrive.MotorType.kRearLeft, false);
    	mecanumDrive.setInvertedMotor(RobotDrive.MotorType.kFrontRight, true);
    	mecanumDrive.setInvertedMotor(RobotDrive.MotorType.kRearRight, false);
        xBox1 = new Joystick(0);
    }

    /**
     * Auton Code
     */
    public void autonomous() 
    {
    	switch(autonStepNum)
    	{
    		case 0:
    		//Reset Variables For Auton Here
    		break;
    		
    		//Put in additonal steps here
    	}
    }

    /**
     * TeleOp Control
     */
    public void operatorControl() 
    {
    	//Intilization
        mecanumDrive.setSafetyEnabled(true);
        
        //Loop
        while (isOperatorControl() && isEnabled()) 
        {
        	/*
        	 * Controller Buttons
        	 */
        	{
        		//A
        		if(xBox1.getRawButton(1)) {
        			setElevator(true);
        		}
        		//B
        		if(xBox1.getRawButton(2)) {
        			setKickers(false);
        		}
        		//X
        		if(xBox1.getRawButton(3)) {
        			setKickers(true);
        		}
        		//Y
        		if(xBox1.getRawButton(4)) {
        			setElevator(false);
        		}
        		//L Top Trigger
        		if(xBox1.getRawButton(5)) {
        			pickUpBox();
        		}
        		//R Top Trigger
        		if(xBox1.getRawButton(6)) {
        			putDownBox();
        		}
        	}
        	
        	/*
        	 * Controller Axis
        	 */
        	{
        		//X
        		if(xBox1.getRawAxis(0) > xDeadzone || xBox1.getRawAxis(0) < -xDeadzone) {
        			x = xBox1.getRawAxis(0);
        		} 
        		else {
        			x = 0;
        		}	
        		//Y
        		if(xBox1.getRawAxis(1) > yDeadzone || xBox1.getRawAxis(1) < -yDeadzone) {
        			y = xBox1.getRawAxis(1);
        		}
        		else {
        			y = 0;
        		}      
        		//Rotation
        		if(xBox1.getRawAxis(2) - xBox1.getRawAxis(3) > rotationDeadzone || xBox1.getRawAxis(2) - xBox1.getRawAxis(3) < -rotationDeadzone) {
        			rotation = xBox1.getRawAxis(2) - xBox1.getRawAxis(3);
        		}
        		else {
        			rotation = 0;
        		}
        	}
        	
        	mecanumDrive.mecanumDrive_Polar(Math.sqrt(x * x + y * y), (Math.toDegrees(Math.atan2(y, x)) - 90), rotation);
        	
            Timer.delay(0.005);		// wait for a motor update time
        }
    }

    /**
     * Runs during test mode
     */
    public void test() 
    {
    	
    }
    
    public void putDownBox()
    {
    	setElevator(true);
    	Timer.delay(.4);
    	setKickers(false);
    	Timer.delay(1.2);
    	setElevator(false);
    }
    
    public void pickUpBox()
    {
    	setElevator(true);
    	Timer.delay(0.1);
    	setKickers(false);
    	Timer.delay(0.75);
    	setKickers(true);
    	Timer.delay(1);
    	setElevator(false);
    }
    
    public void setKickers(boolean state)
    {
    	if(state) {
    		solenoidElevatorKickers.set(DoubleSolenoid.Value.kForward);
    	} else {
    		solenoidElevatorKickers.set(DoubleSolenoid.Value.kReverse);
    	}
    }
    
    public void setElevator(boolean state)
    {
    	if(state) {
    		solenoidMainElevator.set(DoubleSolenoid.Value.kForward);
    	} else {
    		solenoidMainElevator.set(DoubleSolenoid.Value.kReverse);
    	}
    }
}
