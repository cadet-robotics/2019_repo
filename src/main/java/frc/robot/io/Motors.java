package frc.robot.io;

import com.google.gson.JsonObject;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.VictorSP;
import frc.robot.config.ConfigHandlerInt;

import java.util.ArrayList;

/**
 * The legibility-orient rewrite of the motors class
 *
 * Later modified to extend ConfigHandlerInt
 *
 * @author Alex Pickering + Owen Avery
 */
public class Motors extends ConfigHandlerInt {
    //Configured motors record
    ArrayList<String> configuredMotors = new ArrayList<>();

    //Motor Objects
    public CANSparkMax frontLeftDrive,
            frontRightDrive,
            backLeftDrive,
            backRightDrive;

    public Talon leftElevator,
            rightElevator;

    public VictorSP leftClaw,
            rightClaw;


    boolean debug = true;

    @Override
    public boolean isDebug() {
        return debug;
    }

    /**
     * Gets the list of configured motors
     *
     * @return A list of configured motors
     */
    public ArrayList<String> getConfiguredMotors() {
        return configuredMotors;
    }

    public Motors(JsonObject configIn) {
        super(configIn, "pwm");
    }

    /**
     * Sets all motors to 0 for safety
     */
    public void resetAll() {
        frontLeftDrive.set(0);
        frontRightDrive.set(0);
        backLeftDrive.set(0);
        backRightDrive.set(0);

        leftElevator.set(0);
        rightElevator.set(0);

        leftClaw.set(0);
        rightClaw.set(0);
    }

    /**
     * Copied nearly wholesale from the old init method, initializes this class' objects
     *
     * @param k The name of the object
     * @param itemInt The index of the object in the pcm/pwm ports/digital io ports/etc.
     */
    @Override
    public void loadItem(String k, int itemInt) {
        switch(k) {
            case "front left":
                frontLeftDrive = new CANSparkMax(itemInt, MotorType.kBrushed);
                System.out.println("FLD: " + itemInt);
                break;

            case "front right":
                frontRightDrive = new CANSparkMax(itemInt, MotorType.kBrushed);
                System.out.println("FRD: " + itemInt);
                break;

            case "rear left":
                backLeftDrive = new CANSparkMax(itemInt, MotorType.kBrushed);
                System.out.println("BLD: " + itemInt);
                break;

            case "rear right":
                backRightDrive = new CANSparkMax(itemInt, MotorType.kBrushed);
                System.out.println("BRD: " + itemInt);
                break;

            case "left elevator":
                leftElevator = new Talon(itemInt);
                break;

            case "right elevator":
                rightElevator = new Talon(itemInt);
                break;

            case "left claw wheel":
                leftClaw = new VictorSP(itemInt);
                break;

            case "right claw wheel":
                rightClaw = new VictorSP(itemInt);
                break;

            default:
                System.err.println("Unrecognized motor: " + k);
                return;
        }
        configuredMotors.add(k);
    }

    @Override
    public void finalizeItems() {
    }

    @Override
    public void error() {
        //throw new RuntimeException("Failed to load motors");
    }
}