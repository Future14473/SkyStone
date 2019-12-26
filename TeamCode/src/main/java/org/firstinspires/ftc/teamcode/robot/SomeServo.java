package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.system.BotSystem;
import org.jetbrains.annotations.NotNull;

public class SomeServo extends HardwareMapElement {
	public Servo servo = null;
	
	@Override
	protected void moreInit(@NotNull BotSystem botSystem) {
		servo = hardwareMap.get(Servo.class, "SomeServo");
	}
}
