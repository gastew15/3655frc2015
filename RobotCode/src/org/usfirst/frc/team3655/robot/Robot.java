package org.usfirst.frc.team3655.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
//import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Main Robot Class
 * @author G. Stewart & Will Reid
 * @version 2/18/2015
 */
public class Robot extends SampleRobot
{
	//Input Devices
	private Joystick xBox1;
    private Joystick xBox2;
    
    //SubSystems
    private RobotDrive mecanumDrive;
    private DoubleSolenoid solenoidMainElevator;
    private DoubleSolenoid solenoidElevatorKickers;
    private DoubleSolenoid solenoidBinLifter1;
    private DoubleSolenoid solenoidBinLifter2;
    private Compressor compressor;
    private Victor elevatorWinch;
    //private Gyro gyroscope;
    
    //Variables
	private double x = 0;
	private double y = 0;
	private double rotation = 0;
	private int autonMode = 0;
	private boolean xBox2Button9Pressed = false;
	private boolean xBox2Button10Pressed = false;
	
	 // +- Deadzone on controller Axis
	private double yDeadzone = .1;
	private double xDeadzone = .1;
	private double rotationDeadzone = .1;
	
	 // 0 - 1 for Motor Output = Input * limiter
	private double yLimiter = .9; 
	private double xLimiter = .9; 
	private double rotationLimiter = .9; 

    public Robot() 
    {
    	//JoySticks
    	xBox1 = new Joystick(0);
        xBox2 = new Joystick(1);
    	
    	//Drive
    	mecanumDrive = new RobotDrive(RobotMap.driveForwardLeftMotor, RobotMap.driveRearLeftMotor, RobotMap.driveForwardRightMotor, RobotMap.driveRearRightMotor);
    	mecanumDrive.setExpiration(0.1);
    	mecanumDrive.setInvertedMotor(RobotDrive.MotorType.kFrontLeft, false);
    	mecanumDrive.setInvertedMotor(RobotDrive.MotorType.kRearLeft, false);
    	mecanumDrive.setInvertedMotor(RobotDrive.MotorType.kFrontRight, true);
    	mecanumDrive.setInvertedMotor(RobotDrive.MotorType.kRearRight, true);
    	
    	//Motors
    	elevatorWinch = new Victor(RobotMap.elevatorWinchMotor);
    	elevatorWinch.enableDeadbandElimination(true);
    	
    	//Solenoids
    	solenoidMainElevator = new DoubleSolenoid(RobotMap.solenoidElevator1, RobotMap.solenoidElevator2);
    	solenoidElevatorKickers = new DoubleSolenoid(RobotMap.solenoidElevatorKicker1, RobotMap.solenoidElevatorKicker2); 
    	solenoidBinLifter1 = new DoubleSolenoid(RobotMap.solenoidBinLifter1_1, RobotMap.solenoidBinLifter1_2);
    	solenoidBinLifter2 = new DoubleSolenoid(RobotMap.solenoidBinLifter2_1, RobotMap.solenoidBinLifter2_2);
    	compressor = new Compressor();
    	compressor.start();
    	solenoidBinLifter2.set(DoubleSolenoid.Value.kForward);	
    	setElevator(false);
    	setKickers(false);
    	//Inputs
    	//gyroscope = new Gyro(RobotMap.gyroInput);
    }

    /**
     * Auton Code
     */
	public void autonomous() 
    {    	
    	//autonMode = (int)SmartDashboard.getNumber("Auton Mode", 0);
		
		boolean isTripped;
		int counter;
		//boolean craptripped = false;
		//while (isAutonomous() && isEnabled()) 
		{
			//if(!craptripped)
			{
		//1st Step
			counter = 0;
			isTripped = false;
			while(counter < 200 && isEnabled() && isAutonomous())
			{
				if(!isTripped)
				{
					solenoidBinLifter2.set(DoubleSolenoid.Value.kForward);
					isTripped = true;
				}
				Timer.delay(.005);
				counter++;
			}
    				
    	//2nd Step
    		counter = 0;
    		isTripped = false;
    		while(counter < 300  && isEnabled() && isAutonomous())
    		{
    			y = 0;
    			x = -.7;
    			rotation = .05;
    			mecanumDrive.mecanumDrive_Polar(Math.sqrt(x * x + y * y), (Math.toDegrees(Math.atan2(y, x)) - 90), rotation);
    			Timer.delay(.005);
    			counter++;
    			//200 ticks in a second
    		}
    		
    	//3rd Step	
    		counter = 0;
    		isTripped = false;
    		while(counter < 30  && isEnabled() && isAutonomous())
    		{
    			y = 0;
    			x = 0;
    			rotation = .75;
    			mecanumDrive.mecanumDrive_Polar(Math.sqrt(x * x + y * y), (Math.toDegrees(Math.atan2(y, x)) - 90), rotation);
    			Timer.delay(.005);
    			counter++;
    			//200 ticks in a second
    		}
    		
    	//4th Step	
    		counter = 0;
    		isTripped = false;
    		while(counter < 223  && isEnabled() && isAutonomous())
    		{
    			y = .75;
    			x = 0;
    			rotation = 0;
    			mecanumDrive.mecanumDrive_Polar(Math.sqrt(x * x + y * y), (Math.toDegrees(Math.atan2(y, x)) - 90), rotation);
    			Timer.delay(.005);
    			counter++;
    			//200 ticks in a second
    		}
    		
    	//5th Step	
    		counter = 0;
    		isTripped = false;
    		while(counter < 345  && isEnabled() && isAutonomous())
    		{
    			y = 0;
    			x = .7;
    			rotation = .05;
    			mecanumDrive.mecanumDrive_Polar(Math.sqrt(x * x + y * y), (Math.toDegrees(Math.atan2(y, x)) - 90), rotation);
    			Timer.delay(.005);
    			counter++;
    			//200 ticks in a second
    		}
    		
    	//6th Step	
    		counter = 0;
			isTripped = false;
			while(counter < 100  && isEnabled() && isAutonomous())
			{
				if(!isTripped)
				{
					pickUpBox();
					isTripped = true;
				}
				Timer.delay(.005);
				counter++;
			}
    		
    	//7th Step
    		counter = 0;
    		isTripped = false;
    		while(counter < 300  && isEnabled() && isAutonomous())
    		{
    			y = 0;
    			x = .7;
    			rotation = .05;
    			mecanumDrive.mecanumDrive_Polar(Math.sqrt(x * x + y * y), (Math.toDegrees(Math.atan2(y, x)) - 90), rotation);
    			Timer.delay(.005);
    			counter++;
    			//200 ticks in a second
    		}
    		
			}	
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
        	 * Controller Buttons  (Xbox Controller 2)
        	 */
        	{
        		//A
        		if(xBox2.getRawButton(1)) {
        			setElevator(true);
        		}
        		//B
        		if(xBox2.getRawButton(2)) {
        			setKickers(false);
        		}
        		//X
        		if(xBox2.getRawButton(3)) {
        			setKickers(true);
        		}
        		//Y
        		if(xBox2.getRawButton(4)) {
        			setElevator(false);
        		}		
        		//L Top Trigger
        		if(xBox2.getRawButton(5)) {
        			putDownBox();       			
        		}      		
        		//R Top Trigger
        		if(xBox2.getRawButton(6)) {
        			pickUpBox();
        		}     		
        		//Winch Buttons (Start & Select)
        		if(xBox2.getRawButton(8)) {
        			elevatorWinch.set(.8);
        		}
        		else if(xBox2.getRawButton(7))
        		{
        			elevatorWinch.set(-.8);
        		}
        		else
        		{
        			elevatorWinch.set(0);
        		}      		
        		//Left Bin Lifter (Left JoyStick Button)
        		if(xBox2.getRawButton(9) && !xBox2Button9Pressed){
        			if(solenoidBinLifter1.get() == DoubleSolenoid.Value.kForward) {
        				solenoidBinLifter1.set(DoubleSolenoid.Value.kReverse);
        			} else {
        				solenoidBinLifter1.set(DoubleSolenoid.Value.kForward);	
        			}
        			xBox2Button9Pressed = true;
        		}
        		else if(!xBox2.getRawButton(9))
        		{
        			xBox2Button9Pressed = false;
        		}       		
        		//Right Bin Lifter (Right JoyStick Button)
        		if(xBox2.getRawButton(10) && !xBox2Button10Pressed){
        			if(solenoidBinLifter2.get() == DoubleSolenoid.Value.kForward) {
        				solenoidBinLifter2.set(DoubleSolenoid.Value.kReverse);
        			} else {
        				solenoidBinLifter2.set(DoubleSolenoid.Value.kForward);	
        			}
        			xBox2Button10Pressed = true;
        		}
        		else if(!xBox2.getRawButton(10))
        		{
        			xBox2Button10Pressed = false;
        		}
        	}
        	
        	/*
        	 * Controller Axis (Xbox Controller 1)
        	 */
        	{	
        		//X
        		if(xBox1.getRawAxis(5) > xDeadzone || xBox1.getRawAxis(5) < -xDeadzone) {
        			x = xBox1.getRawAxis(5) * xLimiter * -1;
        		} 
        		else {
        			x = 0;
        		}	
        		//Y
        		if(xBox1.getRawAxis(4) > yDeadzone || xBox1.getRawAxis(4) < -yDeadzone) {
        			y = xBox1.getRawAxis(4) * yLimiter;
        		}
        		else {
        			y = 0;
        		} 
        		
        		//Rotation
        		if(xBox1.getRawAxis(2) - xBox1.getRawAxis(3) > rotationDeadzone || xBox1.getRawAxis(2) - xBox1.getRawAxis(3) < -rotationDeadzone) {
        			rotation = (xBox1.getRawAxis(2) - xBox1.getRawAxis(3)) * rotationLimiter * -1;
        		}
        		else {
        			rotation = 0;
        		}     		
        	}
        	
        	//Dashboard Writing
        	boolean binLiftLeft;
        	boolean binLiftRight;
        	
        	if(solenoidBinLifter1.get() == DoubleSolenoid.Value.kForward)
        		binLiftLeft = true;
        	else
        		binLiftLeft = false;

        	if(solenoidBinLifter2.get() == DoubleSolenoid.Value.kForward)
        		binLiftRight = true;
        	else
        		binLiftRight = false;
        	
        	SmartDashboard.putBoolean("BinLifer Left", binLiftLeft);
        	SmartDashboard.putBoolean("BinLifer Right", binLiftRight);
        	SmartDashboard.putNumber("Y", xBox1.getRawAxis(5) * -1);
        	SmartDashboard.putNumber("X", xBox1.getRawAxis(4));
        	SmartDashboard.putNumber("Rotation", xBox1.getRawAxis(2) - xBox1.getRawAxis(3) * -1);
        	
        	//Drive Base
        	mecanumDrive.mecanumDrive_Polar(Math.sqrt(x * x + y * y), (Math.toDegrees(Math.atan2(y, x)) - 90), rotation);
        	
        	//Tick Delay (200 times a second)
            Timer.delay(0.005);		
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
    	Timer.delay(2.0);
    	setKickers(false);
    	Timer.delay(.75);
    	setElevator(false);
    }
    
    public void pickUpBox()
    {
    	setElevator(true);
    	Timer.delay(0.5);
    	setKickers(false);
    	Timer.delay(1.3);
    	setKickers(true);
    	Timer.delay(.75);
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
