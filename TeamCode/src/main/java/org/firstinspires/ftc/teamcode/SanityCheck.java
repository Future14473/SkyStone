package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.teamcode.robot.Wheels;
import org.firstinspires.ftc.teamcode.system.BotSystem;
import org.firstinspires.ftc.teamcode.system.DependsOn;
import org.firstinspires.ftc.teamcode.system.Element;
import org.firstinspires.ftc.teamcode.system.LinearRunnerElement;
import org.jetbrains.annotations.NotNull;

import java.util.*;

@SuppressWarnings("unchecked")
public class SanityCheck extends BotSystemsOpMode {
	
	@NotNull
	@Override
	protected List<Element> getElements() {
		return Arrays.asList(new Additional(),new DependsOn(Wheels.class));
	}
	
	private class Additional extends LinearRunnerElement {
		private Wheels wheels = null;
		@Override
		protected void moreInit(@NotNull BotSystem botSystem) {
			wheels = botSystem.get(Wheels.class);
		}
		
		@Override
		protected void runElement() {
			telemetry.addLine("Hello sir");
			waitForStart();
			while (opModeIsActive()) {
				telemetry.addLine("Im running!!!");
				telemetry.update();
				sleep(200);
			}
		}
	}
}
