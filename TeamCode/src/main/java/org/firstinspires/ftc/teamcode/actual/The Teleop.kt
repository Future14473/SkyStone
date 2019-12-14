package org.firstinspires.ftc.teamcode.actual

import com.qualcomm.robotcore.eventloop.opmode.Disabled
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.lib.BotSystemsOpMode
import org.firstinspires.ftc.teamcode.lib.system.BotSystems

@TeleOp(name = "The TeleOp")
class TheTeleOp : BotSystemsOpMode(BotSystems.AllTeleOp)

@TeleOp(name = "Driver one only")
@Disabled
class DriverOneOnly : BotSystemsOpMode(BotSystems.TeleOp1)
