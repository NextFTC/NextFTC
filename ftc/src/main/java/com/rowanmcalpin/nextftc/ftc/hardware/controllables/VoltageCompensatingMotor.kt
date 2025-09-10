package com.rowanmcalpin.nextftc.ftc.hardware.controllables

import com.rowanmcalpin.nextftc.ftc.OpModeData
import kotlin.properties.Delegates
import kotlin.time.ComparableTimeMark
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds
import kotlin.time.TimeSource.Monotonic.markNow

class VoltageCompensatingMotor(
    private val controllable: Controllable,
    private val nominalVoltage: Double = 12.0,
    private val voltageCacheTime: Duration = 500.milliseconds
) : Controllable by controllable {

    @JvmOverloads
    constructor(
        controllable: Controllable,
        nominalVoltage: Double = 12.0,
        voltageCacheTimeSeconds: Double = 0.5
    ) : this(controllable, nominalVoltage, voltageCacheTimeSeconds.seconds)

    private val voltageSensor by lazy { OpModeData.hardwareMap.voltageSensor.first() }

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