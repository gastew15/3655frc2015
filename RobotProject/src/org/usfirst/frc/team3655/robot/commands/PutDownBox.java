package org.usfirst.frc.team3655.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Group Command to pick down a box
 * @author G. Stewart
 * @version 1/23/2015
 */
public class PutDownBox extends CommandGroup 
{  
    public  PutDownBox() 
    {
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.
    	addSequential(new LowerElevator());
    	addSequential(new PullKickers());
    	addSequential(new RaiseElevator());
    	addSequential(new PushKickers());

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    }
}
