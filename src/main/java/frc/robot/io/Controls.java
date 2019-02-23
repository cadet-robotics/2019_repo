package frc.robot.io;

import com.google.gson.JsonObject;
import edu.wpi.first.wpilibj.Joystick;
import frc.robot.config.ConfigHandlerInt;

import java.util.ArrayList;

/**
 * Contains controls objects and handles loading them from config
 * <p>
 * Later modified to make it extend ConfigHandlerInt
 *
 * @author Alex Pickering, Owen Avery
 */
public class Controls extends ConfigHandlerInt {
    //Config'd controls record
    ArrayList<String> configuredControls = new ArrayList<>();

    public Controls(JsonObject configIn) {
        super(configIn, "controls");
        finishInit();
        mainJoystick = new Joystick(mainJoystickPort);
    }

    @Override
    public void error() {
    }

    //Controls objects
    Joystick mainJoystick;

    //Configured control ports and such
    //Axes
    int mainJoystickPort = 0,
            xAxis = 0,
            yAxis = 1,
            zAxis = 2,
            throttleAxis = 3;

    //Buttons
    int autoLockButtonPort = 5,
            elevatorUp = 5,
            elevatorDown = 3,
            getBall = 1,
            ejectBall = 2,
            toggleClaw = 4,
            toggleType = 9; //TODO: TEMPORARY
    int[] elevatorPos = new int[6];

    boolean debug = false;

    @Override
    public boolean isDebug() {
        return debug;
    }

    //Getters

    /**
     * Gets the X Axis control
     *
     * @return The X Axis
     */
    public double getXAxis() {
        return mainJoystick.getRawAxis(xAxis);
    }

    /**
     * Gets the Y Axis control
     *
     * @return The Y Axis
     */
    public double getYAxis() {
        return mainJoystick.getRawAxis(yAxis);
    }

    /**
     * Gets the Z Axis control
     *
     * @return The Z Axis
     */
    public double getZAxis() {
        return mainJoystick.getRawAxis(zAxis);
    }

    /**
     * Gets the throttle axis control
     *
     * @return The throttle axis
     */
    public double getThrottleAxis() {
        return mainJoystick.getRawAxis(throttleAxis);
    }

    /**
     * Gets the manual raise elevator button
     *
     * @return Elevator Up button state
     */
    public boolean getElevatorUp() {
        return mainJoystick.getRawButton(elevatorUp);
    }

    /**
     * Gets the manual lower elevator button
     *
     * @return Elevator Down button state
     */
    public boolean getElevatorDown() {
        return mainJoystick.getRawButton(elevatorDown);
    }

    /**
     * Gets the get ball button
     *
     * @return Get Ball button state
     */
    public boolean getGetBall() {
        return mainJoystick.getRawButton(getBall);
    }

    /**
     * Gets the eject ball/get hatch button
     *
     * @return Eject Ball button state
     */
    public boolean getEjectBall() {
        return mainJoystick.getRawButton(ejectBall);
    }

    /**
     * Gets the toggle claw button
     *
     * @return Toggle Claw button state
     */
    public boolean getToggleClaw() {
        return mainJoystick.getRawButton(toggleClaw);
    }

    /**
     * Gets the type toggle button
     * TODO: TEMPORARY
     *
     * @return Toggle Type button state
     */
    public boolean getToggleType() {
        return mainJoystick.getRawButton(toggleType);
    }

    /**
     * Gets an elevator position button
     *
     * @param pos The position to check
     * @return Whether or not the position's button is pressed
     */
    public boolean getElevatorButton(int pos) {
        return mainJoystick.getRawButton(elevatorPos[pos]);
    }

    /**
     * Gets the Auto Lock Button state
     *
     * @return
     */
    public boolean isAutoLock() {
        return mainJoystick.getRawButton(autoLockButtonPort);
    }

    public ArrayList<String> getConfiguredControls() {
        return configuredControls;
    }

    @Override
    public void loadItem(String k, int itemInt) {
        switch (k) {
            case "main joystick":
                mainJoystickPort = itemInt;
                break;

            case "main joystick x-axis":
                xAxis = itemInt;
                break;

            case "main joystick y-axis":
                yAxis = itemInt;
                break;

            case "main joystick z-axis":
                zAxis = itemInt;
                break;

            case "main joystick throttle axis":
                throttleAxis = itemInt;
                break;

            case "main joystick auto-lock":
                autoLockButtonPort = itemInt;
                break;

            case "elevator hatch 1":
                elevatorPos[0] = itemInt;
                break;

            case "elevator ball 1":
                elevatorPos[1] = itemInt;
                break;

            case "elevator hatch 2":
                elevatorPos[2] = itemInt;
                break;

            case "elevator ball 2":
                elevatorPos[3] = itemInt;
                break;

            case "elevator hatch 3":
                elevatorPos[4] = itemInt;
                break;

            case "elevator ball 3":
                elevatorPos[5] = itemInt;
                break;

            case "elevator up":
                elevatorUp = itemInt;
                break;

            case "elevator down":
                elevatorDown = itemInt;
                break;

            case "toggle claw":
                toggleClaw = itemInt;
                break;

            case "get ball":
                getBall = itemInt;
                break;

            case "eject ball, get hatch":
                ejectBall = itemInt;
                break;

            case "toggle type": //TODO: TEMPORARY
                toggleType = itemInt;
                break;

            default:
                System.err.println("Unrecognized control: " + k);
                return;
        }
        configuredControls.add(k);
    }
}