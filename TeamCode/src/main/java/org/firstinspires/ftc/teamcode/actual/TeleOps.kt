package org.firstinspires.ftc.teamcode.actual

import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.lib.BotSystemsOnly
import org.firstinspires.ftc.teamcode.lib.system.BotSystems

@TeleOp
class DriverOneOnly : BotSystemsOnly(BotSystems.TeleOp1)

@TeleOp
class DriverTwoOnly : BotSystemsOnly(BotSystems.TeleOp2)

@TeleOp(name = "The TeleOp")
class TheTeleOp : BotSystemsOnly(BotSystems.AllTeleOp)
