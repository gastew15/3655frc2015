package org.usfirst.frc.team3655.robot;

import org.usfirst.frc.team3655.robot.commands.PickUpBox;
import org.usfirst.frc.team3655.robot.commands.PutDownBox;

import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.Joystick;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI 
{
    //// CREATING BUTTONS
    // One type of button is a joystick button which is any button on a joystick.
    // You create one by telling it which joystick it's on and which button
    // number it is.
     private Joystick xBox1 = new Joystick(1);
     private Button xBox1Button1 = new JoystickButton(xBox1, 1);
     private Button xBox1Button2 = new JoystickButton(xBox1, 2);
     
     private final double xBox1XAxisDeadZone = .05;
     private final double xBox1YAxisDeadZone = .05;
     private final double xBox1ZAxisDeadZone = .05;
    
    // There are a few additional built in buttons you can use. Additionally,
    // by subclassing Button you can create custom triggers and bind those to
    // commands the same as any other Button.
    
    //// TRIGGERING COMMANDS WITH BUTTONS
    // Once you have a button, it's trivial to bind it to a button in one of
    // three ways:
    
    // Start the command when the button is pressed and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenPressed(new ExampleCommand());
     public void teleOpJoyStick()
     {
    	 xBox1Button1.whenPressed(new PickUpBox());
    	 xBox1Button2.whenPressed(new PutDownBox());
     }
    
    // Run the command while the button is being held down and interrupt it once
    // the button is released.
    // button.whileHeld(new ExampleCommand());
    
    // Start the command when the button is released  and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenReleased(new ExampleCommand());
     
     //Returns the X axis data only if it is outside of the deadzone
     public double getXBox1X()
     {
    	//Left JoyStick X Axis
    	 if(xBox1.getRawAxis(1) > xBox1XAxisDeadZone || xBox1.getRawAxis(1) < -xBox1XAxisDeadZone) {
    		 return xBox1.getRawAxis(1);
    	 }
    	 else {
    		 return 0.0;
    	 }
     }
     
     public double getXBox1Y()
     {
    	//Left JoyStick Y Axis
    	 if(xBox1.getRawAxis(2) > xBox1YAxisDeadZone || xBox1.getRawAxis(2) < -xBox1YAxisDeadZone) {
    		 return xBox1.getRawAxis(2);
    	 }
    	 else {
    		 return 0.0;
    	 }
     }
     
     public double getXBox1Z()
     {
    	//Right JoyStick X Axis
    	 if(xBox1.getRawAxis(4) > xBox1ZAxisDeadZone || xBox1.getRawAxis(4) < -xBox1ZAxisDeadZone) {
    		 return xBox1.getRawAxis(4);
    	 }
    	 else {
    		 return 0.0;
    	 }
     }
     
     public double getRawXBox1X()
     {
    	 return xBox1.getRawAxis(1); //Left JoyStick X Axis
     }
     
     public double getRawXBox1Y()
     {
    	 return xBox1.getRawAxis(2); //Left JoyStick Y Axis
     }
     
     public double getRawXBox1Z()
     {
    	 return xBox1.getRawAxis(4); //Right JoyStick X Axis
     }
}

