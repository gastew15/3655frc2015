package org.usfirst.frc.team3655.robot;
/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap 
{	
	//PWM
    public static int driveForwardLeftMotor = 1;
    public static int driveForwardRightMotor = 3;
    public static int driveRearLeftMotor = 2;
    public static int driveRearRightMotor = 4;
    
    //DIO
	public static int rotarEncoderForwardLeftA = 1;
	public static int rotarEncoderForwardLeftB = 2;	
	public static int rotarEncoderForwardRightA = 5;
	public static int rotarEncoderForwardRightB = 6;	
	public static int rotarEncoderRearLeftA = 3;
	public static int rotarEncoderRearLeftB = 4;	
	public static int rotarEncoderRearRightA = 7;
	public static int rotarEncoderRearRightB = 8;
	public static int gyroInput = 9;
	
	//Solenoid
	public static int solenoidElevatorLeft1 = 0;
	public static int solenoidElevatorLeft2 = 1;
	public static int solenoidElevatorRight1 = 2;
	public static int solenoidElevatorRight2 = 3;
	public static int solenoidElevatorPushLeft = 6;
	public static int solenoidElevatorPushRight = 7;
}
