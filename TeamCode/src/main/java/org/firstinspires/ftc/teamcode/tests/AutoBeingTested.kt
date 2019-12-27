package org.firstinspires.ftc.teamcode.tests

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import kotlinx.coroutines.*
import org.firstinspires.ftc.teamcode.lib.BotSystemsOpMode
import org.firstinspires.ftc.teamcode.lib.blueyay.SkystoneDetector
import org.firstinspires.ftc.teamcode.lib.bot.AutoDrive
import org.firstinspires.ftc.teamcode.lib.field.*
import org.firstinspires.ftc.teamcode.lib.system.AutoArmState
import org.firstinspires.ftc.teamcode.lib.system.BotSystems
import org.firstinspires.ftc.teamcode.lib.system.RobotSystem
import org.futurerobotics.jargon.math.Vector2d
import org.futurerobotics.jargon.math.convert.*
import org.futurerobotics.jargon.pathing.graph.WaypointConstraint
import org.futurerobotics.jargon.util.replaceIf


//right now: loading side
@Autonomous
@UseExperimental(ExperimentalCoroutinesApi::class)
open class AutoBeingTested(private val isFlipped: Boolean = false) : BotSystemsOpMode(BotSystems.Auto) {

    private val graph = getFieldGraph()
    private val tracker = GraphTracker(graph, "start loading", Side.Intake)
    private lateinit var drive: AutoDrive

    override suspend fun RobotSystem.onInit() {
        drive = autoDrive
        drive.resetInitialPose(startLoading)
        drive.isFlipped = isFlipped
    }

    private fun moveTo(name: String, startFacing: Side, endFacing: Side = startFacing): Job {
        return drive.followPath(tracker.pathTo(name, startFacing, endFacing))
    }

    override suspend fun RobotSystem.onStart() = coroutineScope<Unit> {
/*
        moveTo("look 1", Side.Intake).join()

        val skystoneLocation = getSkystoneLocation(skystoneDetector)
        updateGrabNode(skystoneLocation)
        intake.power = 0.8

        moveTo("grab", Side.Intake)*/
        moveTo("prepare move", Side.Outtake).join()

        intake.power = 0.0
        autoArm.stateChannel.send(AutoArmState.Grab)

        grabber.close()
        delay(200)
        autoArm.stateChannel.send(AutoArmState.Drop)
        moveTo("move", Side.Intake).join()

        grabber.open()
        delay(200)
        moveTo("park", Side.Intake)
    }

    private fun updateGrabNode(skystoneLocation: Vector2d) = graph.run {
        graph.getNodeOrNull("grab")?.let { graph.removeNode(it) }
        graph.getNodeOrNull("grab i")?.let { graph.removeNode(it) }
        val location = skystoneLocation + grabOffset
        addNode(location)() {
            val it = this
            it.name = "grab"
            it.nodeConstraint = WaypointConstraint(direction = grabAngle, heading = grabAngle)
            it.splineTo("bridge enter")
            "align"().splineTo(location + preGrabOffset)() {
                name = "grab i"
                nodeConstraint = WaypointConstraint(direction = grabAngle)
            }.splineTo(it)
        }
    }


    private suspend fun getSkystoneLocation(skystoneDetector: SkystoneDetector): Vector2d {
        val channel = skystoneDetector.channel.openSubscription()
        val number = try {
            withTimeout(5000) {
                loop@ while (isActive) {
                    telemetry.update()
                    val skystones = channel.receive()
                    skystones.forEach {
                        telemetry.addLine(it.toString())
                    }
//                    if (skystones.size != 1) continue
                    val x = skystones.first().x
                    return@withTimeout when {
                        x <= 40 -> continue@loop
                        x in 40.0..180.0 -> 0
                        x in 180.0..318.0 -> 1
                        x in 318.0..461.0 -> 2
                        else -> continue@loop
                    }.also {
                        telemetry.addLine("Detected at $it")
                        telemetry.update()
                    }
                }
                1
            }
        } catch (e: TimeoutCancellationException) {
            telemetry.addLine("gave up")
            telemetry.update()
            1
        }.replaceIf(isFlipped) { 2 - it }
        skystoneDetector.cancel()
        return Vector2d(-2 * tile - number * block, tile)
    }
}

private val preGrabOffset = Vector2d(8 * `in`, 8 * `in`)
private val grabOffset =/* Vector2d.polar(robot / 3, grabAngle) -*/ Vector2d(0 * `in`, -7 * `in`)
