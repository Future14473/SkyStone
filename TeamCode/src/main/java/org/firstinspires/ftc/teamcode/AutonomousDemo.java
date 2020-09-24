package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.mecanum.Drivetrain;

import java.io.IOException;

/**
 * This file contains an example of an iterative (Non-Linear) "OpMode".
 * An OpMode is a 'program' that runs in either the autonomous or the teleop period of an FTC match.
 * The names of OpModes appear on the menu of the FTC Driver Station.
 * When an selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 *
 * This particular OpMode just executes a basic Tank Drive Teleop for a two wheeled robot
 * It includes all the skeletal structure that all iterative OpModes contain.
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Drive r Station OpMode list
 */

@Autonomous(name="AutonomousDemo", group="Iterative Opmode")
//@Disabled
public class AutonomousDemo extends OpMode
{
    private Drivetrain mecanumDrive;


    private ElapsedTime runtime = new ElapsedTime();
    @Override
    public void init() {
        // make drivetrain
        Drivetrain mecanumDrive = new Drivetrain(hardwareMap);

        // output message to drivers
        telemetry.addData("Status", "The Future is NOW!");
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {

    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {
        runtime.reset();
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {
        autonomousWheels.moveWheelsFeetPower(2);
        shadowCaster.moveLinearActuatorUp(1120);
        shadowCaster.uncoverLight();
        // DO CV DETECTION
        boolean detectedGerms = true; // change this with the real cv detection boolean
        try {
            odometryGraph.graphData(detectedGerms);
        } catch (IOException e) {
            e.printStackTrace();
        }
        shadowCaster.coverLight();
        shadowCaster.moveLinearActuatorDown(1120);

        autonomousWheels.turnAngle(90);
        telemetry.addData("Status", "Run Time: " + runtime.toString());
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {telemetry.addData("Ending Note", "Good Job Team!");
    }

}
