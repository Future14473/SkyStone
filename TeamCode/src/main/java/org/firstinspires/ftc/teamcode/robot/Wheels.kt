package org.firstinspires.ftc.teamcode.robot

import com.qualcomm.robotcore.hardware.DcMotorEx
import org.firstinspires.ftc.teamcode.system.BotSystem

class Wheels : HardwareMapElement() {
    var wheels: List<DcMotorEx>? = null
    override fun moreInit(botSystem: BotSystem) {
        wheels = wheelNames.map {
            hardwareMap.get(DcMotorEx::class.java, it)
        }.toList()
    }

    companion object {
        private val wheelNames =
            listOf("FrontLeft", "FrontRight", "BackLeft", "BackRight")
    }
}
