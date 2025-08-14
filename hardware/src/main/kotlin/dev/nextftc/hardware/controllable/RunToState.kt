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

package dev.nextftc.hardware.controllable

import dev.nextftc.core.command.Command
import dev.nextftc.control.ControlSystem
import dev.nextftc.control.KineticState

/**
 * This command sets a [ControlSystem] goal to the [goal], and then waits until the
 * system is within the [tolerance]
 *
 * @param system the system to control
 * @param goal the goal for the [system]
 * @param tolerance tolerance to be considered "at" the [goal]
 */
open class RunToState(
    val system: ControlSystem,
    val goal: KineticState,
    val tolerance: KineticState = KineticState(10.0, 5.0, Double.POSITIVE_INFINITY)
) : Command() {

    override val isDone: Boolean
        get() = system.isWithinTolerance(tolerance)

    override fun start() {
        system.goal = goal
    }
}