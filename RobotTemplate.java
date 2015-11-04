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

    //DoubleSolenoid doublesolenoid = new DoubleSolenoid(1,2);
    Relay compressor = new Relay(1);
    //Solenoid s1 = new Solenoid(1);
    //Solenoid s2 = new Solenoid(2);
    DigitalInput input = new DigitalInput(1);
    DoubleSolenoid launcher = new DoubleSolenoid(1, 2);

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
    public void operatorControl() {
        //Define button
        boolean a = controller.getRawButton(1);
        //Turn on compressor and wait 5 seconds then turn off
        compressor.set(Relay.Value.kForward);
        System.out.println("Filling air tank");
        Timer.delay(5.0);
        compressor.set(Relay.Value.kOff);
        //Set launcher to reverse
        launcher.set(DoubleSolenoid.Value.kReverse);
        System.out.println("Ready to launch");
        while (true) {
            //Wait for buttonpress
            while (!a||!input.get()) {}
            //Launch
            launcher.set(DoubleSolenoid.Value.kForward);
            System.out.println("Launching");
            //Wait 3 seconds, then reverse
            Timer.delay(3);
            System.out.println("Re-setting launcher");
            launcher.set(DoubleSolenoid.Value.kReverse);
            //Turn on compressor for 5 seconds
            compressor.set(Relay.Value.kForward);
            System.out.println("Filling air tank");
            Timer.delay(5);
            compressor.set(Relay.Value.kOff);
            System.out.println("Ready to launch");
        }
    }

    /**
     * This function is called once each time the robot enters test mode.
     */
    public void test() {

    }
}
