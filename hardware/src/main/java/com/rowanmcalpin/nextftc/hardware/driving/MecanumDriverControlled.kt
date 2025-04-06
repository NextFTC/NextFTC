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

package com.rowanmcalpin.nextftc.hardware.driving

import com.rowanmcalpin.nextftc.core.units.Angle
import com.rowanmcalpin.nextftc.hardware.controllable.Controllable
import java.util.function.Supplier
import kotlin.math.absoluteValue
import kotlin.math.cos
import kotlin.math.max
import kotlin.math.sin

class MecanumDriverControlled @JvmOverloads constructor(
    val frontLeftMotor: Controllable,
    val frontRightMotor: Controllable,
    val backLeftMotor: Controllable,
    val backRightMotor: Controllable,
    drivePower: Supplier<Float>,
    strafePower: Supplier<Float>,
    turnPower: Supplier<Float>,
    val mode: HolonomicMode = HolonomicMode.RobotCentric
) : DriverControlledCommand(strafePower, drivePower, turnPower) {


    override fun calculateAndSetPowers(powers: DoubleArray) {
        val (x, y, rx) = HolonomicDrivePowers.from(powers)
            .rotate(mode)
            .let { HolonomicDrivePowers(it.x * 1.1, it.y, it.heading) }

        val denominator = max(x.absoluteValue + y.absoluteValue + rx.absoluteValue, 1.0)
        val frontLeftPower = (y + x + rx) / denominator
        val frontRightPower = (y - x - rx) / denominator
        val backLeftPower = (y - x + rx) / denominator
        val backRightPower = (y + x - rx) / denominator

        frontLeftMotor.power = frontLeftPower
        frontRightMotor.power = frontRightPower
        backLeftMotor.power = backLeftPower
        backRightMotor.power = backRightPower
    }
}