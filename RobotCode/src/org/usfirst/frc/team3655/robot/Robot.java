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
	private int autonMode = 0;
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
    	gyroscope = new Gyro(RobotMap.gyroInput);
    	gyroscope.setSensitivity(0.0072); //Check Data Sheet
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
		
		/* Probably Needs Time to be changed*/
		//Constant Running Command
    	//Step 2: Drive Forward
    	counter = 0;		//Leave Default
    	isTripped = false;	//Leave Default
    	while(counter < 200  && isEnabled() && isAutonomous())  //Count < timeTillNextStep (In Ticks, 200 ticks in a second) 	&& (Leave Default) && (LeaveDefault)
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
    	while(counter < 200  && isEnabled() && isAutonomous())
    	{
    		y = 0;
    		x = 0;
    		rotation = -.75;
    		mecanumDrive.mecanumDrive_Polar(Math.sqrt(x * x + y * y), (Math.toDegrees(Math.atan2(y, x)) - 90), rotation);
    		Timer.delay(.005);
    		counter++;
    		//200 ticks in a second
    	}
    	
    	/* Probably Needs Time to be changed*/
    	//Step 4: Drive Forward
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
		//Step 6: Rotate Left Correction
    	counter = 0;
    	isTripped = false;
    	while(counter < 60  && isEnabled() && isAutonomous())
    	{
    		y = 0;
    		x = 0;
    		rotation = .75;
    		mecanumDrive.mecanumDrive_Polar(Math.sqrt(x * x + y * y), (Math.toDegrees(Math.atan2(y, x)) - 90), rotation);
    		Timer.delay(.005);
    		counter++;
    		//200 ticks in a second
    	}
    	
    	/* Probably Needs Time to be Increased, or possibly right*/
    	//Step 7: Drive Backward
    	counter = 0;
    	isTripped = false;
    	while(counter < 600  && isEnabled() && isAutonomous())
    	{
    		y = 0;
    		x = -.7;
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
        	
        	//JoyStick to base angle Correction
        	angleOffInput = getJoystickAngle(y,x) - gyroscope.getAngle();
        	if(angleOffInput < angleOffThreshold && angleOffInput > - angleOffThreshold)
        		angleOffInput = 0;
        	
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
		
		//Step 2: Pulls in Kicker Arms
				counter = 0;
				isTripped = false;
				while(counter < 150 && isEnabled())
				{
					if(!isTripped)
					{
						setKickers(false);
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
		
		//Step 2: Pulls in kicker arms
				counter = 0;
				isTripped = false;
				while(counter < 260 && isEnabled())
				{
					if(!isTripped)
					{
						setKickers(false);
						isTripped = true;
					}
					Timer.delay(.005);
					counter++;
					//200 ticks in a second
				}
				
			//Step 3: Pushes in Kickers
				counter = 0;
				isTripped = false;
				while(counter < 150 && isEnabled())
				{
					if(!isTripped)
					{
						setKickers(true);
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
