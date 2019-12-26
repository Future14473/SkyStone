package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.teamcode.system.AbstractElement;
import org.firstinspires.ftc.teamcode.system.BotSystem;
import org.firstinspires.ftc.teamcode.system.Element;
import org.firstinspires.ftc.teamcode.system.OpModeElement;
import org.jetbrains.annotations.NotNull;

import static org.firstinspires.ftc.teamcode.Util.varargPlus;

@SuppressWarnings("unchecked")
public abstract class HardwareMapElement extends AbstractElement {
	protected OpMode opMode = null;
	protected HardwareMap hardwareMap = null;
	
	public HardwareMapElement(@NotNull Class<? extends Element>... dependsOn) {
		super(varargPlus(dependsOn, OpModeElement.class));
	}
	
	public HardwareMapElement() {
		super(OpModeElement.class);
	}
	
	@Override
	public void init(@NotNull BotSystem botSystem) {
		opMode = botSystem.get(OpModeElement.class).getOpMode();
		hardwareMap = opMode.hardwareMap;
		moreInit(botSystem);
	}
	
	protected abstract void moreInit(@NotNull BotSystem botSystem);
}
