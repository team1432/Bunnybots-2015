/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.SimpleRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Victor;

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
    //Compressor compressor = new Compressor(1, 1);
    Victor launchwheels = new Victor(1);
    Jaguar leftwheel, rightwheel, centerwheel;
    RobotDrive drive;
    //this runs once

    /**
     *this runs every time robot enters autonomous
     */
    public void autonomous() {
	while (isEnabled() && isAutonomous()){
	    drive(3);
	    drive.arcadeDrive(.5,0);
	}
    }
    /*
    *1=forward
    *2=right
    *3=back
    *4=left
    */
    public void drive(int direction){
	updateir();
	while (!back && !line){
	    if (direction == 1){
		drive.arcadeDrive(.5,0);
	    }
	    if (direction == 3){
		drive.arcadeDrive(-.5,0);
	    }
	    if (direction == 2){
		drive.arcadeDrive(0,0);
		centerwheel.set(.5);
	    }
	    if (direction == 4){
		drive.arcadeDrive(0,0);
		centerwheel.set(-.5);
	    }
	}
	if (line){
	    int count;
	    while (count < 1000){
		drive.arcadeDrive(-.5);
	    }
	}
    }
    /*
    *
    */
    public void updateir(){
	/*check left
	check right
	check back
	check line*/
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
//	compressor.start();
	System.out.println("started compressor");
	//Define button
	boolean a = controller.getRawButton(1);
	boolean b = controller.getRawButton(2);
	double strife = controller.getRawAxis(2);
	//Set launcher to reverse
	launcherreverse();
	System.out.println("Ready to launch");
	while (isOperatorControl() && isEnabled()){
	    drive.arcadeDrive(controller, 1, controller, 4);
	    strife = controller.getRawAxis(2);
	    centerwheel.set(strife);
	    a = controller.getRawButton(1);
	    b = controller.getRawButton(2);
	    Timer.delay(.01);
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
	/*
	while (isOperatorControl() && isEnabled()) {
	    //Wait for buttonpress
	    a = controller.getRawButton(1);
	    b = controller.getRawButton(2);
	    
	    

	}*/
    }

    /**
     * This function is called once each time the robot enters test mode.
     */
    public void test() {

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
     * autolaunch sends launcher forward, turns motors on, and then off, and reverse
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
    public void setupdrive(){
	leftwheel = new Jaguar(3);
	centerwheel = new Jaguar(4);
	rightwheel = new Jaguar(5);
	drive = new RobotDrive(leftwheel, rightwheel);
    }
    
}
