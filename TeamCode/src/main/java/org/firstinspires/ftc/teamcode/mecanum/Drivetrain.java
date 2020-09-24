package org.firstinspires.ftc.teamcode.mecanum;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Drivetrain {
    DcMotor right1 = null;
    DcMotor left1 = null;
    DcMotor right2 = null;
    DcMotor left2 = null;

    public Drivetrain(HardwareMap hardwareMap){
        right1 = hardwareMap.get(DcMotor.class,"right1");
        left1 = hardwareMap.get(DcMotor.class,"left1");
        right2 = hardwareMap.get(DcMotor.class,"right2");
        left2 = hardwareMap.get(DcMotor.class,"left2");

        // some motors need to be reversed because they are mounted that way
        right1.setDirection(DcMotorSimple.Direction.FORWARD);
        right2.setDirection(DcMotorSimple.Direction.REVERSE);

        left1.setDirection(DcMotorSimple.Direction.FORWARD);
        left2.setDirection(DcMotorSimple.Direction.REVERSE);

    }

    public void move(double forward, double strafe, double turn){
        // get the desired encoder position for each motor
        Calculations.updateEncoders(forward, strafe, turn);

        int right1Position = findEncoderPosition(Calculations.right1, right1);
        int right2Position = findEncoderPosition(Calculations.right2, right2);
        int left1Position = findEncoderPosition(Calculations.left1, left1);
        int left2Position = findEncoderPosition(Calculations.left2, left1);

        // move the motors to correct position
        right1.setTargetPosition(right1Position);
        right2.setTargetPosition(right2Position);

        left1.setTargetPosition(left1Position);
        left2.setTargetPosition(left2Position);
    }

    public void setPower(double power){
        right1.setPower(power);
        right2.setPower(power);

        left1.setPower(power);
        left2.setPower(power);
    }

    public int findEncoderPosition(int encoders, DcMotor motor){
        int currentEncoderPosition = motor.getCurrentPosition();
        int desiredEncoderPosition = currentEncoderPosition + encoders;

        return desiredEncoderPosition;
    }

}
