package org.firstinspires.ftc.teamcode.lib.system

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.channels.ReceiveChannel
import org.firstinspires.ftc.teamcode.lib.SYSTEM_PERIOD
import org.firstinspires.ftc.teamcode.lib.TickerSystem
import org.firstinspires.ftc.teamcode.lib.blueyay.ImageTracker
import org.firstinspires.ftc.teamcode.lib.blueyay.SkystoneDetector
import org.firstinspires.ftc.teamcode.lib.bot.*
import org.futurerobotics.bluejay.original.detectors.ImageDetector
import org.futurerobotics.jargon.ftcbridge.BulkMotorBlock
import org.futurerobotics.jargon.ftcbridge.MotorBulkData
import org.futurerobotics.jargon.ftcbridge.MultipleBulkData
import org.futurerobotics.jargon.hardware.Gyro
import org.futurerobotics.jargon.running.FrequencyRegulatedTicker
import org.futurerobotics.jargon.running.MaxSpeedRegulator
import org.futurerobotics.jargon.running.Ticker
import org.futurerobotics.jargon.running.syncedLoop
import org.openftc.revextensions2.ExpansionHubEx
import java.util.*

/**
 * An enum of all the possible systems on the bot.
 *
 * They can also depend on other systems.
 *
 * @see RobotSystemImpl
 */
@Suppress("SelfReferenceConstructorParameter")
enum class BotSystems(vararg val dependsOn: BotSystems) {

    /** [IMUGyro]*/
    Gyro,
    /** [BulkDataBroadcaster] */
    BulkDataBroadcast,
    /** [AutoDrive] */
    AutoDrive(BulkDataBroadcast, Gyro),
    /** [ManualDrive] */
    ManualDrive(BulkDataBroadcast, Gyro),
    /** [Lift] */
    Lift(BulkDataBroadcast),
    /** [ImageTracker] */
    ImageTracker,
    SkystoneDetector,
    TeleOp1(ManualDrive),
    TeleOp2(Lift),
    AllTeleOp(TeleOp1, TeleOp2),
    Auto(SkystoneDetector, AutoDrive, Lift)
}

/**
 * Interface for [RobotSystemImpl] so we can later use kotlin interface delegates.
 */
@UseExperimental(ExperimentalCoroutinesApi::class)
interface RobotSystem {

    val ticker: Ticker

    val opMode: OpMode
    val hardware: Hardware

    val bulkDataBroadcast: ConflatedBroadcastChannel<MotorBulkData>

    val imageTracker: ImageTracker
    val skystoneDetector: SkystoneDetector

    val gyro: Gyro
    val autoDrive: AutoDrive
    val manualDrive: ManualDrive

    val intake: SimpleSetPower
    val claw: ServoDoor
    val arm: Arm
    val lift: Lift

    val flicker: ServoDoor
    val grabber: ServoDoor

    val autoArm: AutoArmRunner
}

/**
 * Contains basically EVERYTHING we want to do in a `normal` op mode/teleop.
 *
 * Individual elements can be turned on or off using the passed [BotSystems].
 *
 * Then this will be run using [start].
 *
 * Only works on Future14473's bot. Sorry.
 */
@UseExperimental(ExperimentalCoroutinesApi::class) //for broadcast channel
class RobotSystemImpl
private constructor(
    systems: EnumSet<BotSystems>,
    override val opMode: OpMode
) : RobotSystem {

    constructor(opMode: OpMode, systems: EnumSet<BotSystems>) : this(
        EnumSet.noneOf(BotSystems::class.java).apply {
            //add all dependencies
            fun addSystem(system: BotSystems) {
                if (system in this) return
                this += system
                system.dependsOn.forEach {
                    addSystem(it)
                }
            }
            systems.forEach { addSystem(it) }
        },
        opMode
    )

    constructor(opMode: OpMode, vararg systems: BotSystems) : this(opMode, EnumSet.copyOf(systems.asList()))

    private val tickerSystems = mutableListOf<TickerSystem>()

    override val hardware = Hardware(opMode)


    override val ticker = FrequencyRegulatedTicker(MaxSpeedRegulator(SYSTEM_PERIOD))


    private var _bulkDataBroadcast = if (BotSystems.BulkDataBroadcast !in systems) null
    else BulkDataBroadcaster(hardware.hubs).also { tickerSystems += it }
    override val bulkDataBroadcast: ConflatedBroadcastChannel<MotorBulkData>
        get() = _bulkDataBroadcast?.channel ?: error("BulkDataBroadcast not in list of running systems.")

    //currently not used
    private val _imageTracker = if (BotSystems.ImageTracker !in systems) null else
        ImageTracker(opMode).also { tickerSystems += it }
    override val imageTracker: ImageTracker
        get() = _imageTracker ?: error("Image detection not in list of running systems.")

    private val _skystoneDetector = if (BotSystems.SkystoneDetector !in systems) null else
        SkystoneDetector(ImageDetector(opMode, true)).also { tickerSystems += it }
    override val skystoneDetector: SkystoneDetector
        get() = _skystoneDetector ?: error("Skystone detector not in list of running systems.")

    private val _gyro = if (BotSystems.Gyro !in systems) null else hardware.imu?.let {
        IMUGyro(it).apply { initialize() }
    } ?: error("IMU not in hardware map.")
    override val gyro: IMUGyro
        get() = _gyro ?: error("Gyro not in list of running systems.")


    private val _autoDrive: AutoDrive? = if (BotSystems.AutoDrive !in systems) null else {
        val channel = bulkDataBroadcast.openSubscription()
        val motors = BulkMotorBlock(hardware.wheelMotors.requireNoNulls()) {
            channel.receiveBlocking()
        }
        AutoDrive(motors, gyro).also { tickerSystems += it }
    }
    override val autoDrive get() = _autoDrive ?: error("Drive not in list of running systems.")


    private val _manualDrive: ManualDrive? = if (BotSystems.ManualDrive !in systems) null else {
        if (_autoDrive != null) error("Cannot have auto drive and manual drive at the same time")
        val channel = bulkDataBroadcast.openSubscription()
        val motors = BulkMotorBlock(hardware.wheelMotors.requireNoNulls()) {
            channel.receiveBlocking()
        }
        ManualDrive(motors).also { tickerSystems += it }
    }
    override val manualDrive: ManualDrive get() = _manualDrive ?: error("Manual Drive not in list of running systems.")


    private val _intake = if (null in hardware.intakeMotors) null else object : SimpleSetPower {
        private val motors = hardware.intakeMotors.requireNoNulls()
        override var power: Double = 0.0
            set(value) {
                motors.forEach {
                    it.power = value
                }
                field = value
            }
    }
    override val intake: SimpleSetPower
        get() = _intake ?: error("Intake motors not in hardware map.")


    private val _claw = hardware.clawServo?.let { ServoDoor(it, CLAW_RANGE, true) }
    override val claw get() = _claw ?: error("Claw not in hardware map.")


    private val _arm: Arm? = if (null in hardware.armServos) null else Arm(hardware.armServos.requireNoNulls())
    override val arm: Arm get() = _arm ?: error("Arm servos not in hardware map.")


    private val _lift: Lift? = if (BotSystems.Lift !in systems) null else {
        val channel = bulkDataBroadcast.openSubscription()
        val motors = BulkMotorBlock(hardware.liftMotors.requireNoNulls()) {
            channel.receiveBlocking()
        }
        Lift(motors).also { tickerSystems += it }
    }
    override val lift get() = _lift ?: error("Lift not in list of running systems.")


    private val _flicker = hardware.flickerServo?.let { ServoDoor(it, FLICKER_RANGE, true) }
    override val flicker get() = _flicker ?: error("Flicker not in hardware map.")

    private val _grabber = hardware.grabberServo?.let { ServoDoor(it, GRABBER_RANGE, true) }
    override val grabber: ServoDoor get() = _grabber ?: error("Grabber not in hardware map.")

    private val _autoArm = if(BotSystems.Auto !in systems) null else
        AutoArmRunner(this).also { tickerSystems += it }
    override val autoArm: AutoArmRunner
        get() = _autoArm ?: error("Auto arm not in list of running systems.")

    init {
        if (BotSystems.TeleOp1 in systems) tickerSystems += TeleOp1(this)
        if (BotSystems.TeleOp2 in systems) tickerSystems += TeleOp2(this)
    }

    fun start(scope: CoroutineScope) = with(scope) {
        launch {
            ticker.runSuspend()
        }
        tickerSystems.forEach {
            it.launchSystem(this, ticker)
        }
    }
}

/**
 * Contains a [channel] which broadcasts [MotorBulkData] (wrapper around [RevBulkData]s)
 */
@UseExperimental(ExperimentalCoroutinesApi::class)
class BulkDataBroadcaster(
    private val hubs: List<ExpansionHubEx>
) : TickerSystem { //brod

    val channel = ConflatedBroadcastChannel<MotorBulkData>()

    override fun launchSystem(scope: CoroutineScope, ticker: Ticker) {
        scope.launch(Dispatchers.IO) {
            //            RobotLog.v("hubs: $hubs")
            try {
                ticker.listener().syncedLoop {
                    val data = MultipleBulkData(hubs.map { it.bulkInputData })
                    channel.send(data)
                    false
                }
            } catch (e: Throwable) {
                channel.close(e)
                throw e
            }
        }
    }
}

fun <E : Any> ReceiveChannel<E>.receiveBlocking() = poll() ?: runBlocking { receive() }
