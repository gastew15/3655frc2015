
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
    private double xBox1DeadzoneX = .1;
    private double xBox1DeadzoneY = .1;
    private double xBox1DeadzoneZ = .1;

    public Robot() 
    {
    	mecanumDrive = new RobotDrive(RobotMap.driveForwardLeftMotor, RobotMap.driveRearLeftMotor, RobotMap.driveForwardRightMotor, RobotMap.driveRearRightMotor);
    	solenoidMainElevator = new DoubleSolenoid(RobotMap.solenoidElevator1, RobotMap.solenoidElevator2);
    	solenoidElevatorKickers = new DoubleSolenoid(RobotMap.solenoidElevatorKicker1, RobotMap.solenoidElevatorKicker2); 
    	gyroscope = new Gyro(RobotMap.gyroInput);
    	mecanumDrive.setExpiration(0.1);
    	mecanumDrive.setInvertedMotor(RobotDrive.MotorType.kFrontLeft, false);
    	mecanumDrive.setInvertedMotor(RobotDrive.MotorType.kRearLeft, false);
    	mecanumDrive.setInvertedMotor(RobotDrive.MotorType.kFrontRight, true);
    	mecanumDrive.setInvertedMotor(RobotDrive.MotorType.kRearRight, true);
        xBox1 = new Joystick(0);
    }

    /**
     * Blank
     */
    public void autonomous() 
    {
    	/*
        myRobot.setSafetyEnabled(false);
        myRobot.drive(-0.5, 0.0);	// drive forwards half speed
        Timer.delay(2.0);		//    for 2 seconds
        myRobot.drive(0.0, 0.0);	// stop robot
        */
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
        	//Controller Buttons
        	if(xBox1.getRawButton(1)) {
        		setElevator(true);
        	}
        	if(xBox1.getRawButton(2)) {
        		setKickers(false);
        	}
        	if(xBox1.getRawButton(3)) {
        		setKickers(true);
        	}
        	if(xBox1.getRawButton(4)) {
        		setElevator(false);
        	}
        	if(xBox1.getRawButton(5)) {
        		pickUpBox();
        	}
        	if(xBox1.getRawButton(6)) {
        		putDownBox();
        	}
        	
        	//mecanumDrive.tankDrive(xBox1.getRawAxis(1), xBox1.getRawAxis(5), true);
        	mecanumDrive.mecanumDrive_Polar(xBox1.getMagnitude(), xBox1.getDirectionDegrees(), xBox1.getRawAxis(2) - xBox1.getRawAxis(3));
        	//teleOpDrive();
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

    public void teleOpDrive()
    {
    	//X Y Driving
    	if(((xBox1.getX() > .1 && xBox1.getX() < .5) || (xBox1.getX() < -.1 && xBox1.getX() > -.5)) || ((xBox1.getY() > .1 && xBox1.getY() < .5) || (xBox1.getY() < -.1 && xBox1.getY() > -.5))) {
    		mecanumDrive.setLeftRightMotorOutputs(xBox1.getMagnitude(), xBox1.getDirectionDegrees());
    	}
    	else
    	{
    		if(xBox1.getX() > .5 && xBox1.getY() > .5)	
    		{
    	    	mecanumDrive.setInvertedMotor(RobotDrive.MotorType.kRearLeft, true);
    	    	mecanumDrive.setInvertedMotor(RobotDrive.MotorType.kFrontRight, false);
    	    	
    	    	mecanumDrive.setLeftRightMotorOutputs((xBox1.getX() + xBox1.getY()) / 2, (xBox1.getX() + xBox1.getY()) / 2);
    	    	
    	    	mecanumDrive.setInvertedMotor(RobotDrive.MotorType.kRearLeft, false);
    	    	mecanumDrive.setInvertedMotor(RobotDrive.MotorType.kFrontRight, true);
    		}
    		else if(xBox1.getX() < -.5 && xBox1.getY() < -.5)
    		{
    			mecanumDrive.setInvertedMotor(RobotDrive.MotorType.kFrontLeft, true);
    	    	mecanumDrive.setInvertedMotor(RobotDrive.MotorType.kRearRight, false);
    	    	
    	    	mecanumDrive.setLeftRightMotorOutputs((xBox1.getX() + xBox1.getY()) / 2, (xBox1.getX() + xBox1.getY()) / 2);
    	    	
    	    	mecanumDrive.setInvertedMotor(RobotDrive.MotorType.kFrontLeft, false);
    	    	mecanumDrive.setInvertedMotor(RobotDrive.MotorType.kRearRight, true);
    		}
    	}  	
    	
    	//Rotation
    	if(xBox1.getRawAxis(2) - xBox1.getRawAxis(3) > .2)
    	{
    		//Left
    		mecanumDrive.setLeftRightMotorOutputs(-(xBox1.getRawAxis(2) - xBox1.getRawAxis(3)),(xBox1.getRawAxis(2) - xBox1.getRawAxis(3)));
    	}
    	else if(xBox1.getRawAxis(2) - xBox1.getRawAxis(3) < -.2)
    	{
    		//Right
    		mecanumDrive.setLeftRightMotorOutputs((xBox1.getRawAxis(2) - xBox1.getRawAxis(3)),-(xBox1.getRawAxis(2) - xBox1.getRawAxis(3)));
    	}
    }
}
