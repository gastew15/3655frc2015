package org.usfirst.frc.team3655.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team3655.auton.*;

/**
 * Main Robot Class
 * @author G. Stewart & Will Reid
 * @version 3/31/2015
 */
public class Robot extends SampleRobot
{
	//Input Devices
	private Joystick xBox1;
    private Joystick xBox2;
    
    //SubSystems
    private RobotDrive mecanumDrive;
    private DoubleSolenoid solenoidMainElevator;
    private DoubleSolenoid solenoidBinLifter1;
    private DoubleSolenoid solenoidBinLifter2;
    private Compressor compressor;
    private Victor intakeBinWheel1;
    private Victor intakeBinWheel2;
    private Gyro gyroscope;
    private AutonModeHandler autonModeHandler = new AutonModeHandler();
    
    //Variables
	private double x = 0;
	private double y = 0;
	private double rotation = 0;
	private boolean xBox2Button9Pressed = false;
	private boolean xBox2Button10Pressed = false;
	
	//Gyro Input to Actual Adjust
	private double angleOffInput = 0;
	private double angleAdjustCont = .0027; //Motor Output Adjust per Degree
	private double angleOffThreshold = 1; //Degrees off before correction occurs, + & -
	
	 // +- Deadzone on controller Axis
	private double yDeadzone = .1;
	private double xDeadzone = .1;
	private double rotationDeadzone = .1;
	
	 // 0 - 1 for Motor Output = Input * limiter
	private double yLimiter = .75; 
	private double xLimiter = .75; 
	private double rotationLimiter = .75; 

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
    	
    	//Solenoids
    	solenoidMainElevator = new DoubleSolenoid(RobotMap.solenoidElevator1, RobotMap.solenoidElevator2);
    	solenoidBinLifter1 = new DoubleSolenoid(RobotMap.solenoidBinLifter1_1, RobotMap.solenoidBinLifter1_2);
    	solenoidBinLifter2 = new DoubleSolenoid(RobotMap.solenoidBinLifter2_1, RobotMap.solenoidBinLifter2_2);
    	intakeBinWheel1 = new Victor(RobotMap.intakeWheel1);
    	intakeBinWheel2 = new Victor(RobotMap.intakeWheel2);
    	compressor = new Compressor();
    	compressor.start();
    	solenoidBinLifter2.set(DoubleSolenoid.Value.kForward);	
    	setElevator(false);
    	
    	//Inputs
    	gyroscope = new Gyro(RobotMap.gyroInput);
    	gyroscope.reset();
    	gyroscope.setSensitivity(0.0072); //Check Data Sheet
    	
    	//Auton Mode Load
    	AutonModeHandler.getAutonMode();
    }

    /**
     * Auton Code
     */
	public void autonomous() 
    {    	
    	//autonMode = (int)SmartDashboard.getNumber("Auton Mode", 0);
		
		boolean isTripped;
		int counter;

		//Single Acuation Command
		//Step 1: Pick Up Can
		counter = 0;  					//Leave Default
		isTripped = false;				//Leave Default
		while(counter < 200 && isEnabled() && isAutonomous()) //Count < timeTillNextStep (In Ticks, 200 ticks in a second) 	&& (Leave Default) && (LeaveDefault)
		{
			if(!isTripped) //Leave Defualt
			{
				solenoidBinLifter2.set(DoubleSolenoid.Value.kForward); //Action to perform goes here
				isTripped = true; //Leave Default
			}
			Timer.delay(.005); //Leave Default
			counter++; //Leave Default
			//200 ticks in a second
		}
		
		//move out
		counter = 0;
    	isTripped = false;
    	while(counter < 25  && isEnabled() && isAutonomous())
    	{
    		y = -1;
    		x = 0;
    		rotation = .05;
    		mecanumDrive.mecanumDrive_Polar(Math.sqrt(x * x + y * y), (Math.toDegrees(Math.atan2(y, x)) - 90), rotation);
    		Timer.delay(.005);
    		counter++;
    		//200 ticks in a second
    	}
		
		
		/* Probably Needs Time to be changed*/
		//Constant Running Command
    	//Step 2: Drive Forward
    	counter = 0;		//Leave Default
    	isTripped = false;	//Leave Default
    	while(counter < 250  && isEnabled() && isAutonomous())  //Count < timeTillNextStep (In Ticks, 200 ticks in a second) 	&& (Leave Default) && (LeaveDefault)
    	{
    		y = 0; //X Value for side to side movement
    		x = .7; //Y Value for forward or back movement
    		rotation = .05; //Rotation Value
    		mecanumDrive.mecanumDrive_Polar(Math.sqrt(x * x + y * y), (Math.toDegrees(Math.atan2(y, x)) - 90), rotation); //Leave Default
    		Timer.delay(.005); //leave Default
    		counter++; //Leave Default
    		//200 ticks in a second
    	}
		
    	/* Probably Needs Time to be changed*/
    	//Step 3: Rotate Right
    	counter = 0;
    	isTripped = false;
    	while(counter < 275  && isEnabled() && isAutonomous())
    	{
    		y = 0;
    		x = 0;
    		rotation = .75;
    		mecanumDrive.mecanumDrive_Polar(Math.sqrt(x * x + y * y), (Math.toDegrees(Math.atan2(y, x)) - 90), rotation);
    		Timer.delay(.005);
    		counter++;
    		//200 ticks in a second
    	}
    	
    	/* Probably Needs Time to be changed*/
    	//Step 4: Drive Forward
    	counter = 0;
    	isTripped = false;
    	while(counter < 400  && isEnabled() && isAutonomous())
    	{
    		y = 0;
    		x = .7;
    		rotation = .05;
    		mecanumDrive.mecanumDrive_Polar(Math.sqrt(x * x + y * y), (Math.toDegrees(Math.atan2(y, x)) - 90), rotation);
    		Timer.delay(.005);
    		counter++;
    		//200 ticks in a second
    	}
    		
    	//Step 5: Pick Up Box
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
		
		/* Probably Needs Time to be changed, possibly even direction changed, or even just deleted.. */
		//Step 6: Rotate Left
    	counter = 0;
    	isTripped = false;
    	while(counter < 250  && isEnabled() && isAutonomous())
    	{
    		y = 0;
    		x = 0;
    		rotation = -.75;
    		mecanumDrive.mecanumDrive_Polar(Math.sqrt(x * x + y * y), (Math.toDegrees(Math.atan2(y, x)) - 90), rotation);
    		Timer.delay(.005);
    		counter++;
    		//200 ticks in a second
    	}
    	
    	/* Probably Needs Time to be Increased, or possibly right*/
    	//Step 7: Drive Backward
    	counter = 0;
    	isTripped = false;
    	while(counter < 1000  && isEnabled() && isAutonomous())
    	{
    		y = -1;
    		x = 0;
    		rotation = .05;
    		mecanumDrive.mecanumDrive_Polar(Math.sqrt(x * x + y * y), (Math.toDegrees(Math.atan2(y, x)) - 90), rotation);
    		Timer.delay(.005);
    		counter++;
    		//200 ticks in a second
    	}  
    	
    	//End
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
        	 * Controller Buttons  (Xbox Controller 2)
        	 */
        	{
        		//A
        		if(xBox2.getRawButton(1)) {
        			setElevator(true);
        		}
        		//B
        		if(xBox2.getRawButton(2)) {
        			intakeBinWheel1.set(.8);
        			intakeBinWheel2.set(.8);
        		}
        		//X
        		if(xBox2.getRawButton(3)) {
        			intakeBinWheel1.set(-.8);
        			intakeBinWheel2.set(-.8);
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
        		if(xBox1.getY() > xDeadzone || xBox1.getY() < -xDeadzone) {
        			x = xBox1.getY() * xLimiter * -1;
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
        			rotation = (xBox1.getRawAxis(2) - xBox1.getRawAxis(3)) * rotationLimiter * -1;
        		}
        		else {
        			rotation = 0;
        		}     		
        	}
        	
        	//Dashboard Writing
        	boolean binLiftLeft;
        	boolean binLiftRight;
        	
        	if(solenoidBinLifter1.get().equals(DoubleSolenoid.Value.kForward))
        		binLiftLeft = true;
        	else
        		binLiftLeft = false;

        	if(solenoidBinLifter2.get().equals(DoubleSolenoid.Value.kForward))
        		binLiftRight = true;
        	else
        		binLiftRight = false;
        	
        	SmartDashboard.putBoolean("BinLifer Left", binLiftLeft);
        	SmartDashboard.putBoolean("BinLifer Right", binLiftRight);
        	SmartDashboard.putNumber("Y", xBox1.getRawAxis(5) * -1);
        	SmartDashboard.putNumber("X", xBox1.getRawAxis(4));
        	SmartDashboard.putNumber("Rotation", xBox1.getRawAxis(2) - xBox1.getRawAxis(3) * -1);
        	SmartDashboard.putNumber("Controller Input Angle", getJoystickAngle(y,x)); //X & Y inverted
        	SmartDashboard.putNumber("Gyro Angle", gyroscope.getAngle());
        	
        	double angleModifer = -1;
        	//JoyStick to base angle Correction
        	if(getJoystickAngle(x,y) > 0 && getJoystickAngle(x,y) <= 90)
        		angleModifer = 0;
        	else if(getJoystickAngle(x,y) > 90 && getJoystickAngle(x,y) <= 180)
        		angleModifer = 90;
        	else if(getJoystickAngle(x,y) > 180 && getJoystickAngle(x,y) <= 270)
        		angleModifer = 180;
        	else if(getJoystickAngle(x,y) > 270 && getJoystickAngle(x,y) <= 360)
        		angleModifer = 270;
        	
        	if(angleModifer != -1)
        	{
        		angleOffInput = getJoystickAngle(x,y) - (gyroscope.getAngle() + angleModifer);
        		if(angleOffInput < angleOffThreshold && angleOffInput > - angleOffThreshold)
        			angleOffInput = 0;
        	}
        	else
        	{
        		angleOffInput = 0;
        	}
        	SmartDashboard.putNumber("Angle Off Input", angleOffInput);
        	SmartDashboard.putNumber("AutonMode", AutonModeHandler.getAutonMode());
        	
        	//Drive Base
        	//Add on this to the rotation	(angleOffInput * angleAdjustConst)
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
    	double autonModeProgTimer = 0;
    	boolean autonModeProgActive = false;
    	
    	//Auton Mode Programming
		//Select or Start?
    	if(!autonModeProgActive)
    	{
    		if(xBox1.getRawButton(7)) {
    			autonModeProgTimer += 0.005;
    		}
    		else {
    			autonModeProgTimer = 0;
    		}
		
    		if(autonModeProgTimer >= 1) {
    			autonModeProgActive = true;
    			autonModeProgTimer = 0;
    		}
    	}
    	else
    	{
    		//A
        	if(xBox2.getRawButton(1)) {
        		autonModeHandler.setAutonMode(1);
        		autonModeProgActive = false;
        	}
        	//B
        	if(xBox2.getRawButton(2)) {
        		autonModeProgActive = false;
        		autonModeHandler.setAutonMode(1);
        	}
        	//X
        	if(xBox2.getRawButton(3)) {
        		autonModeProgActive = false;
        		autonModeHandler.setAutonMode(1);
        	}
        	
        	if(!autonModeProgActive)
        	{
        		autonModeHandler.saveData();
        	}
    	}
    	
    	SmartDashboard.putBoolean("Auton Program Active", autonModeProgActive);
    	SmartDashboard.putNumber("AutonMode", AutonModeHandler.getAutonMode());
		
		//Tick Delay (200 times a second)
        Timer.delay(0.005);	
    }
    
    public void putDownBox()
    {
    	boolean isTripped;
		int counter;

		//Step 1: Lower Main Elevator
		counter = 0;
		isTripped = false;
		while(counter < 400 && isEnabled())
		{
			if(!isTripped)
			{
				setElevator(true);
				isTripped = true;
			}
			Timer.delay(.005);
			counter++;
			//200 ticks in a second
		}
				
			//Step 3: Pulls up main elevator
				counter = 0;
				isTripped = false;
				while(counter < 1 && isEnabled())
				{
					if(!isTripped)
					{
						setElevator(false);
						isTripped = true;
					}
					Timer.delay(.005);
					counter++;
					//200 ticks in a second
				}
    }
    
    public void pickUpBox()
    {
    	boolean isTripped;
		int counter;

		//Step 1: Lower Main Elevator
		counter = 0;
		isTripped = false;
		while(counter < 100 && isEnabled())
		{
			if(!isTripped)
			{
				setElevator(true);
				isTripped = true;
			}
			Timer.delay(.005);
			counter++;
			//200 ticks in a second
		}
				
			//Step 4: Pushes in Kickers
				counter = 0;
				isTripped = false;
				while(counter < 1 && isEnabled())
				{
					if(!isTripped)
					{
						setElevator(false);
						isTripped = true;
					}
					Timer.delay(.005);
					counter++;
					//200 ticks in a second
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

    
    public double getJoystickAngle(double x, double y)
    {
    	int quadrentValue = 0;
    	
    	//Top Right
        if((y <= 1 && y > 0) && (x <= 1 && x > 0))
        {
             quadrentValue = 0;
        }
        //Top Left
        else if((y <= 1 && y > 0) && (x >= - 1 && x < 0))
        {
             quadrentValue = 90;
        }
        //Bottom Left
        else if((y < 0 && y >= -1) && (x >= - 1 && x < 0))
        {
             quadrentValue = 180;
        }
        //Bottom Right
        else if((y < 0 && y >= -1) && (x <= 1 && x > 0))
        {
             quadrentValue = 270;
        }
        else
        {
          if(y == 1)
          {
             quadrentValue = 0;
          }
          else if(y == -1)
          {
             quadrentValue = 180;
          }
          else if(x == -1)
          {
             quadrentValue = 180;
          }
          else if(x == 1)
          {
             quadrentValue = 360;
          }
        }
        
        //System.out.println("Controller Angle: " + desiredControllerAngle);
        return Math.round(Math.atan2(Math.abs(y), Math.abs(x)) * (180/Math.PI)) + (quadrentValue);
    }
}
