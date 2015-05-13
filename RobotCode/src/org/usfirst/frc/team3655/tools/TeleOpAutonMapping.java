package org.usfirst.frc.team3655.tools;

import edu.wpi.first.wpilibj.Joystick;

/**
 * Autonmous mapping from tele op driving system
 * @author G. Stewart
 * @version 5/13/2015
 */

public class TeleOpAutonMapping 
{
	Joystick joystick;
	
	/**
	 * Base constructor that accepts what joystick to monitor
	 * @param joystick what joystick object to monitor
	 */
	public TeleOpAutonMapping(Joystick joystick)
	{
		this.joystick = joystick;
	}	
	
	public void startMapping()
	{
		
	}
}
