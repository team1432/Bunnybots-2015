/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package edu.wpi.first.wpilibj.templates;

import com.sun.squawk.VM;
import edu.wpi.first.wpilibj.AnalogChannel;
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
    boolean left = false;
    boolean right = false;
    boolean back = false;
    boolean line = false;
    boolean GotToLine = false;
    AnalogChannel irLeft = new AnalogChannel(1);
    AnalogChannel irRight = new AnalogChannel(2);
    AnalogChannel irLine = new AnalogChannel(3);
    AnalogChannel irBack = new AnalogChannel(4);
    long starttime;
    int state = 0;
    double DriveForwardValue;
    double DriveRotateValue;
    double DriveSideValue;

    /**
     * this runs every time robot enters autonomous
     */
    public void updateIR() {
	if (45 > irRight.getValue() && irRight.getValue() > 299) {
	    right = false;
	} else {
	    right = true;
	}
	if (45 > irLeft.getValue() && irLeft.getValue() > 299) {
	    left = false;
	} else {
	    left = true;
	}
	if (45 > irBack.getValue() && irBack.getValue() > 299) {
	    back = false;
	} else {
	    back = true;
	}
	if (45 > irLine.getValue() && irLine.getValue() > 299) {
	    line = false;
	} else {
	    line = true;
	}
    }

    public void autonomous() {
	if (!back) {
	    state = 0;
	}
	while (isEnabled() && isAutonomous()) {
	    updateIR();
	    if (!back && left && !right && !GotToLine && state != 5) {
		state = 1;
	    } else if (back && right && !left && !GotToLine && state != 5) {
		state = 2;
	    } else if (back && !right && !left && !GotToLine && state != 5) {
		state = 2;
	    } else if (line && !back && state != 5) {
		state = 3;
	    } else if ((back && left && right && state != 5) || (back && GotToLine && state != 5)) {
		state = 4;
	    }
	    autodrive();
	}
    }

    /**
     * This is constantly called in autonomous state 0 is normal drive state 1
     * is drive right state 2 is drive left state 3 is line state 4 is all sides
     * are closed
     */
    public void autodrive() {
	if (state == 0) {
	    System.out.println("State is now 0");
	    if (!back) {
		//drive forward
		centerwheel.set(0);
		leftwheel.set(.5);
		rightwheel.set(-.5);
	    }
	} else if (state == 1) {
	    System.out.println("State is now 1");
	    if (back) {
		//drive right
		centerwheel.set(.5);
		leftwheel.set(0);
		rightwheel.set(0);
	    } else if (!back) {
		starttime = VM.getTimeMillis();
		while (VM.getTimeMillis() < (starttime + 1000)) {
		    centerwheel.set(.5);
		}
		updateIR();
		if (!back && !GotToLine) {
		    state = 0;
		} else if (!back && GotToLine) {
		    state = 3;
		}
	    }
	} else if (state == 2) {
	    System.out.println("State is now 2");
	    if (back) {
		//drive left
		centerwheel.set(-.5);
		leftwheel.set(0);
		rightwheel.set(0);
	    } else if (!back) {
		starttime = VM.getTimeMillis();
		while (VM.getTimeMillis() < (starttime + 1000)) {
		    centerwheel.set(-.5);
		}
		updateIR();
		if (!back && !GotToLine) {
		    state = 0;
		} else if (!back && GotToLine) {
		    state = 3;
		}
	    }
	} else if (state == 3) {
	    System.out.println("State is now 3");
	    starttime = VM.getTimeMillis();
	    //go a bit further
	    while ((VM.getTimeMillis() - starttime) < 500) {
		centerwheel.set(0);
		leftwheel.set(.5);
		rightwheel.set(-.5);
	    }
	    //go forward
	    starttime = VM.getTimeMillis();
	    while ((VM.getTimeMillis() - starttime) < 1000) {
		centerwheel.set(0);
		leftwheel.set(-.5);
		rightwheel.set(.5);
	    }
	    while ((VM.getTimeMillis() - starttime) < 1000) {
		centerwheel.set(0);
		leftwheel.set(-.5);
		rightwheel.set(.5);
	    }
	    state = 5;
	} else if (state == 4) {
	    System.out.println("State is now 4");
	    centerwheel.set(0);
	    leftwheel.set(0);
	    rightwheel.set(0);
	    updateIR();
	    if (!back && !GotToLine) {
		state = 0;
	    } else if (!back && GotToLine) {
		state = 3;
	    }
	} else if (state == 5) {
	    centerwheel.set(0);
	    leftwheel.set(0);
	    rightwheel.set(0);
	    System.out.println("State is now 5");
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
	driving();
	//Set launcher to reverse
	launcherreverse();
	System.out.println("Ready to launch");
	while (isOperatorControl() && isEnabled()) {
	    driving();
	    a = controller.getRawButton(1);
	    b = controller.getRawButton(2);
	    Timer.delay(.05);
	    if (a) {
		System.out.println("A pressed");
		autolaunch(1);
	    }
	    if (b) {
		while (b) {
		    Timer.delay(.01);
		    b = controller.getRawButton(2);
		    driving();
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

    public void driving() {
	DriveForwardValue = controller.getRawAxis(2);
	DriveRotateValue = controller.getRawAxis(4);
	DriveSideValue = -(controller.getRawAxis(1));
	drive.arcadeDrive(DriveForwardValue, DriveRotateValue, true);
	centerwheel.set(DriveSideValue);
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
     * Sends launcher forward, turns motors on, and then off, and reverse
     *
     * @param button is the integer of the button on the controller.
     */
    public void autolaunch(int button) {
	boolean a = controller.getRawButton(button);
	launchwheels.set(0.3);
	//Launch
	starttime = VM.getTimeMillis();
	drive.arcadeDrive(controller);
	while ((VM.getTimeMillis() - starttime) < 200) {
	    driving();
	}
	launcherforward();
	while (a) {
	    a = controller.getRawButton(button);
	    driving();
	    Timer.delay(0.1);
	}
	launchwheels.set(0);
	launcherreverse();
    }
}
