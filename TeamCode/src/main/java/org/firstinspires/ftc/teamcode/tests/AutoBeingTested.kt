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


//right now: loading side
@Autonomous
@UseExperimental(ExperimentalCoroutinesApi::class)
class AutoBeingTested : BotSystemsOpMode(BotSystems.Auto) {

    private val graph = getFieldGraph()
    private val tracker = GraphTracker(graph, "start loading", Side.Intake)
    private lateinit var drive: AutoDrive

    override suspend fun RobotSystem.onInit() {
        drive = autoDrive
        drive.resetInitialPose(startLoading)
        drive.setSide(AutoDrive.Side.Blue)
    }

    private fun moveTo(name: String, startFacing: Side, endFacing: Side = startFacing): Job {
        return drive.followPath(tracker.pathTo(name, startFacing, endFacing))
    }

    override suspend fun RobotSystem.onStart() = coroutineScope<Unit> {

        val toLook = moveTo("look 1", Side.Intake)

        val skystoneLocation = getSkystoneLocation(skystoneDetector)
        updateGrabNode(skystoneLocation)
        launch {
            toLook.join()
            intake.power = 0.8
        }
        val toGrab = moveTo("grab", Side.Intake)
        toGrab.join()
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
                while (isActive) {
                    telemetry.update()
                    val skystones = channel.receive()
                    skystones.forEach {
                        telemetry.addLine(it.toString())
                    }
                    if (skystones.size != 1) continue
                    val x = skystones.first().x
                    val width = 640 / 3
                    return@withTimeout (x / width).toInt().coerceAtMost(2).also {
                        telemetry.addLine("Block at $it")
                        telemetry.update()
                    }
                }
                1
            }
        } catch (e: TimeoutCancellationException) {
            telemetry.addLine("gave up")
            telemetry.update()
            1
        }
        skystoneDetector.cancel()
        return Vector2d(-2 * tile - number * block, tile)
    }
}

private val preGrabOffset = Vector2d(7 * `in`, 7 * `in`)
private val grabOffset =/* Vector2d.polar(robot / 3, grabAngle) -*/ Vector2d(7 * `in`, -4 * `in`)
