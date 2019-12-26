package org.firstinspires.ftc.teamcode.robot;

import android.annotation.SuppressLint;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import org.firstinspires.ftc.teamcode.system.BotSystem;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Wheels extends HardwareMapElement {
	
	private static List<String> wheelNames = Arrays.asList("FrontLeft", "FrontRight", "BackLeft", "BackRight");
	public List<DcMotorEx> wheels = null;
	
	@SuppressLint("NewApi")
	@Override
	protected void moreInit(@NotNull BotSystem botSystem) {
		wheels = wheelNames.stream()
				         .map(s -> hardwareMap.get(DcMotorEx.class, s))
				         .collect(Collectors.toList());
	}
}

