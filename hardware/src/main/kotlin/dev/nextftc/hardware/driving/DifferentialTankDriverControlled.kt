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

package dev.nextftc.hardware.driving

import dev.nextftc.hardware.controllable.Controllable
import java.util.function.Supplier

/**
 * Drives a differential drivetrain as a tank drive.
 * @param leftMotor: The motor(s) on the left side of the drivetrain
 * @param rightMotor: The motor(s) on the right side of the drivetrain
 * @param leftPower: The power to use to control the left side of the drivetrain
 * @param rightPower: The power to use to control the right side of the drivetrain
 */
class DifferentialTankDriverControlled(
    val leftMotor: Controllable,
    val rightMotor: Controllable,
    val leftPower: Supplier<Float>,
    val rightPower: Supplier<Float>
) : DriverControlledCommand(leftPower, rightPower) {

    override fun calculateAndSetPowers(powers: DoubleArray) {
        val (leftPower, rightPower) = powers

        leftMotor.power = leftPower
        rightMotor.power = rightPower
    }
}