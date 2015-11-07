/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.SimpleRobot;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Victor;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the SimpleRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class RobotTemplate extends SimpleRobot {

    //This function is called once each time the robot enters autonomous mode.
    Joystick controller = new Joystick(1);
    DoubleSolenoid launcher = new DoubleSolenoid(2, 1);
    Compressor compressor = new Compressor(1, 1);
    Victor launchwheels = new Victor(1);

    //this runs once
    public void autonomous() {

	/*launcher.set(DoubleSolenoid.Value.kOff);
	 //s1.set(false);
	 //s2.set(false);
	 System.out.println("turned off solenoid");
	 compressor.set(Relay.Value.kForward);
	 System.out.println("turned on relay");
	 Timer.delay(1);
	 while(!input.get()){}
	 //        relay.set(Relay.Value.kOff);
	 System.out.println("turned off relay");
	 Timer.delay(1);
	 while(input.get()){}
	 System.out.println("Button no longer pressed");
	 Timer.delay(1);
	 while(!input.get()){}
	 System.out.println("Button was pressed2nd time ");
	 //doublesolenoid
	 //s1.set(true);
	 System.out.println("Solenoid set forward");
	 Timer.delay(1);
	 while(input.get()){}
	 while(!input.get()){}
	 //s1.set(false);
	 //s2.set(true);
	 System.out.println("button pressed 3 and solenoid set to reverse");
	 Timer.delay(1);
	 while(input.get()){}
	 while(!input.get()){}
	 //s2.set(true);
	 //s1.set(true);
	 System.out.println("pressed 4, now both on");

	 Timer.delay(1);
        
	 //doublesolenoid.set(DoubleSolenoid.Value.kReverse);
	 while(input.get()){}
	 while(!input.get()){}
	 //s2.set(false);
	 //s1.set(false);
	 compressor.set(Relay.Value.kOff);
	 System.out.println("pressed 4, now turned off");*/
//while(true){
            /*solenoid.set(true);
	 Timer.delay(.1);
	 solenoid.set(false);
	 Timer.delay(.1);*/
            //buttonvalue = joystick.getRawButton(1);
	//System.out.println(buttonvalue);
	//value = joystick.getRawAxis(2);
	//Timer.delay(.1);
	// if(input.get()){
	//   relay.set(Relay.Value.kForward);
	//}
	//else(){
	//    relay.set(Relay.Value.kOff);
	//}
            /*relay.set(Relay.Value.kForward);
	 Timer.delay(.1);
	 relay.set(Relay.Value.kOff);
	 Timer.delay(.1);
	 relay.set(Relay.Value.kReverse);
	 Timer.delay(.1);
	 relay.set(Relay.Value.kOn);*/
	//}
    }

    /**
     * This function is called once each time the robot enters operator control.
     */
    public void teleoptInit(){
	System.out.println("teleop started");
    }
    public void operatorControl() {
	compressor.start();
	System.out.println("started compressor");
	//Define button
	boolean a = controller.getRawButton(1);
	boolean b = controller.getRawButton(2);

	//Set launcher to reverse
	launcher.set(DoubleSolenoid.Value.kReverse);
	System.out.println("Ready to launch");
	while (isOperatorControl() && isEnabled()) {
	    Timer.delay(.2);
	    //Wait for buttonpress
	    a = controller.getRawButton(1);
	    b = controller.getRawButton(2);
	    while (!a && !b) {
		Timer.delay(.1);
		a = controller.getRawButton(1);
		b = controller.getRawButton(2);
	    }
	    if (a) {
		System.out.println("A pressed");
		//Launch
		launchwheels.set(0.5);
		Timer.delay(0.2);
		launcher.set(DoubleSolenoid.Value.kForward);
		System.out.println("Launching");
		//Wait 1 second, then reverse
		Timer.delay(1);
		launchwheels.set(0);
		System.out.println("Re-setting launcher");
		launcher.set(DoubleSolenoid.Value.kReverse);
	    }
	    if (b) {
		System.out.println("B pressed");
		if (launcher.get() == DoubleSolenoid.Value.kReverse) {
		    launcher.set(DoubleSolenoid.Value.kForward);
		    System.out.println("forward");
		} else {
		    //Timer.delay(.1);
		    launcher.set(DoubleSolenoid.Value.kReverse);
		    System.out.println("reverse");
		}
	    }

	}
    }
    /**
     * This function is called once each time the robot enters test mode.
     */
    public void test() {

    }
}