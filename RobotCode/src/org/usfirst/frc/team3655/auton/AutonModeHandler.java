package org.usfirst.frc.team3655.auton;

import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * IO Auton Mode Handler
 * @author G. Stewart
 * @version 3/30/2015
 */

public class AutonModeHandler 
{
	static int autonMode = 0;
	
	/**
	 * Default constructor, intilizes autonMode with save file data
	 */
	public AutonModeHandler()
	{
		loadData();
	}
	
	/**
	 * Saves Auton Mode to local file
	 * @return void
	 */
	public void saveData()
	{
		try
		{
			PrintWriter outFile = new PrintWriter(new File("autonMode.dat"));
			outFile.println(AutonModeHandler.getAutonMode());
			outFile.close();
		}
		catch(Exception x)
		{
			System.out.println("Auton File Write Failed: " + x);
		}
	}
	
	/**
	 * Loads Auton Mode from local file
	 * @return void
	 */
	public void loadData()
	{
		try
    	{
    		Scanner inputFile = new Scanner(new File("autonMode.dat"));
    		autonMode = Integer.parseInt(inputFile.next());
    		inputFile.close();
    	}
    	catch(Exception e)
    	{
    		System.out.println("Auton File Load Failed - Attempting file creation: " + e);
    		try
    		{
    			PrintWriter outFile = new PrintWriter(new File("autonMode.dat"));
    			outFile.println(autonMode);
    			outFile.close();
    		}
    		catch(Exception x)
    		{
    			System.out.println("Auton File Creation Failed: " + x);
    		}
    	}
	}
	
	/**
	 * Returns current autonMode
	 * @return autonMode
	 */
	public static int getAutonMode()
	{
		return autonMode;
	}
	
	/**
	 * Sets current autonMode
	 * @param autonMode
	 * @return void
	 */
	@SuppressWarnings("static-access")
	public void setAutonMode(int autonMode)
	{
		this.autonMode = autonMode;
	}
}
