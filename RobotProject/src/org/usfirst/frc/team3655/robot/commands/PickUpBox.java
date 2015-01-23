package org.usfirst.frc.team3655.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Group Command to pick up a box
 * @author G. Stewart
 * @version 1/23/2015
 */
public class PickUpBox extends CommandGroup 
{   
    public  PickUpBox() 
    {
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.
    	addSequential(new PullKickers());
    	addSequential(new LowerElevator());
    	addSequential(new PushKickers());
    	addSequential(new RaiseElevator());

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
