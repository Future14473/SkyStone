package org.firstinspires.ftc.teamcode.mecanum;

import com.qualcomm.robotcore.util.Range;

public class Calculations {
    /* y
       ^  right 2          right 1
       |
       |  left 2          left  1
       ~ -  - > x
     */

    static int right1;
    static int right2;

    static int left1;
    static int left2;

    //turning variables
    static double radius = 16.97; //todo real radius
    static double wheelCircum = 3.5; //todo real cirumfrence
    static double encodersPerRotation = 386;

    public static void updateEncoders(double forward, double strafe, double turn) {

        // convert inches to encoders
        turn = convertTurnToInches(turn);
        turn = convertInchesToEncoders(turn);

        forward = convertInchesToEncoders(forward);

        strafe = convertInchesToEncoders(strafe);

        right1 = (int) (forward - strafe - turn);
        right2 = (int) (forward + strafe + turn);

        left1 = (int) (forward + strafe - turn);
        left2 = (int) (forward - strafe + turn);
    }

    public static double averagePower(double power1, double power2){
        // put the powers in the range of motor power
        power1 = Range.clip(power1, -1.0,1.0);
        power2 = Range.clip(power2, -1.0,1.0);

        double average = (power1 + power2)/2;
        return average;
    }

    public static double convertInchesToEncoders(double inches) {
        double rotations = inches / wheelCircum;
        double encoders = rotations * encodersPerRotation;
        return encoders;
    }

    public static double convertTurnToInches(double turn) {
        turn = Math.toRadians(turn);
        return turn * radius;
    }
}
