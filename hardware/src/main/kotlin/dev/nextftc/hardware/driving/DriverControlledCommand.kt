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

import dev.nextftc.core.commands.Command
import java.util.function.Supplier

abstract class DriverControlledCommand(vararg val joystickPowers: Supplier<Double>) : Command() {
    final override val isDone: Boolean = false

    var scalar: Double = 1.0

    init {
        requires(Drivetrain)
    }

    abstract fun calculateAndSetPowers(powers: DoubleArray)

    final override fun update() {
        val scaledPowers =
            joystickPowers.map { it.get() }.map { it * scalar.toFloat() }.toDoubleArray()
        calculateAndSetPowers(scaledPowers)
    }
}