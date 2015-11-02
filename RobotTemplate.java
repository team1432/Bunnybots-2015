/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;


import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.SimpleRobot;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Relay;
/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the SimpleRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class RobotTemplate extends SimpleRobot {
    //This function is called once each time the robot enters autonomous mode.
    Joystick joystick = new Joystick(0);
    //Solenoid solenoid = new Solenoid(1);
    //DoubleSolenoid doublesolenoid = new DoubleSolenoid(1,2);
    //Relay relay = new Relay(1);
    public void autonomous() {
        while(true){
            /*solenoid.set(true);
            Timer.delay(2.0);
            solenoid.set(false);
            Timer.delay(2.0);*/
            boolean buttonvalue = joystick.getRawButton(1);
            System.out.print(buttonvalue);
            double value;
            value=joystick.getRawAxis(2);
            /*doublesolenoid.set(DoubleSolenoid.Value.kOff);
            Timer.delay(2.0);
            doublesolenoid.set(DoubleSolenoid.Value.kForward);
            Timer.delay(2.0);
            doublesolenoid.set(DoubleSolenoid.Value.kReverse);
            Timer.delay(2.0);*/
            /*relay.set(Relay.Value.kForward);
            Timer.delay(2.0);
            relay.set(Relay.Value.kOff);
            Timer.delay(2.0);
            relay.set(Relay.Value.kReverse);
            Timer.delay(2.0);
            relay.set(Relay.Value.kOn);*/
        }
    }

    /**
     * This function is called once each time the robot enters operator control.
     */
    public void operatorControl() {

    }

    /**
     * This function is called once each time the robot enters test mode.
     */
    public void test() {

    }
}
