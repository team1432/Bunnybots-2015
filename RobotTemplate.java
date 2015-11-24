/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.SimpleRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.DigitalInput;
import java.util.Random;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the SimpleRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class RobotTemplate extends SimpleRobot {

    Joystick controller = new Joystick(1);
    DoubleSolenoid launcher = new DoubleSolenoid(2, 1);
    Compressor compressor = new Compressor(1, 1);
    Victor launchwheels = new Victor(1);
    Jaguar leftwheel, rightwheel, centerwheel;
    RobotDrive drive;
    DigitalInput left = new DigitalInput(2);
    DigitalInput right = new DigitalInput(3);
    DigitalInput back = new DigitalInput(4);
    DigitalInput line = new DigitalInput(5);

    /**
     * this runs every time robot enters autonomous
     */
    public void autonomous() {
	while (isEnabled() && isAutonomous()) {
	    autodrive();
	}
    }

    /**
     * This is constantly called in autonomous
     */
    public void autodrive() {
	if (!back.get() /*&& !line.get()*/) {
	    System.out.println("False");
	    centerwheel.set(0);
	    drive.arcadeDrive(.5, 0);
	} else if(back.get()) {
	    System.out.println("True");
	}
	/*else if (back.get()) {
	 if (left.get() && !right.get()) {
	 //drive right
	 centerwheel.set(.5);
	 drive.arcadeDrive(0, 0);
	 if (!back.get()) {
	 Timer.delay(.5);
	 centerwheel.set(0);
	 }
	 }
	 if (right.get() && !left.get()) {
	 while (!back.get()) {
	 //drive right
	 centerwheel.set(-.5);
	 drive.arcadeDrive(0, 0);
	 }
	 Timer.delay(.5);
	 centerwheel.set(0);
	 }
	 if (!right.get() && !left.get()) {
	 Random random = new Random();
	 int n = random.nextInt(1);
	 if (n == 1) {
	 while (!back.get()) {
	 centerwheel.set(-.5);
	 drive.arcadeDrive(0, 0);
	 }

	 } else {
	 while (!back.get()) {
	 centerwheel.set(.5);
	 drive.arcadeDrive(0, 0);
	 }
	 }
	 Timer.delay(.5);
	 centerwheel.set(0);
	 }
	 } else if (line.get() && !back.get()) {
	 long millis = System.currentTimeMillis() % 1000;
	 while (System.currentTimeMillis() % 1000 - millis < 500 && !back.get()) {
	 drive.arcadeDrive(-.5, 0);
	 }
	 millis = System.currentTimeMillis();
	 while (System.currentTimeMillis() - millis < 5000) {
	 drive.arcadeDrive(.5, 0);
	 }
	 }*/
    }

    /**
     * This function is called once each time the robot enters operator control.
     */
    public void robotInit() {
	System.out.println("Ready to start");
	setupdrive();
    }

    public void disabled() {
	System.out.println("Robot Disabled");
    }

    public void operatorControl() {
	System.out.println("Robot Enabled");
	compressor.start();
	System.out.println("started compressor");
	//Define button
	boolean a = controller.getRawButton(1);
	boolean b = controller.getRawButton(2);
	double strafe = controller.getRawAxis(1);
	//Set launcher to reverse
	launcherreverse();
	System.out.println("Ready to launch");
	while (isOperatorControl() && isEnabled()) {
	    drive.arcadeDrive(controller, 2, controller, 4);
	    strafe = -(controller.getRawAxis(1));
	    centerwheel.set(strafe);
	    a = controller.getRawButton(1);
	    b = controller.getRawButton(2);
	    Timer.delay(.05);
	    if (a) {
		System.out.println("A pressed");
		autolaunch(1);
	    }
	    if (b) {
		while (b) {
		    Timer.delay(.1);
		    b = controller.getRawButton(2);
		}
		System.out.println("B pressed");
		if (launcher.get() == DoubleSolenoid.Value.kReverse) {
		    launcherforward();
		} else {
		    launcherreverse();
		}
	    }
	}
    }

    /**
     * This function is called once each time the robot enters test mode.
     */
    public void test() {

    }

    public void setupdrive() {
	leftwheel = new Jaguar(3);
	centerwheel = new Jaguar(4);
	rightwheel = new Jaguar(5);
	drive = new RobotDrive(leftwheel, rightwheel);
    }

    /*
     This sets the shooter on solenoid 2,1 forward
     */
    public void launcherforward() {
	launcher.set(DoubleSolenoid.Value.kForward);
	System.out.println("forward");
    }

    /**
     * sets shooter on solenoid 2,1 reverse
     */
    public void launcherreverse() {
	launcher.set(DoubleSolenoid.Value.kReverse);
	System.out.println("reverse");
    }

    /**
     * autolaunch sends launcher forward, turns motors on, and then off, and
     * reverse
     *
     * @param button is the integer of the button on the controller.
     */
    public void autolaunch(int button) {
	boolean a = controller.getRawButton(button);
	launchwheels.set(0.3);
	//Launch
	Timer.delay(0.2);
	launcherforward();
	while (a) {
	    a = controller.getRawButton(button);
	    Timer.delay(0.1);
	}
	launchwheels.set(0);
	launcherreverse();
    }

}
