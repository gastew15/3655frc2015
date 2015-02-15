
package org.usfirst.frc.team3655.robot;


import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Victor;

/**
 * Main Robot Class
 * @author G. Stewart
 * @version 1/13/2015
 */
public class Robot extends SampleRobot
{
    private RobotDrive mecanumDrive;
    private Joystick xBox1;
    private DoubleSolenoid solenoidMainElevator;
    private DoubleSolenoid solenoidElevatorKickers;
    private Victor elevatorWinch;
    private Gyro gyroscope;
    private double magnitude = 0;
	private double direction = 0;
	private double rotation = 0;
	private int autonStepNum = 0;
	private int autonMode = 0;
	
	 // +- Deadzone on controller Axis
	private double magnitudeDeadzone = .1;
	private double directionDeadzone = .1;
	private double rotationDeadzone = .1;
	 // 0 - 1 for Motor Output = Input * limiter
	private double magnitudeLimiter = 1; 
	private double directionLimiter = 1; 
	private double rotationLimiter = 1; 

    public Robot() 
    {
    	mecanumDrive = new RobotDrive(RobotMap.driveForwardLeftMotor, RobotMap.driveRearLeftMotor, RobotMap.driveForwardRightMotor, RobotMap.driveRearRightMotor);
    	solenoidMainElevator = new DoubleSolenoid(RobotMap.solenoidElevator1, RobotMap.solenoidElevator2);
    	solenoidElevatorKickers = new DoubleSolenoid(RobotMap.solenoidElevatorKicker1, RobotMap.solenoidElevatorKicker2); 
    	elevatorWinch = new Victor(RobotMap.elevatorWinchMotor);
    	elevatorWinch.enableDeadbandElimination(true);
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
        elevatorWinch.setSafetyEnabled(true);
        
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
        		//Winch Buttons (Start & Select)
        		if(xBox1.getRawButton(8)) {
        			elevatorWinch.set(.8);
        		}
        		else if(xBox1.getRawButton(7))
        		{
        			elevatorWinch.set(-.8);
        		}
        		else
        		{
        			elevatorWinch.set(0);
        		}
        	}
        	
        	/*
        	 * Controller Axis
        	 */
        	{
        		//X
        		if(xBox1.getMagnitude() > magnitudeDeadzone || xBox1.getMagnitude() < -magnitudeDeadzone) {
        			magnitude = xBox1.getMagnitude() * magnitudeLimiter;
        		} 
        		else {
        			magnitude = 0;
        		}	
        		//Y
        		if(xBox1.getDirectionDegrees() > directionDeadzone || xBox1.getDirectionDegrees() < -directionDeadzone) {
        			direction = xBox1.getDirectionDegrees() * directionLimiter;
        		}
        		else {
        			direction = 0;
        		}      
        		//Rotation
        		if(xBox1.getRawAxis(2) - xBox1.getRawAxis(3) > rotationDeadzone || xBox1.getRawAxis(2) - xBox1.getRawAxis(3) < -rotationDeadzone) {
        			rotation = (xBox1.getRawAxis(2) - xBox1.getRawAxis(3)) * rotationLimiter;
        		}
        		else {
        			rotation = 0;
        		}
        	}
        	
        	mecanumDrive.mecanumDrive_Polar(magnitude, direction, rotation);
        	//mecanumDrive.mecanumDrive_Polar(Math.sqrt(x * x + y * y), (Math.toDegrees(Math.atan2(y, x)) - 90), rotation);
        	
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
