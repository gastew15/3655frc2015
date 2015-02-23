package org.usfirst.frc.team3655.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.Compressor;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
    private Gyro gyroscope;
    
    //Variables
	private double x = 0;
	private double y = 0;
	private double rotation = 0;
	private int autonStepNum = 0;
	private int autonMode = 0;
	private boolean xBox1Button9Pressed = false;
	private boolean xBox1Button10Pressed = false;
	
	 // +- Deadzone on controller Axis
	private double yDeadzone = .1;
	private double xDeadzone = .1;
	private double rotationDeadzone = .1;
	
	 // 0 - 1 for Motor Output = Input * limiter
	private double yLimiter = 1; 
	private double xLimiter = 1; 
	private double rotationLimiter = 1; 

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
    	
    	//Inputs
    	gyroscope = new Gyro(RobotMap.gyroInput);
    }

    /**
     * Auton Code
     */
	public void autonomous() 
    {
    		compressor.start();
    	
    	//autonMode = (int)SmartDashboard.getNumber("Auton Mode", 0);
    	
    	//Main Auton Loop
    	while(isAutonomous() && isEnabled())
    	{
    		switch(autonStepNum)
    		{
    			case 0:
    				//Reset Variables For Auton Here
    				break;
    			case 1:
    				//Example
    				if(autonMode == 1) {
    					//Do One Thing
    				} 
    				else if(autonMode == 2) {
    					//Do Another
    				}
    				break;
    				
    			//Put in additonal steps here
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
    		compressor.start();
        
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
        		//Left Bin Lifter (Left JoyStick Button)
        		if(xBox1.getRawButton(9) && !xBox1Button9Pressed){
        			if(solenoidBinLifter1.get() == DoubleSolenoid.Value.kForward) {
        				solenoidBinLifter1.set(DoubleSolenoid.Value.kReverse);
        			} else {
        				solenoidBinLifter1.set(DoubleSolenoid.Value.kForward);	
        			}
        			xBox1Button9Pressed = true;
        		}
        		else if(!xBox1.getRawButton(9))
        		{
        			xBox1Button9Pressed = false;
        		}       		
        		//Right Bin Lifter (Right JoyStick Button)
        		if(xBox1.getRawButton(10) && !xBox1Button10Pressed){
        			if(solenoidBinLifter2.get() == DoubleSolenoid.Value.kForward) {
        				solenoidBinLifter2.set(DoubleSolenoid.Value.kReverse);
        			} else {
        				solenoidBinLifter2.set(DoubleSolenoid.Value.kForward);	
        			}
        			xBox1Button10Pressed = true;
        		}
        		else if(!xBox1.getRawButton(10))
        		{
        			xBox1Button10Pressed = false;
        		}
        	}
        	
        	/*
        	 * Controller Axis
        	 */
        	{
        		
        		//X
        		if(xBox1.getY() > xDeadzone || xBox1.getY() < -xDeadzone) {
        			x = xBox1.getY() * xLimiter;
        		} 
        		else {
        			x = 0;
        		}	
        		//Y
        		if(xBox1.getX() > yDeadzone || xBox1.getX() < -yDeadzone) {
        			y = xBox1.getX() * yLimiter;
        		}
        		else {
        			y = 0;
        		} 
        		
        		//Rotation
        		if(xBox1.getRawAxis(2) - xBox1.getRawAxis(3) > rotationDeadzone || xBox1.getRawAxis(2) - xBox1.getRawAxis(3) < -rotationDeadzone) {
        			rotation = (xBox1.getRawAxis(2) - xBox1.getRawAxis(3)) * rotationLimiter;
        		}
        		else {
        			rotation = 0;
        		}
        		
        	}

        	mecanumDrive.mecanumDrive_Polar(Math.sqrt(x * x + y * y), (Math.toDegrees(Math.atan2(y, x)) - 90), rotation);
        	
        	//DashBoard
        	//SmartDashboard.putNumber("Air Pressure", compressor.getCompressorCurrent());
        	
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
