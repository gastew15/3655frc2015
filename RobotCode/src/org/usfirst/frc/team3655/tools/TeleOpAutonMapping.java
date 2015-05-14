package org.usfirst.frc.team3655.tools;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.first.wpilibj.Joystick;

/**
 * Autonmous mapping from tele op driving system
 * @author G. Stewart
 * @version 5/13/2015
 */

public class TeleOpAutonMapping 
{
	private Joystick joystick;
	private boolean[] currentButtons;
	private boolean[] previousButtons;
	
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
		//Loop for running
			//Get buttons changed and as they do have the times down
		
		//End of loop set old joystick to current
	}
	
	private boolean[] getButtonStates(Joystick joystick)
	{
		boolean[] buttonStates = new boolean[joystick.getButtonCount()];
		for(int i = 0; i < buttonStates.length; i++)
		{
			buttonStates[i] = joystick.getRawButton(i);
		}
		return buttonStates;
	}
	
	private List<Integer> getButtonsChanged(boolean[] currentButtons, boolean[] previousButtons)
	{
		List<Integer> changed = new ArrayList<Integer>();
		
		for(int i = 0; i < currentButtons.length; i++)
		{
			if(currentButtons[i] != previousButtons[i])
				changed.add(i);
		}
		return changed;
	}
}
