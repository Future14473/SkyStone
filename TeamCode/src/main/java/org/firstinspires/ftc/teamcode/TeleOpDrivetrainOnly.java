package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.mecanum.Calculations;
import org.firstinspires.ftc.teamcode.mecanum.Drivetrain;

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

@Autonomous(name="DrivetrainOnly", group="Iterative Opmode")
//@Disabled
public class TeleOpDrivetrainOnly extends OpMode
{
    private Drivetrain MecanumDrive;


    private ElapsedTime runtime = new ElapsedTime();
    @Override
    public void init() {
        // make drivetrain
        MecanumDrive = new Drivetrain(hardwareMap);


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
      // make the strafe/forward code
        // make the  power respond to both translation and turnpower
        double translationPower = Calculations.averagePower(gamepad2.left_stick_x, gamepad2.left_stick_y);
        double turnPower = Calculations.averagePower(gamepad2.right_stick_x, gamepad2.right_stick_y);
        double outputPower = Calculations.averagePower(translationPower, turnPower);

        //only move if someone is pressing the joysticks
        if (outputPower != 0){
            MecanumDrive.setPower(outputPower);
        }


       telemetry.addData("Status", "Run Time: " + runtime.toString());
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {telemetry.addData("Ending Note", "Did it work?");
    }

}
