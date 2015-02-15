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
    public static int driveForwardLeftMotor = 2;
    public static int driveForwardRightMotor = 3;
    public static int driveRearLeftMotor = 0;
    public static int driveRearRightMotor = 1;
    public static int elevatorWinchMotor = 4;
    
    //DIO
	public static int rotarEncoderForwardLeftA = 0;
	public static int rotarEncoderForwardLeftB = 1;	
	public static int rotarEncoderForwardRightA = 4;
	public static int rotarEncoderForwardRightB = 5;	
	public static int rotarEncoderRearLeftA = 2;
	public static int rotarEncoderRearLeftB = 3;	
	public static int rotarEncoderRearRightA = 6;
	public static int rotarEncoderRearRightB = 7;
	
	//Analog
	public static int gyroInput = 0;
	
	//Solenoid
	public static int solenoidElevator1 = 0;
	public static int solenoidElevator2 = 1;
	public static int solenoidElevatorKicker1 = 2;
	public static int solenoidElevatorKicker2= 3;
}
