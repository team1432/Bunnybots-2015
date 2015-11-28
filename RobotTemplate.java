/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package edu.wpi.first.wpilibj.templates;

import com.sun.squawk.VM;
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
    boolean GotToLine = false;
    long starttime;

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
     	//if nothing in front and not at the line or already passed the line
		if (!back.get() && !line.get() && !GotToLine) {
			centerwheel.set(0);
			leftwheel.set(-.5);
			rightwheel.set(-.5);
		} else if (back.get()) {
			//if right open only
			if (left.get() && !right.get()) {
				//drive right
				centerwheel.set(.5);
				leftwheel.set(0);
				rightwheel.set(0);
			}
			//if left open only
			if (right.get() && !left.get()) {
				//drive left
				centerwheel.set(-.5);
				leftwheel.set(0);
				rightwheel.set(0);
			}
			//if both open
			if (!right.get() && !left.get()) {
				//drive left
				centerwheel.set(-.5);
				leftwheel.set(0);
				rightwheel.set(0);
		    }
		}
		//if we reach the line, update that vairable.
		else if (line.get()) {
			GotToLine = true;
		}
		//if we have already gotten to the line
		else if (GotToLine == true) {
			starttime = VM.getTimeMillis();
			//go a bit further
			while ((VM.getTimeMillis() - starttime) < 500){
				centerwheel.set(0);
				leftwheel.set(-.5);
				rightwheel.set(-.5);
			}
			//go forward
			starttime = VM.getTimeMillis();
			while ((VM.getTimeMillis() - starttime) < 1000){
				centerwheel.set(0);
				leftwheel.set(.5);
				rightwheel.set(.5);
			}
		}
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

    /**
     * sets shooter on solenoid 2,1 forward
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
