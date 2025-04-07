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

package com.rowanmcalpin.nextftc.ftc.hardware

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorEx
import com.rowanmcalpin.nextftc.ftc.OpModeData
import com.rowanmcalpin.nextftc.hardware.controllable.Controllable

/**
 * Wrapper class for motors that implements controllable (and can therefore be used with RunToPosition
 * commands).
 */
class MotorEx(cacheTolerance: Double, motorFactory: () -> DcMotorEx) :
    Hardware<DcMotorEx>(motorFactory), Controllable {

    constructor(motorFactory: () -> DcMotorEx) : this(0.01, motorFactory)

    @JvmOverloads
    constructor(motor: DcMotorEx, cacheTolerance: Double = 0.01) : this(cacheTolerance, { motor })

    @JvmOverloads
    constructor(
        name: String,
        cacheTolerance: Double = 0.01
    ) : this(cacheTolerance, { OpModeData.hardwareMap!![DcMotorEx::class.java, name] })

    private var offset = 0.0
    private var center = 0.0
    var direction = 1
        set(value) {
            require(
                value in intArrayOf(
                    -1,
                    1
                )
            ) { "direction must be either 1 or -1 but was $value." }
            center = currentPosition
            field = value
        }

    /**
     * Gives the unmodified raw tick value of the motor
     */
    val rawTicks: Double
        get() = hardware.currentPosition.toDouble()

    /**
     * Zero Power Behavior for the motor
     */
    var zeroPowerBehavior: DcMotor.ZeroPowerBehavior by hardware::zeroPowerBehavior


    /**
     * This returns the current position of the motor, accounting for any offsets created by manually
     * settings its currentPosition.
     *
     * Setting this value tells it that its current position is actually the position you've set the variable to,
     * which will get accounted for automatically when getting this value.
     */
    override var currentPosition: Double
        get() = (offset + rawTicks - center) * direction + center
        set(value) {
            center = value
            offset = rawTicks - value
        }

    /**
     * Current velocity of the motor
     */
    override val velocity: Double by hardware::velocity

    /**
     * Gets / sets the current power of the motor (automatically implements power caching)
     */
    override var power: Double by Caching(0.0, cacheTolerance) {
        hardware.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER
        it?.let { hardware.power = it }
    }

    fun reverse() {
        direction = -direction
    }

    fun reversed() = apply { reverse() }

    fun zero() {
        currentPosition = 0.0
    }

    fun zeroed() = apply { zero() }

    fun floatMode() = apply { zeroPowerBehavior = DcMotor.ZeroPowerBehavior.FLOAT }
    fun brakeMode() = apply { zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE }
}