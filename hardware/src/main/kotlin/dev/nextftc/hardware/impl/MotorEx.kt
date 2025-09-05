/*
 * NextFTC: a user-friendly control library for FIRST Tech Challenge
 *     Copyright (C) 2025 Rowan McAlpin
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package dev.nextftc.hardware.impl

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorEx
import dev.nextftc.ftc.ActiveOpMode
import dev.nextftc.hardware.delegates.Caching
import dev.nextftc.hardware.delegates.Offsetable
import dev.nextftc.hardware.controllable.Controllable

/**
 * Wrapper class for motors that implements controllable (and can therefore be used with RunToPosition
 * commands).
 */
class MotorEx(cacheTolerance: Double, motorFactory: () -> DcMotorEx) : Controllable {

    constructor(motorFactory: () -> DcMotorEx) : this(0.01, motorFactory)

    @JvmOverloads
    constructor(motor: DcMotorEx, cacheTolerance: Double = 0.01) : this(cacheTolerance, { motor })

    @JvmOverloads
    constructor(
        name: String,
        cacheTolerance: Double = 0.01
    ) : this(cacheTolerance, { ActiveOpMode.hardwareMap[name] as DcMotorEx })

    val motor by lazy(motorFactory)

    /**
     * Gives the unmodified raw tick value of the motor
     */
    val rawTicks: Double
        get() = motor.currentPosition.toDouble()

    val offsetable = Offsetable { rawTicks }

    var direction by offsetable::direction

    /**
     * Zero Power Behavior for the motor
     */
    var zeroPowerBehavior: DcMotor.ZeroPowerBehavior by motor::zeroPowerBehavior


    /**
     * This returns the current position of the motor, accounting for any offsets created by manually
     * settings its currentPosition.
     *
     * Setting this value tells it that its current position is actually the position you've set the variable to,
     * which will get accounted for automatically when getting this value.
     */
    override var currentPosition by offsetable

    /**
     * Current velocity of the motor
     */
    override val velocity: Double by motor::velocity

    /**
     * Gets / sets the current power of the motor (automatically implements power caching)
     */
    override var power: Double by Caching(cacheTolerance) {
        motor.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER
        it?.let { motor.power = it * direction }
    }

    fun reverse() {
        direction = -direction
    }

    fun reversed() = apply { reverse() }

    fun zero() {
        currentPosition = 0.0
    }

    fun zeroed() = apply { zero() }

    fun atPosition(position: Double) = apply { currentPosition = position }

    fun floatMode() = apply { zeroPowerBehavior = DcMotor.ZeroPowerBehavior.FLOAT }
    fun brakeMode() = apply { zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE }
}
