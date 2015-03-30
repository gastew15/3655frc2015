package org.usfirst.frc.team3655.robot;

import edu.wpi.first.wpilibj.Joystick;

/**
 * Autonmous mapping from tele op driving system
 * @author G. Stewart
 * @version 3/30/2015
 */

public class TeleOpAutonMapping 
{
	Joystick joystick;
	
	/**
	 * Base constructor that accepts what joystick to monitor
	 * @param joystick
	 */
	public TeleOpAutonMapping(Joystick joystick)
	{
		this.joystick = joystick;
	}	
	
	
}
