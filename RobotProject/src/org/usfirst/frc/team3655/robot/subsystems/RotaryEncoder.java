package org.usfirst.frc.team3655.robot.subsystems;

import org.usfirst.frc.team3655.robot.RobotMap;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Rotary Encoders for drive base of robot
 * @author G. Stewart
 * @version 1/21/2015
 */

public class RotaryEncoder extends Subsystem {
    
	private Encoder encoderForwardLeft = new Encoder(RobotMap.rotarEncoderForwardLeftA, RobotMap.rotarEncoderForwardLeftB, true, EncodingType.k4X);
	private Encoder encoderRearLeft = new Encoder(RobotMap.rotarEncoderRearLeftA, RobotMap.rotarEncoderRearLeftB, true, EncodingType.k4X);
	private Encoder encoderForwardRight = new Encoder(RobotMap.rotarEncoderForwardRightA, RobotMap.rotarEncoderForwardRightB, true, EncodingType.k4X);
	private Encoder encoderRearRight = new Encoder(RobotMap.rotarEncoderRearRightA, RobotMap.rotarEncoderRearRightB, true, EncodingType.k4X);

    public void initDefaultCommand() 
    {
    	encoderForwardLeft.setMaxPeriod(.1);
        encoderForwardRight.setMaxPeriod(.1);
        encoderRearLeft.setMaxPeriod(.1);
        encoderRearRight.setMaxPeriod(.1);
        
        encoderForwardLeft.setMinRate(10);
        encoderForwardRight.setMinRate(10);
        encoderRearLeft.setMinRate(10);
        encoderRearRight.setMinRate(10);
        
        encoderForwardLeft.setDistancePerPulse(5);
        encoderForwardRight.setDistancePerPulse(5);
        encoderRearLeft.setDistancePerPulse(5);
        encoderRearRight.setDistancePerPulse(5);
        
        encoderForwardLeft.setReverseDirection(true);
        encoderForwardRight.setReverseDirection(true);
        encoderRearLeft.setReverseDirection(true);
        encoderRearRight.setReverseDirection(true);
        
        encoderForwardLeft.setSamplesToAverage(7);
        encoderForwardRight.setSamplesToAverage(7);
        encoderRearLeft.setSamplesToAverage(7);
        encoderRearRight.setSamplesToAverage(7);
        
        encoderForwardLeft.startLiveWindowMode();
        encoderForwardRight.startLiveWindowMode();
        encoderRearLeft.startLiveWindowMode();
        encoderRearRight.startLiveWindowMode();
    }
    
    public double[] getEncoderRates()
    {   	
    	return new double[]{encoderForwardLeft.getRate(), encoderRearLeft.getRate(), encoderForwardRight.getRate(), encoderRearRight.getRate()};
    }
    
    public double getEncoderRateAverage()
    {
    	return (encoderForwardLeft.getRate() + encoderForwardRight.getRate() + encoderRearLeft.getRate() + encoderRearRight.getRate()) / 4;
    }
    
    public void resetEncoders()
    {
    	encoderForwardLeft.reset();
        encoderForwardRight.reset();
        encoderRearLeft.reset();
        encoderRearRight.reset();
    }
    
    public void stopEncoders()
    {
    	encoderForwardLeft.stopLiveWindowMode();
        encoderForwardRight.stopLiveWindowMode();
        encoderRearLeft.stopLiveWindowMode();
        encoderRearRight.stopLiveWindowMode();
    }
}

