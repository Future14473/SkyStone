package org.firstinspires.ftc.teamcode.lib.system

import com.qualcomm.robotcore.hardware.Gamepad
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.isActive


//additional teleOp. This is on the top on purpose.
private fun TeleOp2.additional() {
    val intakeSignal = (gamepad.right_trigger - gamepad.left_trigger) / 2.0
    intake.power = intakeSignal + additionalIntakePower
}


@UseExperimental(ExperimentalCoroutinesApi::class)
class TeleOp2(system: RobotSystem) : ArmStateScope(system) {

    var state: ArmState = ArmState.Ready

    //controls
    val gamepad: Gamepad = opMode.gamepad2
    private val buttons = Buttons(gamepad)

    val liftSignal get() = -gamepad.right_stick_y
    val armSignal get() = -gamepad.left_stick_x

    val grabSignal get() = buttons.right_bumper.isClicked
    val releaseSignal get() = buttons.left_bumper.isClicked
    val flickSignal get() = buttons.dpad_up.isClicked
    val unFlickSignal get() = buttons.dpad_up.isReleased

    var isFancy = false


    val toFancySignal get() = buttons.a.isClicked
    val toOldSchoolSignal get() = buttons.b.isClicked


    var additionalIntakePower = 0.0



    /**
     * Should run every loop.
     */
    override suspend fun update() {
        buttons.update()
        additional()
    }

    override suspend fun CoroutineScope.runSystem() {
        while (isActive) {
            state = with(state) { run() }
        }
    }
}
