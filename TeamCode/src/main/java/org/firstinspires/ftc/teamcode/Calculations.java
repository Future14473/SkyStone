package org.firstinspires.ftc.teamcode;

public class Calculations {
    /* y
       ^  2          1
       |
       |  3          4
       ~ -  - > x
     */

    static int motor1_encoders;
    static int motor2_encoders;
    static int motor3_encoders;
    static int motor4_encoders;

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

        motor1_encoders = (int) (forward - strafe - turn);
        motor2_encoders = (int) (forward + strafe + turn);
        motor3_encoders = (int) (forward - strafe + turn);
        motor4_encoders = (int) (forward + strafe - turn);
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
        return motor1_encoders;
    }

    public static int getMotor2() {
        return motor2_encoders;
    }

    public static int getMotor3() {
        return motor3_encoders;
    }

    public static int getMotor4() {
        return motor4_encoders;
    }
}
