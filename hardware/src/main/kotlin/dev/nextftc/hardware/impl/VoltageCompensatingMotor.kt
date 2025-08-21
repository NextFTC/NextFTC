package dev.nextftc.hardware.impl

import dev.nextftc.ftc.ActiveOpMode
import dev.nextftc.hardware.controllable.Controllable
import kotlin.properties.Delegates
import kotlin.time.ComparableTimeMark
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds
import kotlin.time.TimeSource.Monotonic.markNow

class VoltageCompensatingMotor(
    private val controllable: Controllable,
    private val voltageCacheTime: Duration,
    private val nominalVoltage: Double = 12.0
) : Controllable by controllable {

    @JvmOverloads
    constructor(
        controllable: Controllable,
        voltageCacheTimeSeconds: Double = 0.5,
        nominalVoltage: Double = 12.0,
    ) : this(controllable, voltageCacheTimeSeconds.seconds, nominalVoltage)

    @JvmOverloads
    constructor(
        controllable: Controllable,
        voltageCacheTime: String,
        nominalVoltage: Double = 12.0
    ) : this(controllable, Duration.parse(voltageCacheTime.replace("\\s".toRegex(), "")), nominalVoltage)

    private val voltageSensor by lazy { ActiveOpMode.hardwareMap.voltageSensor.first() }

    private var cachedVoltage by Delegates.notNull<Double>()
    private lateinit var lastVoltageRead: ComparableTimeMark

    private val voltage: Double
        get() {
            val currentTime = markNow()
            val timeSinceLastVoltageRead = currentTime - lastVoltageRead
            if (!::lastVoltageRead.isInitialized || timeSinceLastVoltageRead >= voltageCacheTime) {
                cachedVoltage = voltageSensor.voltage
                lastVoltageRead = currentTime
            }
            return cachedVoltage
        }

    override var power by Delegates.observable(0.0) { _, _, new ->
        controllable.power = new * nominalVoltage / voltage
    }
}