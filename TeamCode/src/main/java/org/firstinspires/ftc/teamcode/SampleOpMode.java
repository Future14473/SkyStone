package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.teamcode.robot.Wheels;
import org.firstinspires.ftc.teamcode.system.BotSystem;
import org.firstinspires.ftc.teamcode.system.DependsOn;
import org.firstinspires.ftc.teamcode.system.Element;
import org.firstinspires.ftc.teamcode.system.LinearElement;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

@SuppressWarnings("unchecked")
public class SampleOpMode extends BotSystemsOpMode {
	public SampleOpMode() {
		super(new DependsOn(Wheels.class));
	}
	
	@NotNull
	@Override
	protected List<Element> additionalElements() {
		return Collections.singletonList(new Additional());
	}
	
	private class Additional extends LinearElement {
		@Override
		protected void moreInit(@NotNull BotSystem botSystem) {
		}
		
		@Override
		protected void runElement() throws InterruptedException {
			telemetry.addLine("Hello sir");
			waitForStart();
			while (opModeIsActive()) {
				telemetry.addLine("Im running!!!");
				telemetry.update();
				sleep(200);
			}
			requestOpModeStop();
		}
	}
}
