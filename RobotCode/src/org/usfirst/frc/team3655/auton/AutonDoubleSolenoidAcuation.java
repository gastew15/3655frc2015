package org.usfirst.frc.team3655.auton;

import java.util.List;
import java.util.ArrayList;
import edu.wpi.first.wpilibj.DoubleSolenoid;

/**
 * Auton Class for single acuation Solenoid systems
 * @author G. Stewart
 * @version 3/31/2015
 */

public class AutonDoubleSolenoidAcuation extends AutonSingleAcuation
{
	List<DoubleSolenoid> solenoids;
	List<DoubleSolenoid.Value> values;
	
	/**
	 * Single solenoid constructor
	 * @param solenoid solenoid object to manipulate
	 * @param value what state to set the solenoid to
	 */
	public AutonDoubleSolenoidAcuation(DoubleSolenoid solenoid, DoubleSolenoid.Value value)
	{
		this.solenoids = new ArrayList<DoubleSolenoid>();
		this.solenoids.add(solenoid);
		this.values = new ArrayList<DoubleSolenoid.Value>();
		this.values.add(value);
	}
	
	/**
	 * Multi solenoid constructor
	 * @param solenoids solenoid objects to manipulate
	 * @param values what states to set the solenoids to
	 */
	public AutonDoubleSolenoidAcuation(List<DoubleSolenoid> solenoids, List<DoubleSolenoid.Value> values)
	{
		this.solenoids = solenoids;
		this.values = values;
	}
	
	@Override
	public void acuationTask() 
	{
		for(int i = 0; i < solenoids.size(); i++)
		{
			solenoids.get(i).set(values.get(i));
		}
	}
}
