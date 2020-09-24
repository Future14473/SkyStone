package org.firstinspires.ftc.teamcode.mecanum;

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

    public static double convertInchesToEncoders(double inches) {
        double rotations = inches / wheelCircum;
        double encoders = rotations * encodersPerRotation;
        return encoders;
    }

    public static double convertTurnToInches(double turn) {
        turn = Math.toRadians(turn);
        return turn * radius;
    }

    public static int getMotor1() {
        return right1;
    }

    public static int getMotor2() {
        return right2;
    }

    public static int getMotor3() {
        return left2;
    }

    public static int getMotor4() {
        return left1;
    }
}
